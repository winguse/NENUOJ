package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("json-default")
@Result(name="success",type="json")
public class LoginAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	
	private String username;
	private String password;
	private String verifyCode;
	
	private int code;
	private int message;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public int getCode() {
		return code;
	}

	public int getMessage() {
		return message;
	}
	
	
}
