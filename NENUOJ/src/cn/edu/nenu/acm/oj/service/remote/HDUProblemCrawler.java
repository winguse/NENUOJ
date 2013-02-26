package cn.edu.nenu.acm.oj.service.remote;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.util.IProblemCrawler;

public class HDUProblemCrawler implements IProblemCrawler {

	public static final String judgerSource = "HDU";
	public static final String problemBaseUrl = "http://acm.hdu.edu.cn/showproblem.php?pid=";

	private String number;
	private String html;

	@Override
	public final String getJudgerSource() {
		return judgerSource;
	}

	@Override
	public void crawl(String number) throws CrawlingException,RemoteProblemNotFoundException {
		this.number = number;
		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet(problemBaseUrl + number);
		try {
			HttpResponse response = httpClient.execute(httpGet);
			HttpEntity entity = response.getEntity();
			html = EntityUtils.toString(entity);
			EntityUtils.consume(entity);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CrawlingException("IOException: "+e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			throw new CrawlingException("Other exception: "+e.getMessage());
		} finally {
			httpGet.releaseConnection();
		}
		
	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getMemoryLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Integer getTimeLimit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getInput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOutput() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSampleIn() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSampleOut() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getHint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getOriginalUrl() {
		return problemBaseUrl+number;
	}

}
