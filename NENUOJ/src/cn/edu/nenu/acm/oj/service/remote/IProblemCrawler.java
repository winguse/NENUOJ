package cn.edu.nenu.acm.oj.service.remote;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;

public interface IProblemCrawler {

	public String getJudgerSource();

	public void crawl(String number) throws NetworkException,CrawlingException, RemoteProblemNotFoundException;

	public String getTitle() throws CrawlingException;

	public Integer getMemoryLimit() throws CrawlingException;

	public Integer getTimeLimit() throws CrawlingException;

	public String getDescription() throws CrawlingException;

	public String getInput() throws CrawlingException;

	public String getOutput() throws CrawlingException;

	public String getSampleIn() throws CrawlingException;

	public String getSampleOut() throws CrawlingException;

	public String getHint();

	public String getSource();

	public String getOriginalUrl();

	// public static

}
