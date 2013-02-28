package cn.edu.nenu.acm.oj.actions.problems;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

import cn.edu.nenu.acm.oj.actions.AbstractAction;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"), @InterceptorRef("jsonValidationWorkflowStack") })
@Result(name = "success", type = "json")
public class AddRemoteProblemAction extends AbstractAction {

	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
