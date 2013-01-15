package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.opensymphony.xwork2.validator.annotations.RequiredStringValidator;
import com.opensymphony.xwork2.validator.annotations.ValidatorType;
import com.opensymphony.xwork2.validator.annotations.ExpressionValidator;
import com.opensymphony.xwork2.validator.annotations.Validations;
import com.opensymphony.xwork2.validator.annotations.FieldExpressionValidator;

@ParentPackage("json-default")
@InterceptorRefs({
	@InterceptorRef("i18n"),
	@InterceptorRef("jsonValidationWorkflowStack")
})
@Results({@Result(name = "success", location = "register.jsp")
,@Result(name = "input", location="register.action", type="redirect")
})
@Validations(
	requiredStrings = {
		@RequiredStringValidator(fieldName = "username", type = ValidatorType.FIELD, key = "username_required", message=""),
		@RequiredStringValidator(fieldName = "password", type = ValidatorType.FIELD, key="helloWorld")
	},
	expressions = { 
		@ExpressionValidator(expression = "password.trim().length() > 5",  key="helloWorld")
	},
	fieldExpressions = {
		@FieldExpressionValidator(fieldName = "password", expression = "password.trim().length() > 6",  key="helloWorld"),
		@FieldExpressionValidator(fieldName = "agree", expression = "agree == true", message = "Accept the Agreement.") 
	}
)
public class RegisterSubmitAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	private String username;
	private String password;
	private String password2;
	private boolean agree;
	
	@Override
	public String execute() throws Exception {
        username = "";
        password = "";
        agree = false;
		return SUCCESS;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public boolean isAgree() {
		return agree;
	}

	public void setAgree(boolean agree) {
		this.agree = agree;
	}

	public String getPassword2() {
		return password2;
	}

	public void setPassword2(String password2) {
		this.password2 = password2;
	}
	
	
}
