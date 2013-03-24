package cn.edu.nenu.acm.oj.actions.problems;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;

@ParentPackage("struts-default")
@Result(name = "success", location = "status-success.jsp")
public class StatusAction extends AbstractAction implements ISolutionStatusCode {

	private static final long serialVersionUID = -8077897542384482842L;
	private static List<String> languageList;
	private static long cacheTimestamp = 0L;

	@Autowired
	private GenericDAO dao;

	private List<String> judgerSourceList;

	@Override
	public String execute() throws Exception {
		long nowTimestamp = new Date().getTime();
		if (nowTimestamp - cacheTimestamp > 1000 * 3600) {
			languageList = dao.namedQuery("Solution.findDistinctLanguage", null, null, String.class);
			languageList.add(0, "All");
			cacheTimestamp = nowTimestamp;
		}
		judgerSourceList = new LinkedList<String>();
		judgerSourceList.add(_("All"));
		for (Judger j : dao.findAll(Judger.class)) {
			judgerSourceList.add(j.getSource());
		}
		return SUCCESS;
	}

	public List<String> getJudgerSourceList() {
		return judgerSourceList;
	}

	public List<String> getLanguageList() {
		return languageList;
	}
	
	public Map<Integer,String> getStatusMapping(){
		return statusMapping;
	}

}
