package cn.edu.nenu.acm.oj.actions.contests.json;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.ProblemDescriptionDAO;
import cn.edu.nenu.acm.oj.dto.ProblemDescriptionSimpleDTO;
@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json")})
public class ProblemDescriptionListAction extends AbstractJsonAction {
	
	private int problemId;
	private List<ProblemDescriptionSimpleDTO> problemDescriptionSimpleDTO;
	
	@Autowired
	ProblemDescriptionDAO dao;
	
	@Override
	public String execute(){
		problemDescriptionSimpleDTO = dao.getDescriptionList(problemId);
		return SUCCESS;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public List<ProblemDescriptionSimpleDTO> getProblemDescriptionSimpleDTO() {
		return problemDescriptionSimpleDTO;
	}
	
}
