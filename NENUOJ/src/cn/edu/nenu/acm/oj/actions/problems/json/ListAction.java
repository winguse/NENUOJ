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
import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.util.Pair;

@ParentPackage("json-default")
@InterceptorRefs({
	@InterceptorRef("i18n"),
	@InterceptorRef("jsonValidationWorkflowStack")
})
@Results({ @Result(name = "success", type = "json") })
public class ListAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = -1246689012240563630L;

	@Autowired
	private ProblemDAO dao;

	private String judgerSource = "";
	private int page = 0;
	private int pageSize = 50;
	private String filterString = "";
	private int orderByIndex = 0;
	private boolean includeLocked = false;
	private Long totalCount;
	private LinkedList<Object[]> data;
	private String[] indexMapping;
	private static Long allProblemCount;
	
	@Override
	public String execute() throws Exception {
		code = 0;
		message = "success";
		Pair<Long, List<ProblemSimpleDTO>> result = dao.getProblemList(judgerSource, filterString, page, pageSize,
				includeLocked, orderByIndex);
		indexMapping = new String[] { _("judger_source"), _("problem_number"), _("problem_title"), _("accepted"),
				_("submitted"), _("is_locked"), _("source"),_("id") };
		data = new LinkedList<Object[]>();
		for (ProblemSimpleDTO p : result.second) {
			data.add(new Object[] { p.getJudgerSource(), p.getNumber(), p.getTitle(), p.getAccepted(),
					p.getSubmitted(), p.getLocked(), p.getSource(), p.getId() });
		}
		totalCount = result.first;
		if("".equals(judgerSource)&&"".equals(filterString)){
			allProblemCount = result.first;
		}
		return SUCCESS;
	}

	public void setJudgerSource(String judgerSource) {
		if (judgerSource == null)
			return;
		if (judgerSource.length() > 10)
			judgerSource = judgerSource.substring(0, 10);
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

	public void setFilterString(String filterString) {
		System.out.println(filterString);
		if (filterString == null)
			return;
		if (filterString.length() > 50)
			filterString = filterString.substring(0, 50);
		System.out.println(filterString);
		this.filterString = filterString;
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
		// TODO set includeLocked if the user logined and has the permission
	}

	public LinkedList<Object[]> getData() {
		return data;
	}

	public String[] getIndexMapping() {
		return indexMapping;
	}

	public Long getAllProblemCount() {
		return allProblemCount;
	}

}
