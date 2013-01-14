package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Result;

@Result(name="success",location="home-success.jsp")
public class HomeAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
