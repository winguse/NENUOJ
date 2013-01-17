package cn.edu.nenu.acm.oj.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.util.Remark;

import com.opensymphony.xwork2.validator.annotations.EmailValidator;
import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"),
		@InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", location = "register-success.jsp"),
		@Result(name = "input", location = "register.jsp") })
public class RegisterSubmitAction extends AbstractAction {

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

	@Autowired(required = true)
	private EntityManagerFactory emf;

	@Override
	public String execute() throws Exception {
		EntityManager em = emf.createEntityManager();
		User newUser = new User();
		newUser.setUsername(username);
		String salt = site.generateSalt();
		password = site.hash(password, salt);
		newUser.setPassword(password);
		newUser.setSalt(salt);
		newUser.setEmail(email);
		newUser.setSchool(school);
		newUser.setMajor(major);
		newUser.setGrade(grade);
		Remark remark = new Remark();
		remark.set("nickname", nickname);
		newUser.setRemark(remark);
		em.getTransaction().begin();
		try {
			em.persist(newUser);
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			em.getTransaction().rollback();
			addActionError("SQL Exception:" + e.getMessage());
			return INPUT;
		} finally {
			em.close();
		}
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

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getPassword2() {
		return password2;
	}

	public String getSchool() {
		return school;
	}

	public String getGrade() {
		return grade;
	}

	public String getNickname() {
		return nickname;
	}

	public String getEmail() {
		return email;
	}

	public boolean isAgree() {
		return agree;
	}

	public EntityManagerFactory getEmf() {
		return emf;
	}

	public void setEmf(EntityManagerFactory emf) {
		this.emf = emf;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public boolean isUsernameExist() {
		int resultCount = 0;
		EntityManager em = emf.createEntityManager();
		Query query = em.createQuery("select u from User u where u.username=?");
		query.setParameter(1, username);
		resultCount = query.getResultList().size();
		return resultCount > 0;
	}
}
