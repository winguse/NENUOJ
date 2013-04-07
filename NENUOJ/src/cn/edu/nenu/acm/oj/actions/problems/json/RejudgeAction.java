package cn.edu.nenu.acm.oj.actions.problems.json;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.SolutionDAO;
import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json") })
public class RejudgeAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = -4232142407810838082L;
	@Autowired
	private SolutionDAO dao;
	@Autowired
	private JudgeService judgeService;

	private int runId = 0;
	private boolean rejudgeNotErrorSolution = false;

	@Override
	public String execute() throws Exception {
		Solution solution = dao.findById(runId);
		if (solution == null) {
			code = STATUS_ERROR;
			message = _("Solution not found.");
		} else if (solution.getStatus() != Solution.STATUS_JUDGE_ERROR
				&& !rejudgeNotErrorSolution) {
			code = STATUS_ERROR;
			message = _("You don't have the permission to rejudge this solution. #"+solution.getStatus());
		} else {
			solution.setStatus(Solution.STATUS_PEDDING);
			solution.setStatusDescription("Pedding Rejudge");
			dao.merge(solution);
			judgeService.putJudgeJob(solution.getId());
			code = STATUS_SUCCESS;
			message = "success";
		}
		return SUCCESS;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO set rejudgeNotErrorSolution = true if the user logined and has
		// the permission
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

}
