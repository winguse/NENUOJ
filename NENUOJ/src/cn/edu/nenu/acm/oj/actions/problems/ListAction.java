package cn.edu.nenu.acm.oj.actions.problems;

import java.util.LinkedList;
import java.util.List;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;

@ParentPackage("struts-default")
@Result(name="success",location="list-success.jsp")
public class ListAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	
	@Autowired
	private GenericDAO dao;
	
	private List<String> judgerSourceList;
	
	@Override
	public String execute() throws Exception {
		judgerSourceList = new LinkedList<String>();
		for(Judger j :dao.findAll(Judger.class)){
			judgerSourceList.add(j.getSource());
		}
		return SUCCESS;
	}

	public List<String> getJudgeSourceList() {
		return judgerSourceList;
	}	
}
