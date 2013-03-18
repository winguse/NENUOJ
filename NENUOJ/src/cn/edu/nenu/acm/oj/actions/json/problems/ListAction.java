package cn.edu.nenu.acm.oj.actions.json.problems;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.dao.ProblemDAO;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n") })
@Results({ @Result(name = "success", type = "json") })
public class ListAction extends AbstractAction implements SessionAware {

	@Autowired
	private ProblemDAO dao;

	private String judgerSource = "";
	private int page = 0;
	private int pageSize = 50;
	private String filterString = "";
	private int orderByIndex = 0;
	private boolean includeLocked = false;
	
	@Override
	public String execute() throws Exception {
		dao.getProblemList(judgerSource, filterString, page, pageSize,includeLocked,orderByIndex);
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
		if (filterString == null)
			return;
		if (filterString.length() > 50)
			filterString = filterString.substring(0, 50);
		this.filterString = filterString;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		//TODO set includeLocked if the user logined and has the permission
	}

}
