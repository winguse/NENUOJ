package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;

@ParentPackage("default")
@Result(name="success",location="show-user-success.jsp")
@Namespace("/")
public class ShowUserAction extends AbstractAction{

	private static final long serialVersionUID = 1L;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

}
