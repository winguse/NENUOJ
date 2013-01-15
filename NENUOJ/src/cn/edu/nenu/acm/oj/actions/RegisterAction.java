package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Result;

@Result(name="success",location="register-success.jsp")
public class RegisterAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
