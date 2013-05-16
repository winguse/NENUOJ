package cn.edu.nenu.acm.oj.actions.contests.json;

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
import cn.edu.nenu.acm.oj.dao.ContestDAO;
import cn.edu.nenu.acm.oj.dto.ContestSimpleDTO;
import cn.edu.nenu.acm.oj.util.Pair;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json") })
public class ListAction extends AbstractJsonAction implements SessionAware {

	private static final long serialVersionUID = 1727087661137013184L;

	@Autowired
	private ContestDAO dao;

	private int page = 0;
	private int pageSize = 20;
	private String filterString = "";
	private int orderByIndex = 0;
	private Long totalCount;
	private LinkedList<Object[]> data;
	private String[] indexMapping;
	private static Long allContestsCount;
	
	private long permission;
	
	private Map<String, Object> session;
	
	@Override
	public String execute() throws Exception {
		code = 0;
		message = "success";
		Pair<Long, List<ContestSimpleDTO>> result = dao.getContestList(
				filterString, page, pageSize, orderByIndex);
		indexMapping = new String[] { _("ID"), _("Title"), _("Start Time"),
				_("Length"), _("Type"), _("Host User") };
		data = new LinkedList<Object[]>();
		for (ContestSimpleDTO c : result.second) {
			data.add(new Object[] { c.getId(), c.getTitle(), c.getStartTime(),
					c.getEndTime()-c.getStartTime(), c.getContestType(), c.getHostUsername() });
		}
		totalCount = result.first;
		if ("".equals(filterString)) {
			allContestsCount = result.first;
		}
		return SUCCESS;
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

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	public LinkedList<Object[]> getData() {
		return data;
	}

	public String[] getIndexMapping() {
		return indexMapping;
	}

	public Long getAllContestsCount() {
		return allContestsCount;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
