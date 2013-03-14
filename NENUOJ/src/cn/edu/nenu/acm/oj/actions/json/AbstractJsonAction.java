package cn.edu.nenu.acm.oj.actions.json;

import org.apache.struts2.convention.annotation.ParentPackage;

import cn.edu.nenu.acm.oj.actions.AbstractAction;

@ParentPackage("json-default")
public abstract class AbstractJsonAction extends AbstractAction {
	protected int code;
	protected String message;
	
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
}
