package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

@Results({
	@Result(name = "success", location = "register-success.jsp")
})
public class RegisterAction extends AbstractAction {

	private static final long serialVersionUID = -700434563092068535L;

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
