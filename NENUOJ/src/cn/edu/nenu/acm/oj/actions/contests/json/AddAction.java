package cn.edu.nenu.acm.oj.actions.contests.json;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;

@ParentPackage("winguse-json-default")
@InterceptorRefs({
	@InterceptorRef("i18n"), 
	@InterceptorRef("jsonValidationWorkflowStack")
	,@InterceptorRef(value = "permissionInterceptor", params = { "permission", "" + IPermissionCode.PERMISSION_LOGIN })
})
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "add", "namespace", "/contests" }) })
public class AddAction extends AbstractJsonAction {
	
	
	private int contestType;
	private String contestTitle;
	private Long startTime;
	private Long endTime;
	private String description = "";
	private String announcement = "";
	private List<String> judgerSource;
	private List<String> problemNumber;
	private List<Integer> problemDescription;
	
	private File replayData;
	
	@Override
	public String execute(){
		
		return SUCCESS;
	}
	
	@RequiredFieldValidator(key = "Contest type is requried!")
	public void setContestType(int contestType) {
		this.contestType = contestType;
	}

	@RequiredStringValidator(key = "Contest yitle is requried!")
	@FieldExpressionValidator(expression = "contestTitle.length()<128", key = "Contest title must not longer than 128.")
	public void setContestTitle(String contestTitle) {
		this.contestTitle = contestTitle;
	}

	@RequiredFieldValidator(key = "Contest starting time is requried!")
	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	@RequiredFieldValidator(key = "Contest type Is Requried!")
	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	@FieldExpressionValidator(expression = "description.length()<1024", key = "Contest description must not longer than 1024.")
	public void setDescription(String description) {
		this.description = description;
	}

	@FieldExpressionValidator(expression = "announcement.length()<1024", key = "Contest announcement must not longer than 1024.")
	public void setAnnouncement(String announcement) {
		this.announcement = announcement;
	}

	public void setJudgerSource(List<String> judgerSource) {
		this.judgerSource = judgerSource;
	}

	public void setProblemNumber(List<String> problemNumber) {
		this.problemNumber = problemNumber;
	}

	public void setProblemDescription(List<Integer> problemDescription) {
		this.problemDescription = problemDescription;
	}

	public void setReplayData(File replayData) {
		this.replayData = replayData;
	}
	
}
