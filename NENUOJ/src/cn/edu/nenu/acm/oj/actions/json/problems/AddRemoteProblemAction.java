package cn.edu.nenu.acm.oj.actions.json.problems;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.actions.json.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({
	@Result(name = "success", type="json"),
	@Result(name = "input",type="redirectAction",params={"actionName","list","namespace","/problems"})
})
public class AddRemoteProblemAction extends AbstractJsonAction {

	private String judgeSource;
	private String problem;

	@Autowired
	private GenericDAO dao;
	
	@Autowired
	private JudgeService judgeService;
	
	@Override
	public String execute() throws Exception {
		List<Judger> lstJudger = dao.findByColumn("source", judgeSource, Judger.class);
		if(lstJudger.size()>=0){
			message = _("Unsupported OJ: ")+judgeSource;
			code = STATUS_ERROR;
		}else{
			judgeService.putCrawlJob(judgeSource, problem);
			message = _("problem_crawling_added");
			code = STATUS_SUCCESS;
		}
		return SUCCESS;
	}
	
	@RequiredStringValidator(key = "judger_source_required")
	public void setJudgerSource(String judgerSource) {
		this.judgeSource = judgerSource;
	}

	@RequiredStringValidator(key = "problem_number_required")
	public void setProblem(String problem) {
		this.problem = problem;
	}
	
	
}
