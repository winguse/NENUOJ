package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.service.impl.JudgeService;
import cn.edu.nenu.acm.oj.service.impl.SubmitWorker;

@ParentPackage("json-default")
@Result(name="success",type="json")
@Namespace("/")
public class TestAction extends AbstractAction implements SessionAware {

	private static final long serialVersionUID = -8077897542384482842L;
	
	private Map<String,Object> session;
	
	private int code;
	private String message;
	
	@Autowired
	private JudgeService service;
	
	@Autowired
	private SubmitWorker submitWorker;
	
	@Override
	public String execute() throws Exception {
		code=CODE_SUCCESS;
		message=service.getCrawler("HDU").getJudgerSource()+submitWorker.getId();
		return SUCCESS;
	}
	
	@Override
	public void setSession(Map<String, Object> session) {
		this.session=session;
	}

	public int getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
	
	
	
}
