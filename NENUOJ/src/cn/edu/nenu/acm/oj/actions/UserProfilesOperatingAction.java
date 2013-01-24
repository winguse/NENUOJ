package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.json.annotations.JSON;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.util.Remark;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;


@ParentPackage("default")
@InterceptorRefs({ 
	@InterceptorRef("i18n"), 
	@InterceptorRef("jsonValidationWorkflowStack")
	,@InterceptorRef("permissionInterceptor")
})
@Results({
	@Result(name = "success", type="json"),
	@Result(name = "input", location="user-profiles-success.jsp")
})
@Namespace("/")
public class UserProfilesOperatingAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;
	private String oldPassword;
	private String password;
	private String password2;
	private String school;
	private String major;
	private String grade;
	private String nickname;
	private String email;
	private Map<String, Object> session;
	
	private Integer code;
	private String message;
	
	@Autowired(required=true)
	private UserDAO userDAO;
	private String verifyCode;

	@Override
	public String execute() throws Exception {
		UserSimpleDTO user=(UserSimpleDTO) session.get("user");
		User currentUser = userDAO.findUserByUsername(user.getUsername());
		if(currentUser==null){
			session.remove("user");
			return "reject";
		}
		if(password!=null&&!password.equals("")){
			if(site.hash(oldPassword, currentUser.getSalt()).equals(currentUser.getPassword())){
				String salt = site.generateSalt();
				password = site.hash(password, salt);
				currentUser.setPassword(password);
				currentUser.setSalt(salt);
			}else{
				code=PASSWORD_NOT_MATCH;
				message=_("old_password_needed_to_update_password");
				return SUCCESS;
			}
		}

		nickname=(nickname==null||nickname.equals(""))?user.getUsername():nickname;
		currentUser.setEmail(email);
		currentUser.setSchool(school);
		currentUser.setMajor(major);
		currentUser.setGrade(grade);
		Remark remark = new Remark();
		remark.set("nickname", nickname);
		currentUser.setRemark(remark);

		userDAO.merge(currentUser);
		session.put("user", new UserSimpleDTO(currentUser));
		code=0;
		message=_("profile_updated")+currentUser.getUsername()+"!";
		return SUCCESS;
	}

	@Validations(fieldExpressions = {
			@FieldExpressionValidator(expression = "password.length()==0||(password.length()>=6 && password.length()<=64)", key = "password_length"),
			@FieldExpressionValidator(expression = "!site.isWeekPassword(password)", key = "password_week") })
	public void setPassword(String password) {
		this.password = password;
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

	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
		
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
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
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
