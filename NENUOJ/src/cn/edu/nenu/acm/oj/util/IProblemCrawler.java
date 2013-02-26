package cn.edu.nenu.acm.oj.util;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;

public interface IProblemCrawler {
	
	public String getJudgerSource();
	public void crawl(String number) throws CrawlingException,RemoteProblemNotFoundException;
	public String getTitle();
	public Integer getMemoryLimit();
	public Integer getTimeLimit();
	public String getDescription();
	public String getInput();
	public String getOutput();
	public String getSampleIn();
	public String getSampleOut();
	public String getHint();
	public String getOriginalUrl();
	
	//public static 
	
}
