package cn.edu.nenu.acm.oj.actions;


public abstract class AbstractJsonAction extends AbstractAction {
	protected Integer code = STATUS_ERROR;
	protected String message = _("Unknow Error!");
	public abstract String getMessage();
	public abstract Integer getCode();
}
