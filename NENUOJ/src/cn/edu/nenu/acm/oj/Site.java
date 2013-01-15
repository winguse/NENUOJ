package cn.edu.nenu.acm.oj;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.inject.Inject;

@Repository
@Scope("singleton")
public class Site {

	private boolean enableHtmlCompress;

	public boolean isEnableHtmlCompress() {
		return enableHtmlCompress;
	}

	@Value("${switch.htmlcompress}")
	public void setEnableHtmlCompress(boolean enableHtmlCompress) {
		this.enableHtmlCompress = enableHtmlCompress;
	}

}
