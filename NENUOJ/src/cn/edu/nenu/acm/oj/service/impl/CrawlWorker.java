package cn.edu.nenu.acm.oj.service.impl;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.eto.CrawlingException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.RemoteProblemNotFoundException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;
import cn.edu.nenu.acm.oj.util.Remark;

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
				em.getTransaction().begin();
				log += problemNumber + ": ";
				crawler.crawl(problemNumber);
				// problem number contains # marked as locked problem
				boolean locked = problemNumber.contains("#");
				problemNumber = problemNumber.replace("#", "");
				Problem problem = null;
				TypedQuery<Problem> query = em.createNamedQuery("Problem.findByJudgerAndNumber", Problem.class);
				query.setParameter("judger", judger);
				query.setParameter("number", problemNumber);
				List<Problem> lstProblem = query.getResultList();
				ProblemDescription problemDescription = null;
				if (lstProblem.size() > 0) {
					problem = lstProblem.get(0);
					log += "Re-crawl " + problemNumber;
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
					log += "Newly crawl " + problemNumber;
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
				em.persist(problem);
				em.persist(problemDescription);
				em.getTransaction().commit();
				log += ", crawled successfully";
			} catch (NetworkException e) {
				e.printStackTrace();
				em.getTransaction().rollback();
				log += ", NetworkException:" + e.getMessage();
			} catch (CrawlingException e) {
				e.printStackTrace();
				em.getTransaction().rollback();
				log += ", CrawlingException:" + e.getMessage();
			} catch (RemoteProblemNotFoundException e) {
				e.printStackTrace();
				em.getTransaction().rollback();
				log += ", RemoteProblemNotFoundException:" + e.getMessage();
			} finally {
				log += "\n";
			}
		}
	}

}
