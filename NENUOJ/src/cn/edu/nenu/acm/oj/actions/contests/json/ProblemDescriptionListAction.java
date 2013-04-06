package cn.edu.nenu.acm.oj.actions.contests.json;

import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.ProblemDescriptionDAO;
import cn.edu.nenu.acm.oj.dto.ProblemDescriptionSimpleDTO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json") })
public class ProblemDescriptionListAction extends AbstractJsonAction implements SessionAware {

	private String problemNumber;
	private String judgerSource;
	private List<ProblemDescriptionSimpleDTO> problemDescriptionList;

	private Map<String, Object> session;

	@Autowired
	ProblemDescriptionDAO dao;

	@Override
	public String execute() {
		int userId = 0;
		boolean includeLockedDescription = false;
		boolean includeLockedProblem = false;
		if (session.containsKey("user")) {
			UserSimpleDTO user = (UserSimpleDTO) session.get("user");
			userId = user.getId();
			if (
					(user.getPermission() & UserSimpleDTO.PERMISSION_SEE_LOCKED_DESCRIPTION) == UserSimpleDTO.PERMISSION_SEE_LOCKED_DESCRIPTION||
					(user.getPermission() & UserSimpleDTO.PERMISSION_ADMIN_PRIVILEGE) == UserSimpleDTO.PERMISSION_ADMIN_PRIVILEGE
				) {
				includeLockedDescription = true;
			}
			if (
					(user.getPermission() & UserSimpleDTO.PERMISSION_SEE_LOCKED_PROBLEM) == UserSimpleDTO.PERMISSION_SEE_LOCKED_PROBLEM||
					(user.getPermission() & UserSimpleDTO.PERMISSION_ADMIN_PRIVILEGE) == UserSimpleDTO.PERMISSION_ADMIN_PRIVILEGE
				) {
				includeLockedProblem = true;
			}
		}
		problemDescriptionList = dao.getDescriptionList(problemNumber,includeLockedProblem, judgerSource, includeLockedDescription, userId);

		return SUCCESS;
	}

	public List<ProblemDescriptionSimpleDTO> getProblemDescriptionList() {
		return problemDescriptionList;
	}

	public void setProblemNumber(String problemNumber) {
		this.problemNumber = problemNumber;
	}

	public void setJudgerSource(String judgerSource) {
		this.judgerSource = judgerSource;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

}
