package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

@Result(name="success",location="home-success.jsp")
@Namespace("/")
public class HomeAction extends AbstractAction {

	private static final long serialVersionUID = 4095476766908903165L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
