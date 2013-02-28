package cn.edu.nenu.acm.oj.service.remote;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import cn.edu.nenu.acm.oj.eto.LoginException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.SubmitException;
import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;


public class HDUProblemSubmitter implements IProblemSubmitter,ISolutionStatusCode {
	public static final String judgerSource = "HDU";
	public static final String homePage = "http://acm.hdu.edu.cn";
	public static final Map<String, String> languageMapping = new HashMap<String, String>() {
		{
			put("g++", "0");
			put("gcc", "1");
			put("c++", "2");
			put("c", "3");
			put("pascal", "4");
			put("java", "5");
		}
	};
	public static final Map <String,Integer> statusMapping = new HashMap<String,Integer>(){{
			put("Accepted",STATUS_ACCEPTED);
			put("Compilation Error",STATUS_COMPLIE_ERROR);
//			put("",STATUS_JUDGE_ERROR);
			put("Memory Limit Exceeded",STATUS_MEMORY_LIMITED_EXCEED);
			put("Output Limit Exceeded",STATUS_OUTPUT_LIMITED_EXCEED);
			put("Presentation Error",STATUS_PRESENTATION_ERROR);
//			put("",STATUS_PROCESSING);
			put("Runtime Error",STATUS_RUNTIME_ERROR);
			put("Time Limit Exceeded",STATUS_TIME_LIMITED_EXCEED);
			put("Wrong Answer",STATUS_WRONG_ANSWER);
		}
	};
	
	private String username;
	private String password;
	private Map<String, String> cookies;
	private int remoteRunId;
	private int memory;
	private int time;
	private String statusDescription;
	
	public HDUProblemSubmitter(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public String getJudgerSource() {
		return judgerSource;
	}

	@Override
	public void login() throws NetworkException, LoginException {
		try {
			Connection connection = Jsoup.connect(homePage + "/userloginex.php?action=login")
					.data("username", username).data("userpass", password);
			connection.request().followRedirects(false);
			Document document = connection.post();
			System.out.println(document.text());
			if (connection.response().statusCode() != 302) {
				throw new LoginException("Login error.");
			}
			cookies = connection.response().cookies();
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("Maybe Network Error, IOException:" + e.getMessage());
		}
	}

	@Override
	public void submit(String problem, String sourceCode, String language) throws NetworkException,SubmitException {
		String strLanguage = languageMapping.get(language.toLowerCase());
		if (strLanguage == null) {
			throw new SubmitException("Not Supported Language.");
		}
		if (sourceCode.length() < 50 || sourceCode.length() > 65536) {
			throw new SubmitException(
					"Code length is improper! Make sure your code length is longer than 50 and not exceed 65536 Bytes.");
		}
		if(cookies==null){
			throw new SubmitException("Run Login Method First.");
		}
		try {
			Connection connection = Jsoup.connect(homePage + "/submit.php?action=submit").data("problemid", problem)
					.data("language", strLanguage).data("usercode", sourceCode).cookies(cookies);
			connection.request().followRedirects(false);
			connection.post();
			if (connection.response().statusCode() != 302) 
				throw new SubmitException("Submit Error. Unexpected Status Code.");
			String location=connection.response().header("Location");
			if(!"status.php".equals(location))
				throw new SubmitException("Submit Error. Not redirect to status.php but "+location+" .");//userloginex.php ---> not login
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("Maybe Network Error, Cannot submit, IOException:" + e.getMessage());
		}

	}

	@Override
	public boolean getResult() throws NetworkException {
		try {
			Connection connection = Jsoup.connect(homePage+"/status.php?first=&pid=&user="+username+"&lang=0&status=0").cookies(cookies);
			Element result = connection.get().select("form+tr").first();
			statusDescription=result.select("td:eq(2)").first().text();
			remoteRunId=Integer.parseInt(result.select("td:eq(0)").first().text());
			if("Compiling".equals(statusDescription)||"Queuing".equals(statusDescription))
				return true;
			time=Integer.parseInt(result.select("td:eq(4)").first().text().replace("MS", ""));
			memory=Integer.parseInt(result.select("td:eq(5)").first().text().replace("K", ""));
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("Maybe Network Error, Cannot get result, IOException:" + e.getMessage());
		}
		return false;
	}

	@Override
	public String getAdditionalInformation() throws NetworkException {
		try {
			Document document=Jsoup.connect(homePage+"/viewerror.php?rid="+remoteRunId).cookies(cookies).get();
			Element pre=document.select("pre").first();
			return pre==null?"":pre.text();
		} catch (IOException e) {
			e.printStackTrace();
			throw new NetworkException("Maybe Network Error, Cannot fetch additional information, IOException:" + e.getMessage());
		}
	}

	@Override
	public String getStatusDescription() {
		return statusDescription;
	}

	@Override
	public int getRemoteRunId() {
		return remoteRunId;
	}

	@Override
	public int getTime() {
		return time;
	}

	@Override
	public int getMemory() {
		return memory;
	}

	@Override
	public int getStatus() throws SubmitException {
		Integer status = statusMapping.get(statusDescription.replaceAll("\\(.+\\)", "").trim());
		if(status == null)
			throw new SubmitException("Cannot find correct status code.");
		return status;
	}

}
