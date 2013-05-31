package cn.edu.nenu.acm.oj.actions;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.statuscode.IJsonStatusCode;
import cn.edu.nenu.acm.oj.statuscode.ISessionField;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.config.Configuration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractAction extends ActionSupport implements
		IJsonStatusCode, ISessionField {

	public static final int STATUS_SUCCESS = 0;
	public static final int STATUS_ERROR = 1;

	private static final long serialVersionUID = 1L;

	protected static Logger log = LogManager.getLogger("Actions");
	protected Site site;

	public Site getSite() {
		return site;
	}

	@Autowired(required = true)
	public void setSite(Site site) {
		this.site = site;
	}

	public String _(String aTextName) {
		return super.getText(aTextName);
	}

	protected UserSimpleDTO getUser(Map<String, Object> session) {
		Object obj = session.get(USER);
		if (obj != null && obj instanceof UserSimpleDTO)
			return (UserSimpleDTO) obj;
		return new UserSimpleDTO();
	}
}
