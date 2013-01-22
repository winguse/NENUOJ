package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;

@ParentPackage("json-default")
@Result(name="success",type="json")
public class LogoutAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;
	
	private Map<String,Object> session;
	
	private int code;
	private String message;
	
	@Override
	public String execute() throws Exception {
		session.remove("user");
		code=CODE_SUCCESS;
		message=_("log_out_message");
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
