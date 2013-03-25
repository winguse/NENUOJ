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
import cn.edu.nenu.acm.oj.dao.ProblemDAO;
import cn.edu.nenu.acm.oj.dao.SolutionDAO;
import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.dto.SolutionSimpleDTO;
import cn.edu.nenu.acm.oj.util.Pair;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json") })
public class StatusAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = -1246689012240563630L;

	@Autowired
	private SolutionDAO dao;

	private String username = "";
	private String problemNumber = "";
	private String language = "";
	private String judgerSource = "";
	private int statusCode = 0;
	private int page = 0;
	private int pageSize = 50;
	private int orderByIndex = 0;
	private boolean includeCurrentContest = false;
	private Long totalCount;
	private LinkedList<Object[]> data;
	private String[] indexMapping;
	private static Long allStatusCount;

	@Override
	public String execute() throws Exception {
		code = 0;
		message = "success";
		Pair<Long, List<SolutionSimpleDTO>> result = dao.getSolutionList(username, language, judgerSource,
				problemNumber, statusCode, page, pageSize, orderByIndex, includeCurrentContest);
		indexMapping = new String[] { _("runId"), _("username"), _("judgerSource")+" "+ _("problemNumber"),
				_("statusDescription"), _("memory"), _("time"), _("language"), _("codeLength"), _("submitTime"),
				_("problemId"), _("problemTitle"), _("statusCode"), _("contestId") };
		data = new LinkedList<Object[]>();
		for (SolutionSimpleDTO s : result.second) {
			data.add(new Object[] { s.getRunId(), s.getUsername(), s.getJudgerSource() + " " + s.getPrublemNumber(),
					s.getStatusDescription(), s.getMemory(), s.getTime(), s.getLanguage(), s.getCodeLength(),
					s.getSubmitTime(), s.getProblemId(), s.getProblemTitle(), s.getStatusCode(), s.getContestId() });
		}
		totalCount = result.first;
		if ("".equals(judgerSource) && "".equals(username) && "".equals(language) && "".equals(problemNumber)
				&& statusCode == 0) {
			allStatusCount = result.first;
		}
		return SUCCESS;
	}

	public void setJudgerSource(String judgerSource) {
		if ("All".equals(judgerSource))
			judgerSource = "";
		this.judgerSource = judgerSource;
	}

	public void setPage(int page) {
		if (page < 0)
			page = 0;
		this.page = page;
	}

	public void setPageSize(int pageSize) {
		if (pageSize < 10 || pageSize > 200)
			page = 50;
		this.pageSize = pageSize;
	}

	public void setOrderByIndex(int orderByIndex) {
		this.orderByIndex = orderByIndex;
	}

	public Long getTotalCount() {
		return totalCount;
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

	public static Long getAllStatusCount() {
		return allStatusCount;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setProblemNumber(String problemNumber) {
		this.problemNumber = problemNumber;
	}

	public void setLanguage(String language) {
		if ("All".equals(language))
			language = "";
		this.language = language;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

}
