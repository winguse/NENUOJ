package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.util.Permission;
import cn.edu.nenu.acm.oj.util.Remark;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;


@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({
	@Result(name = "success", type="json"),
	@Result(name = "input", location="register-success.jsp"),
	@Result(name = "reject",type="redirect",location="error.action?type=1")
})
public class RegisterOperatingAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;
	private String username;
	private String password;
	private String password2;
	private String school;
	private String major;
	private String grade;
	private String nickname;
	private String email;
	private boolean agree;
	private Map<String, Object> session;
	
	private Integer code;
	private String message;
	
	@Autowired(required=true)
	private UserDAO userDAO;
	private String verifyCode;

	@Override
	public String execute() throws Exception {
		if (site.isEnableLoginVerifyCode() && (verifyCode == null || !verifyCode.equalsIgnoreCase((String)session.get("verifyCode")))) {
			code=VIRIFY_CODE_ERROR;
			message=getText("verify_code_error");
			return SUCCESS;
		}
		User newUser = new User();
		newUser.setUsername(username);
		String salt = site.generateSalt();
		password = site.hash(password, salt);
		nickname=(nickname==null||nickname.equals(""))?username:nickname;
		newUser.setPassword(password);
		newUser.setSalt(salt);
		newUser.setEmail(email);
		newUser.setSchool(school);
		newUser.setMajor(major);
		newUser.setGrade(grade);
		Remark remark = new Remark();
		remark.set("nickname", nickname);
		newUser.setRemark(remark);
		newUser.setSloved(0);
		newUser.setSubmitted(0);
		newUser.setPermission(Permission.DEFAULT_PERMISSION);
		userDAO.persist(newUser);
		UserSimpleDTO user=new UserSimpleDTO();
		user.setId(newUser.getId());
		user.setUsername(username);
		user.setNickname(nickname);
		user.setPermission(newUser.getPermission());
		session.put("user", user);
		code=0;
		message=_("welcome")+user.getNickname()+"!";
		return SUCCESS;
	}

	@RequiredStringValidator(key = "username_required")
	@FieldExpressionValidator(expression = "!isUsernameExist()", key = "username_exist")
	public void setUsername(String username) {
		this.username = username;
	}

	@Validations(fieldExpressions = {
			@FieldExpressionValidator(expression = "password.length()>=6 && password.length()<=64", key = "password_length"),
			@FieldExpressionValidator(expression = "!site.isWeekPassword(password)", key = "password_week") })
	public void setPassword(String password) {
		this.password = password;
	}

	@FieldExpressionValidator(expression = "agree", key = "agree_terms")
	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	@FieldExpressionValidator(expression = "password.equals(password2)", key = "password_retype")
	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	@RequiredStringValidator(key = "email_required")
	@EmailValidator(key = "email_required")
	public void setEmail(String email) {
		this.email = email;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@JSON(serialize=false)
	public boolean isUsernameExist(){
		return userDAO.isUsernameExist(username);
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
	}

	@JSON(serialize=false)
	public String getUsername() {
		return username;
	}

	@JSON(serialize=false)
	public String getPassword() {
		return password;
	}

	@JSON(serialize=false)
	public String getPassword2() {
		return password2;
	}

	@JSON(serialize=false)
	public String getSchool() {
		return school;
	}

	@JSON(serialize=false)
	public String getMajor() {
		return major;
	}

	@JSON(serialize=false)
	public String getGrade() {
		return grade;
	}

	@JSON(serialize=false)
	public String getNickname() {
		return nickname;
	}

	@JSON(serialize=false)
	public String getEmail() {
		return email;
	}

	@JSON(serialize=false)
	public boolean isAgree() {
		return agree;
	}

	@JSON(serialize=false)
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	public Integer getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
}
