package cn.edu.nenu.acm.oj.actions.problems.json;


import java.util.Map;

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
import cn.edu.nenu.acm.oj.dao.ProblemDAO;
import cn.edu.nenu.acm.oj.dto.ProblemDTO;

@ParentPackage("json-default")
@InterceptorRefs({
	@InterceptorRef("i18n"),
	@InterceptorRef("jsonValidationWorkflowStack")
})
@Results({ 
	@Result(name = "success", type = "json"),
	@Result(name = "input", type = "redirectAction", params = { "actionName", "view", "namespace", "/problems" })
})
public class ViewAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;
	private boolean includeLocked = false;
	private int problemId;
	private int problemDescriptionId = 0;
	
	@Autowired
	private ProblemDAO dao;
	
	private ProblemDTO problem;
	
	@Override
	public String execute() throws Exception {
		problem = dao.getProblemAndDescription(problemId, problemDescriptionId, includeLocked);
		return SUCCESS;
	}

	public ProblemDTO getProblem() {
		return problem;
	}

	@RequiredFieldValidator(key = "problem_id_required")
	@FieldExpressionValidator(expression = "problemId>0", key = "invalid_problem_id")
	public void setProblemId(int problemId) {
		System.out.println(problemId);
		this.problemId = problemId;
	}

	@JSON(serialize=false)
	public int getProblemId() {
		return problemId;
	}

	public void setProblemDescriptionId(int problemDescriptionId) {
		this.problemDescriptionId = problemDescriptionId;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO set includeLocked according to user's level
	}
	

}
