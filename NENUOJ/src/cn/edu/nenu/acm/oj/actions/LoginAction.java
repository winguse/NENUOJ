package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;

import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.entitybeans.User;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Result(name = "success", type = "json")
public class LoginAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;

	private String username;
	private String password;
	private String verifyCode;

	@Autowired(required = true)
	private UserDAO userDAO;
	private Map<String, Object> session;

	private int code;
	private String message;

	@Override
	public String execute() throws Exception {
		if (site.isEnableLoginVerifyCode() && (verifyCode == null || !verifyCode.equalsIgnoreCase((String)session.get("verifyCode")))) {
			code=VIRIFY_CODE_ERROR;
			message=getText("verify_code_error");
			return SUCCESS;
		}
		User user = userDAO.findUserByUsername(username);
		if (user == null) {
			code = USERNAME_NOT_EXIST;
			message = getText("login_user_not_exist");
		} else if (!site.hash(password, user.getSalt()).equals(user.getPassword())) {
			code = PASSWORD_NOT_MATCH;
			message = getText("login_password_wrong");
		} else {
			code = CODE_SUCCESS;
			message = getText("login_welcome");
			session.put("user", user);
		}
		return SUCCESS;
	}

	@RequiredStringValidator(key = "username_required")
	public void setUsername(String username) {
		this.username = username;
	}

	@RequiredStringValidator(key = "password_required")
	public void setPassword(String password) {
		this.password = password;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	@JSON(serialize = false)
	public String getUsername() {
		return username;
	}

	@JSON(serialize = false)
	public String getPassword() {
		return password;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
