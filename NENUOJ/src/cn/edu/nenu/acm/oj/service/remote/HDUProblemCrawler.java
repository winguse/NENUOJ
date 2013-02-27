package cn.edu.nenu.acm.oj.service.remote;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.util.IProblemCrawler;

public class HDUProblemCrawler implements IProblemCrawler {

	public static final String judgerSource = "HDU";
	public static final String problemBaseUrl = "http://acm.hdu.edu.cn/showproblem.php?pid=";

	private String number;
	private Document document;

	@Override
	public final String getJudgerSource() {
		return judgerSource;
	}

	@Override
	public void crawl(String number) throws CrawlingException,RemoteProblemNotFoundException {
		this.number = number;
		try {
			document = Jsoup.connect(problemBaseUrl+number).get();
		} catch (IOException e) {
			e.printStackTrace();
			throw new CrawlingException("IOException: "+e.getMessage());
		}
		if(!document.title().equals("Problem - "+number)){
			throw new RemoteProblemNotFoundException();
		}
	}

	@Override
	public String getTitle() {
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
