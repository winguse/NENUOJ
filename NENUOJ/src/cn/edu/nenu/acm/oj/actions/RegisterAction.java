package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;


@Results({
	@Result(name = "success", location = "register-success.jsp"),
	@Result(name = "profiles", location="user-profiles.action", type="redirect")
})
public class RegisterAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -700434563092068535L;

	private Map<String, Object> session;
	
	@Override
	public String execute() throws Exception {
		if(session.get("user") instanceof UserSimpleDTO)
			return "profiles";
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

}
