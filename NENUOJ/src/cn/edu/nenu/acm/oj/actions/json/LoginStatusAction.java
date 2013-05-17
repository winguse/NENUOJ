package cn.edu.nenu.acm.oj.actions.json;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n") })
@Results({ @Result(name = "success", type = "json") })
public class LoginStatusAction extends AbstractJsonAction implements
		SessionAware {

	private static final long serialVersionUID = 8422961954267139393L;
	private UserSimpleDTO user = null;
	private Map<String, Object> session;

	@Override
	public String execute() throws Exception {
		code = CODE_SUCCESS;
		message = "";
		Object tmp = session.get("user");
		if (tmp != null && tmp instanceof UserSimpleDTO) {
			user = (UserSimpleDTO) tmp;
		} else {
			code = USERNAME_NOT_LOGINED;
		}
		return SUCCESS;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public UserSimpleDTO getUser() {
		return user;
	}
}
