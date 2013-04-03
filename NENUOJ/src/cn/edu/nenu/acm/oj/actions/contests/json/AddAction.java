package cn.edu.nenu.acm.oj.actions.contests.json;

import java.io.File;
import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "add", "namespace", "/contests" }) })
public class AddAction extends AbstractJsonAction {
	
	
	private int contestType;
	private String contestTitle;
	private Long startTime;
	private Long endTime;
	private String description;
	private String announcement;
	private List<String> judgerSource;
	private List<String> problemNumber;
	private List<Integer> problemDescription;
	
	private File replayData;
	
	@Override
	public String execute(){
		
		return SUCCESS;
	}

	public void setContestType(int contestType) {
		this.contestType = contestType;
	}

	public void setContestTitle(String contestTitle) {
		this.contestTitle = contestTitle;
	}

	public void setStartTime(Long startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Long endTime) {
		this.endTime = endTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

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
