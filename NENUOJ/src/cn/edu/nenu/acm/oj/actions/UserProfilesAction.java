package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.User;
@ParentPackage("default")
@Results({
	@Result(name="success",location="user-profiles-success.jsp")
})
@InterceptorRefs({ 
	@InterceptorRef("defaultStack")
	,@InterceptorRef("permissionInterceptor")
})
@Namespace("/")
public class UserProfilesAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;

	private Map<String, Object> session;

	private String oldPassword;
	private String password;
	private String password2;
	private String school;
	private String major;
	private String grade;
	private String nickname;
	private String email;
	
	@Autowired(required=true)
	private UserDAO userDAO;
	
	@Override
	public String execute() throws Exception {
		UserSimpleDTO user=(UserSimpleDTO) session.get("user");
		User u=userDAO.findUserByUsername(user.getUsername());
		nickname=user.getNickname();
		school=u.getSchool();
		major=u.getMajor();
		grade=u.getGrade();
		email=u.getEmail();
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}

	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

}
