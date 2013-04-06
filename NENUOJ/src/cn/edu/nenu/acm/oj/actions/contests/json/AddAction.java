package cn.edu.nenu.acm.oj.actions.contests.json;

import java.io.File;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredFieldValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.ContestDAO;
import cn.edu.nenu.acm.oj.dao.ProblemDescriptionDAO;
import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;
import cn.edu.nenu.acm.oj.util.Remark;

@ParentPackage("winguse-json-default")
@InterceptorRefs({
		@InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack"),
		@InterceptorRef(value = "permissionInterceptor", params = { "permission",
				"" + IPermissionCode.PERMISSION_ADD_CONTEST }) })
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "add", "namespace", "/contests" }) })
@Validations(requiredStrings = {}, expressions = {
		@ExpressionValidator(expression = "judgerSource!=null&&problemNumber!=null&&problemDescription!=null", message = "Empty problem set of the contest!"),
		@ExpressionValidator(expression = "judgerSource.length()>0&&judgerSource.length()==problemNumber.length()&&judgerSource.length()==problemDescription.length()", message = "Problem set is invalid!") }, fieldExpressions = {})
public class AddAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = 6201478464197183393L;
	private int contestType;
	private String contestTitle;
	private Long startTime;
	private Long endTime;
	private String description = "";
	private String announcement = "";
	private List<String> judgerSource;
	private List<String> problemNumber;
	private List<Integer> problemDescription;
	private Map<String, Object> session;
	private File replayData;
	
	@Autowired
	private ProblemDescriptionDAO pdDao;
	@Autowired
	private ContestDAO cDao;
	@Autowired
	private UserDAO uDao;
	
	@Override
	public String execute() {
		code = CODE_ERROR;
		UserSimpleDTO user = (UserSimpleDTO) session.get("user");
		Set<ProblemDescription> pdSet = new HashSet<ProblemDescription>();
		boolean includeLockedDescription = false;
		boolean includeLockedProblem = false;
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
		for(Integer pdId : problemDescription){
			ProblemDescription pd = pdDao.findById(pdId);
			if(pd == null){
				message = _("One of the problem description you have submitted is not exist.")+" pdId = "+pdId;
				return SUCCESS;
			}
			if(!includeLockedProblem&&pd.getProblem().isLocked()){
				message = _("One of the problem you do not have permission to access.")+" pdId = "+pdId;
				return SUCCESS;
			}
			if(pd.isLocked()&&!includeLockedDescription){
				message = _("One of the problem description you do not have permission to access.")+" pdId = "+pdId;
				return SUCCESS;
			}
			pdSet.add(pd);
		}
		Contest contest = new Contest();
		contest.setContestType(contestType);
		contest.setEndTime(new Date(endTime));
		contest.setHostUser(uDao.findById(user.getId()));
		contest.setProblemDescriptions(pdSet);
		Remark remark = new Remark();
		remark.set("description", description);
		remark.set("announcement", announcement);
		contest.setRemark(remark);
		contest.setStartTime(new Date(startTime));
		cDao.persist(contest);
		code = CODE_SUCCESS;
		message = _("Contest added successfully.");
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

	@RequiredFieldValidator(key = "Contest ending time Is Requried!")
	@Validations(fieldExpressions = {
			@FieldExpressionValidator(expression = "endTime >= startTime+3600", key = "Contest ending time must 1hr later then starting time.")
,			@FieldExpressionValidator(expression = "contestType == cn.edu.nenu.acm.oj.statuscode.IContestType.CONTEST_TYPE_REPLAY && endTime < new Date().getTime()", key = "Contest ending time must 1hr later then starting time.")
	})
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

	@JSON(serialize = false)
	public int getContestType() {
		return contestType;
	}

	@JSON(serialize = false)
	public String getContestTitle() {
		return contestTitle;
	}

	@JSON(serialize = false)
	public Long getStartTime() {
		return startTime;
	}

	@JSON(serialize = false)
	public Long getEndTime() {
		return endTime;
	}

	@JSON(serialize = false)
	public String getDescription() {
		return description;
	}

	@JSON(serialize = false)
	public String getAnnouncement() {
		return announcement;
	}

	@JSON(serialize = false)
	public List<String> getJudgerSource() {
		return judgerSource;
	}

	@JSON(serialize = false)
	public List<String> getProblemNumber() {
		return problemNumber;
	}

	@JSON(serialize = false)
	public List<Integer> getProblemDescription() {
		return problemDescription;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		session = arg0;
	}

}
