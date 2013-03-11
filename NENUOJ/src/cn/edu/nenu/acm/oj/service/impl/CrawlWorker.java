package cn.edu.nenu.acm.oj.service.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
public class CrawlWorker extends Thread {

	protected static Logger log = LogManager.getLogger("CrawlWorker");
	private static int activeWorkerCount = 0;
	private IProblemCrawler crawler;
	private LinkedBlockingQueue<String> queue;
	@PersistenceContext
	private EntityManager em;

	private ProblemDescription problemDescription = null;
	private Problem problem = null;
	private Judger judger = null;

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
		String judgeSource = crawler.getJudgerSource();
		TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
		judgerQuery.setParameter("source", judgeSource);
		judger = judgerQuery.getSingleResult();
	}

	@Override
	public void run() {
		setActive();
		try {
			String problemNumber = null;
			while (null != (problemNumber = queue.poll())) {
				try {
					log.info(problemNumber + ": ");
					crawler.crawl(problemNumber);
					// problem number contains # marked as locked problem
					boolean locked = problemNumber.contains("#");
					problemNumber = problemNumber.replace("#", "");
					TypedQuery<Problem> query = em.createNamedQuery("Problem.findByJudgerAndNumber", Problem.class);
					query.setParameter("judger", judger);
					query.setParameter("number", problemNumber);
					List<Problem> lstProblem = query.getResultList();
					if (lstProblem.size() > 0) {
						problem = lstProblem.get(0);
						log.info("Re-crawl " + problemNumber);
						// TODO change to named query:
						// int systemCrawlId =
						// (Integer)problem.getRemark().get("SystemCrawlId");
						TypedQuery<ProblemDescription> querySystemCrawl = em.createNamedQuery(
								"ProblemDescription.findSystemCrawl", ProblemDescription.class);
						try {
							problemDescription = querySystemCrawl.getSingleResult();
						} catch (NoResultException e) {
							// keep it null.
						}
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
					problem.setLocked(locked);
					this.persistProblem();
					log.info("Crawled successfully");
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
			setDisactive();
		}
	}

	/**
	 * Persisting a problem. Need transaction, so write separate.
	 */
	@Transactional
	public void persistProblem() {
		if (problem == null || problemDescription == null)
			return;
		em.persist(problem);
		em.persist(problemDescription);
	}

	public static synchronized int getActiveWorkCount() {
		return activeWorkerCount;
	}

	private static synchronized void setActive() {
		activeWorkerCount++;
	}

	private static synchronized void setDisactive() {
		activeWorkerCount--;
	}
}
