package cn.edu.nenu.acm.oj.actions;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.inject.Inject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.struts2.convention.annotation.ParentPackage;

public abstract class AbstractAction extends ActionSupport {

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

	@Inject
	public void setConfiguration(Configuration config) {
		System.out.println("still inject");
		AbstractAction.configuration = config;
	}
}
