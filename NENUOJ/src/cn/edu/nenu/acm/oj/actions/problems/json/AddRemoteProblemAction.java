package cn.edu.nenu.acm.oj.actions.problems.json;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.eto.NotSupportJudgeSourceException;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "list", "namespace", "/problems" }) })
public class AddRemoteProblemAction extends AbstractJsonAction {

	private static final long serialVersionUID = -6473092721026663030L;
	private String judgerSource;
	private String problemNumber;

	@Autowired
	private GenericDAO dao;

	@Autowired
	private JudgeService judgeService;

	@Override
	public String execute(){
		List<Judger> lstJudger = dao.findByColumn("source", judgerSource, Judger.class);
		if (lstJudger.size() == 0) {
			message = _("Unsupported OJ, no database definition: ") + judgerSource;
			code = STATUS_ERROR;
		} else {
			try {
				judgeService.putCrawlJob(judgerSource, problemNumber);
				message = _("problem_crawling_added");
				code = STATUS_SUCCESS;
			} catch (NotSupportJudgeSourceException e) {
				message = _("Unsupported OJ, no backend information: ") + judgerSource;
				code = STATUS_ERROR;
				e.printStackTrace();
			}
		}
		return SUCCESS;
	}

	@RequiredStringValidator(key = "judger_source_required")
	public void setJudgerSource(String judgerSource) {
		this.judgerSource = judgerSource;
	}

	@RequiredStringValidator(key = "problem_number_required")
	public void setProblemNumber(String problemNumber) {
		this.problemNumber = problemNumber;
	}

	@JSON(serialize = false)
	public String getJudgerSource() {
		return judgerSource;
	}

	@JSON(serialize = false)
	public String getProblemNumber() {
		return problemNumber;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
