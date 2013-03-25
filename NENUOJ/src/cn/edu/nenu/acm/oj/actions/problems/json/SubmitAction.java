package cn.edu.nenu.acm.oj.actions.problems.json;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.eto.NotSupportJudgeSourceException;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;
import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;

@ParentPackage("winguse-json-default")
@InterceptorRefs({
		@InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack")
		,@InterceptorRef(value = "permissionInterceptor", params = { "permission", "" + IPermissionCode.PERMISSION_LOGIN })
})
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "list", "namespace", "/problems" }) })
public class SubmitAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = 3128406696030266608L;
	private int problemId;
	private String sourceCode;
	private String language;
	private Map<String, Object> session;

	@Autowired
	private GenericDAO dao;

	@Autowired
	private JudgeService judgeService;

	@Override
	public String execute() {
		try {
			Problem problem = dao.findById(problemId, Problem.class);
			if (problem == null) {
				message = _("problem not found.");
				code = STATUS_ERROR;
			} else {
				User user = dao.findById(((UserSimpleDTO)(session.get("user"))).getId(),User.class);
				if(user==null)
					throw new Exception("Where are you from?");
				HttpServletRequest request = ServletActionContext.getRequest();
				Solution solution = new Solution();
				solution.setCodeLength(sourceCode.length());
				solution.setIpaddr(request.getRemoteAddr() + "|" + request.getHeader("VIA") + "|"
						+ request.getHeader("X-FORWARDED-FOR"));
				solution.setLanguage(language);
				solution.setSourceCode(sourceCode);
				solution.setProblem(problem);
				solution.setShared(user.getRemark().get("notShareCode")==null);
				solution.setUser(user);
				solution.setStatus(Solution.STATUS_PEDDING);
				solution.setStatusDescription("PEDDING");
				solution.setSubmitTime(new Date());
				dao.persist(solution);
				judgeService.putJudgeJob(solution.getId());
				message = _("submitted.");
				code = STATUS_SUCCESS;
			}
		} catch (NotSupportJudgeSourceException e) {
			message = _("Unsupported OJ, no backend definition.");
			code = STATUS_ERROR;
			e.printStackTrace();
		} catch (Exception e) {
			message = e.getMessage();
			code = STATUS_ERROR;
			e.printStackTrace();
		}
		return SUCCESS;
	}

	@JSON(serialize = false)
	public int getProblemId() {
		return problemId;
	}

	@RequiredFieldValidator(key = "problem_id_required")
	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}
	
	@RequiredStringValidator(key = "source_code_required")
	@FieldExpressionValidator(expression = "sourceCode.length()>=50 && sourceCode.length()<=65535", key = "code_length")
	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@JSON(serialize = false)
	public String getSourceCode() {
		return sourceCode;
	}

	@JSON(serialize = false)
	public String getLanguage() {
		return language;
	}

	@RequiredStringValidator(key = "language_required")
	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
}
