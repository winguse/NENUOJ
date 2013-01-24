package cn.edu.nenu.acm.oj.actions.problems;

import org.apache.struts2.convention.annotation.Result;

import cn.edu.nenu.acm.oj.actions.AbstractAction;

@Result(name="success",location="list-success.jsp")
public class ListAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
