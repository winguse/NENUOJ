package cn.edu.nenu.acm.oj.actions;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;

@Result(name="success",location="error-success.jsp")
@Namespace("/")
public class ErrorAction extends AbstractAction {
	private static final long serialVersionUID = 7645488761317045757L;

	private String type;
	
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
}
