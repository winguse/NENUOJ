package cn.edu.nenu.acm.oj.service.impl;

import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import cn.edu.nenu.acm.oj.entitybeans.Judger;
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
		TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
		judgerQuery.setParameter("source", judgeSource);
		Judger judger = judgerQuery.getSingleResult();
		String problemNumber = null;
		String log = "";
		while (null != (problemNumber = queue.poll())) {
			try {
				Problem problem = null;
				TypedQuery<Problem> query = em.createQuery(
						"SELECT p FROM Problem p WHERE p.judger = :judger AND p.number = :number", Problem.class);
				query.setParameter("judger", judger);
				query.setParameter("number", problemNumber);
				
				query.getResultList();
				
				crawler.crawl(problemNumber);
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
