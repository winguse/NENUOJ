package cn.edu.nenu.acm.oj;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import com.opensymphony.xwork2.config.Configuration;
import com.opensymphony.xwork2.inject.Inject;

@Repository
@Scope("singleton")
public class Site {

	private boolean enableHtmlCompress;
	private boolean enableLoginVerifyCode;
	private boolean enableRegisterVerifyCode;
	private String globalSalt;
	private Random random = new Random();

	public String getGlobalSalt() {
		return globalSalt;
	}

	@Value("${security.globalSalt}")
	public void setGlobalSalt(String globalSalt) {
		this.globalSalt = globalSalt;
	}
	
	public boolean isEnableLoginVerifyCode() {
		return enableLoginVerifyCode;
	}

	@Value("${security.login.verifycode}")
	public void setEnableLoginVerifyCode(boolean enableLoginVerifyCode) {
		this.enableLoginVerifyCode = enableLoginVerifyCode;
	}

	public boolean isEnableHtmlCompress() {
		return enableHtmlCompress;
	}

	@Value("${switch.htmlcompress}")
	public void setEnableHtmlCompress(boolean enableHtmlCompress) {
		this.enableHtmlCompress = enableHtmlCompress;
	}

	public boolean isEnableRegisterVerifyCode() {
		return enableRegisterVerifyCode;
	}

	@Value("${security.register.verifycode}")
	public void setEnableRegisterVerifyCode(boolean enableRegisterVerifyCode) {
		this.enableRegisterVerifyCode = enableRegisterVerifyCode;
	}

	/**
	 * Determine weather the password is week.
	 * 
	 * @param password
	 * @return true if the password is week
	 */
	public boolean isWeekPassword(String password) {
		// TODO
		return false;
	}

	public String hash(String string, String salt) {
		String method = "SHA";
		String hex = "i.love_shuxIaO!~";// "1234567890abcdef"
		string = salt + string + globalSalt;
		String s = "";
		MessageDigest mdTemp = null;
		try {
			mdTemp = MessageDigest.getInstance(method);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return string;
		}
		byte[] md = mdTemp.digest(string.getBytes());
		for (int i = 0; i < md.length; i++) {
			s += hex.charAt(md[i] >>> 4 & 0xf);
			s += hex.charAt(md[i] & 0xf);
		}
		return s;
	}
	public String generateSalt(){
		return Long.toHexString(random.nextLong());
	}
}
