package cn.edu.nenu.acm.oj.service.impl;

import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.hibernate.ejb.criteria.CriteriaQueryImpl;

import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;

public class CrawlWorker extends Thread {

	private IProblemCrawler crawler;
	private LinkedBlockingQueue<String> queue;
	private EntityManager em;

	public CrawlWorker(IProblemCrawler crawler, LinkedBlockingQueue<String> queue, EntityManager em) {
		this.queue = queue;
		this.crawler = crawler;
		this.em = em;
	}

	@Override
	public void run() {
		String judgeSource = crawler.getJudgerSource();
		String problemNumber;
		String log="";
		while (null != (problemNumber = queue.poll())) {
			try {
				crawler.crawl(problemNumber);
				Problem problem = null;
			} catch (NetworkException e) {
				e.printStackTrace();
			} catch (CrawlingException e) {
				e.printStackTrace();
			} catch (RemoteProblemNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

}
