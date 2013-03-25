package cn.edu.nenu.acm.oj.actions.problems.json;

import java.util.LinkedList;
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
import cn.edu.nenu.acm.oj.dao.SolutionDAO;
import cn.edu.nenu.acm.oj.dto.SolutionSimpleDTO;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json") })
public class SyncStatusAction extends AbstractJsonAction implements
		SessionAware {

	private static final long serialVersionUID = 4012120014961575987L;

	@Autowired
	private SolutionDAO dao;

	private String runIdList = "";
	private boolean includeCurrentContest = false;
	private LinkedList<Object[]> data;
	private String[] indexMapping;

	@Override
	public String execute() throws Exception {
		code = 0;
		message = "success";
		List<Integer> runIds = new LinkedList<Integer>();
		for (String r : runIdList.split(",")) {
			try {
				runIds.add(new Integer(r));
			} catch (Exception e) {
			}
		}
		List<SolutionSimpleDTO> result = dao.getSolutionList(runIds,
				includeCurrentContest);
		indexMapping = new String[] { _("runId"), _("username"),
				_("judgerSource") + " " + _("problemNumber"),
				_("statusDescription"), _("memory"), _("time"), _("language"),
				_("codeLength"), _("submitTime"), _("problemId"),
				_("problemTitle"), _("statusCode"), _("contestId") };
		data = new LinkedList<Object[]>();
		for (SolutionSimpleDTO s : result) {
			data.add(new Object[] { s.getRunId(), s.getUsername(),
					s.getJudgerSource() + " " + s.getPrublemNumber(),
					s.getStatusDescription(), s.getMemory(), s.getTime(),
					s.getLanguage(), s.getCodeLength(), s.getSubmitTime(),
					s.getProblemId(), s.getProblemTitle(), s.getStatusCode(),
					s.getContestId() });
		}
		return SUCCESS;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		// TODO set include contest if the user logined and has the permission
	}

	public LinkedList<Object[]> getData() {
		return data;
	}

	public String[] getIndexMapping() {
		return indexMapping;
	}

	public void setRunIdList(String runIdList) {
		this.runIdList = runIdList;
	}

}
