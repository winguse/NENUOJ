package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
@ParentPackage("json-default")
@Action("json")
@Result(name="success",type="json")
public class JsonTestAction extends AbstractAction {

	private String name;
	
	
	@Override
	public String execute() throws Exception {
		name="winguse";
		return SUCCESS;
	}


	public String getName() {
		return name;
	}

}
