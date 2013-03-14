package cn.edu.nenu.acm.oj.actions;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;
import cn.edu.nenu.acm.oj.statuscode.ILoginStatusCode;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAction extends ActionSupport implements ILoginStatusCode {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_ERROR = 1;

	private static final long serialVersionUID = 1L;

	protected static Logger log = LogManager.getLogger("Actions");
	protected static Configuration configuration;
	protected Site site;

	public Site getSite() {
		return site;
	}

	@Autowired(required = true)
	public void setSite(Site site) {
		this.site = site;
	}

	@Autowired
	public void setConfiguration(Configuration config) {
		AbstractAction.configuration = config;
	}
	
	public String _(String aTextName){
		return super.getText(aTextName);
	}
}
