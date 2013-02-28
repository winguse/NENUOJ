package cn.edu.nenu.acm.oj.test;

import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.service.remote.HDUProblemCrawler;
import cn.edu.nenu.acm.oj.service.remote.IProblemCrawler;

public class CrawlerTest {

	public CrawlerTest() {
	}

	public static void main(String args[]) {
		IProblemCrawler c = new HDUProblemCrawler();
		try {
			c.crawl("44913");
			System.out.println(c.getTitle());
			System.out.println(c.getDescription());
			System.out.println(c.getInput());
			System.out.println(c.getOutput());
			System.out.println(c.getSampleIn());
			System.out.println(c.getSampleOut());
			System.out.println(c.getHint());
			System.out.println(c.getSource());
			System.out.println(c.getOriginalUrl());
		} catch (NetworkException e) {
			e.printStackTrace();
		} catch (CrawlingException e) {
			e.printStackTrace();
		} catch (RemoteProblemNotFoundException e) {
			e.printStackTrace();
		}

	}

}
