package cn.edu.nenu.acm.oj.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;
import cn.edu.nenu.acm.oj.util.Remark;

@Component
@Scope("prototype")
@Qualifier("crawlWorker")
public class CrawlWorker extends Thread {

	protected static Logger log = LogManager.getLogger("CrawlWorker");
	private static int activeWorkerCount = 0;
	private static HashMap<Long, CrawlWorker> us = new HashMap<Long, CrawlWorker>();

	private IProblemCrawler crawler;
	private LinkedBlockingQueue<String> queue;

	@Autowired
	private GenericDAO dao;

	private ProblemDescription problemDescription = null;
	private Problem problem = null;
	private Judger judger = null;
	private boolean canRunning = true;

	/**
	 * Init the crawler worker
	 * 
	 * @param crawler
	 * @param queue
	 */
	public void init(IProblemCrawler crawler, LinkedBlockingQueue<String> queue) {
		this.queue = queue;
		this.crawler = crawler;
		// load judger
		judger = dao.findByColumn("source", crawler.getJudgerSource(), Judger.class).get(0);
	}

	@Override
	public void run() {
		setActive(this);
		try {
			String problemNumber = null;
			while (null != (problemNumber = queue.poll())) {
				if (!canRunning)
					return;
				try {
					log.info(problemNumber + ": ");
					crawler.crawl(problemNumber);
					if (!canRunning)
						return;
					// problem number contains # marked as locked problem
					boolean locked = problemNumber.contains("#");
					problemNumber = problemNumber.replace("#", "");
					List<Problem> lstProblem = dao.namedQuery("Problem.findByJudgerAndNumber", new String[] { "judger",
							"number" }, new Object[] { judger, problemNumber }, Problem.class);
					if (!canRunning)
						return;
					if (lstProblem.size() > 0) {
						problem = lstProblem.get(0);
						log.info("Re-crawl " + problemNumber);
						// TODO change to named query:
						// int systemCrawlId =
						// (Integer)problem.getRemark().get("SystemCrawlId");
						if (!canRunning)
							return;
						List<ProblemDescription> lstPD = dao.namedQuery("ProblemDescription.findSystemCrawl",
								new String[] { "problem" }, new Object[] { problem }, ProblemDescription.class);
						if (lstPD.size() > 0)
							problemDescription = lstPD.get(0);
					} else {
						log.info("Newly crawl " + problemNumber);
						problem = new Problem();
						problem.setJudger(judger);
						problem.setJudgingType(Problem.REMOTE_JUDGE);
						// crawler is noramlly remote judge
						problem.setMemoryLimit(crawler.getMemoryLimit());
						problem.setNumber(problemNumber);
						problem.setSource(crawler.getSource());
						problem.setTimeLimit(crawler.getTimeLimit());
						problem.setTitle(crawler.getTitle());
						// problem.setProblemDescriptions(problemDescriptions);
					}
					if (problemDescription == null) {
						problemDescription = new ProblemDescription();
						problemDescription.setProblem(problem);// **?
						problem.getProblemDescriptions().add(problemDescription);// **
						Remark remark = new Remark();
						problem.setRemark(remark);
					}
					problemDescription.setDescription(crawler.getDescription());
					problemDescription.setHint(crawler.getHint());
					problemDescription.setInput(crawler.getInput());
					problemDescription.setLocked(locked);
					problemDescription.setOutput(crawler.getOutput());
					problemDescription.setSampleIn(crawler.getSampleIn());
					problemDescription.setSampleOut(crawler.getSampleOut());
					problemDescription.setTitle(crawler.getTitle());
					problemDescription.getRemark().set("versionMark", "System Crawl");
					problemDescription.setRemark(problemDescription.getRemark());
					problem.setLocked(locked);
					this.persistProblem();
					log.info("Crawled successfully");
					if (!canRunning)
						return;
				} catch (NetworkException e) {
					e.printStackTrace();
					log.error("NetworkException:" + e.getMessage());
				} catch (CrawlingException e) {
					e.printStackTrace();
					log.error("CrawlingException:" + e.getMessage());
				} catch (RemoteProblemNotFoundException e) {
					e.printStackTrace();
					log.error("RemoteProblemNotFoundException:" + e.getMessage());
				} finally {
				}
			}
		} catch (Exception e) {
			log.error("Unknow Exception: " + e.getMessage());
		} finally {
			setDisactive(this);
		}
	}

	/**
	 * Persisting a problem. Need transaction, so write separate.
	 */
	@Transactional
	public void persistProblem() {
		if (problem == null || problemDescription == null)
			return;
		dao.persist(problem);
		dao.persist(problemDescription);
	}

	public static synchronized int getActiveWorkerCount() {
		return activeWorkerCount;
	}

	private static synchronized void setActive(CrawlWorker worker) {
		activeWorkerCount++;
		us.put(worker.getId(), worker);
	}

	private static synchronized void setDisactive(CrawlWorker worker) {
		activeWorkerCount--;
		us.remove(worker.getId());
	}

	public static synchronized void setAllStop() {
		for (Map.Entry<Long, CrawlWorker> e : us.entrySet()) {
			e.getValue().setStop();
		}
	}

	public void setStop() {
		canRunning = false;
		this.interrupt();
	}
}
