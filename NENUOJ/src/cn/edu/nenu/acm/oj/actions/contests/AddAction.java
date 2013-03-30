package cn.edu.nenu.acm.oj.actions.contests;

import org.apache.struts2.convention.annotation.Result;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.statuscode.IContestType;

@Result(name="success",location="add-success.jsp")
public class AddAction extends AbstractAction implements IContestType {

	private static final long serialVersionUID = -8077897542384482842L;
	@Override
	public String execute() throws Exception {
		return SUCCESS;
	}
}
