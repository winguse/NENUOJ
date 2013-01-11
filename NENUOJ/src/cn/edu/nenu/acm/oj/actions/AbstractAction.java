package cn.edu.nenu.acm.oj.actions;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;

import com.opensymphony.xwork2.Action;

public abstract class AbstractAction implements Action {

	@Autowired(required=true)
	private Site site;
	
	public Site getSite() {
		return site;
	}

	public void setSite(Site site) {
		this.site = site;
	}
}
