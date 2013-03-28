package cn.edu.nenu.acm.oj.actions.contests.json;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Results({ @Result(name = "success", type = "json"),
		@Result(name = "input", type = "redirectAction", params = { "actionName", "add", "namespace", "/contests" }) })
public class AddAction extends AbstractJsonAction {
	
	
	
	@Override
	public String execute(){
		
		return SUCCESS;
	}
	
}
