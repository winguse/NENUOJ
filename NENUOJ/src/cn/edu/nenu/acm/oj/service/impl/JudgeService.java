package cn.edu.nenu.acm.oj.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import cn.edu.nenu.acm.oj.dao.JudgerDAO;
import cn.edu.nenu.acm.oj.dao.SolutionDAO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.eto.CrawlerNotExistException;
import cn.edu.nenu.acm.oj.eto.NotSupportJudgeSourceException;
import cn.edu.nenu.acm.oj.eto.SubmitterNotExistException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;
import cn.edu.nenu.acm.oj.service.IProblemSubmitter;

@Service
@Scope("singleton")
@Qualifier("judgeService")
public class JudgeService extends Thread{

	protected static Logger log = LogManager.getLogger("JudgeService");

	@Autowired
	private SolutionDAO solutionDAO;
	@Autowired
	private JudgerDAO judgerDAO;
	
	private ApplicationContext applicationContext;
	private Map<String, LinkedBlockingQueue<Solution>> judgeQueue;
	private Map<String, LinkedBlockingQueue<String>> crawlQueue;
	private Map<String, LinkedBlockingQueue<String[]>> accounts;

	private int maxActiveCrawler = 5;
	private int maxActiveSubmitter = 5;

	private boolean needRunning = true;

	public JudgeService() {
		System.out.println("Judge Service init");
		judgeQueue = new HashMap<String, LinkedBlockingQueue<Solution>>();
		crawlQueue = new HashMap<String, LinkedBlockingQueue<String>>();
	}

	@Override
	public void run() {
		log.info("Judge Service started.");
		this.loadAccountInformation();
		solutionDAO.markAllProcessingSolutionJudgeError();
		while (needRunning) {
			for (Map.Entry<String, LinkedBlockingQueue<String>> e : crawlQueue.entrySet()) {
				if (CrawlWorker.getActiveWorkerCount() >= maxActiveCrawler)
					continue;
				if (e.getValue().isEmpty())
					continue;
				try {
					IProblemCrawler crawler = getCrawler(e.getKey());
					CrawlWorker crawlerWorker = applicationContext.getBean(CrawlWorker.class);
					crawlerWorker.init(crawler, e.getValue());
					crawlerWorker.start();
				} catch (CrawlerNotExistException e1) {
					e1.printStackTrace();
					log.error("CrawlerNotExistException:" + e1.getMessage());
				}
			}
			for (Map.Entry<String, LinkedBlockingQueue<Solution>> e : judgeQueue.entrySet()) {
				if (SubmitWorker.getActiveWorkerCount() >= maxActiveSubmitter)
					continue;
				if (e.getValue().isEmpty())
					continue;
				if (!accounts.containsKey(e.getKey())) {
					log.error("No account was found for Submitter #" + e.getKey());
					continue;
				}
				String[] account = null;
				if (null == (account = accounts.get(e.getKey()).poll())) {
					log.info("Submitter #" + e.getKey() + " is not enough, if judging too slow, add more accounts.");
					continue;
				}
				try {
					IProblemSubmitter submitter = getSubmitter(e.getKey());
					SubmitWorker submitWorker = applicationContext.getBean(SubmitWorker.class);
					// I make this a litter complex, just to make sure only all
					// resource (especially account)
					// are available.
					submitWorker.init(submitter, e.getValue(), account, accounts.get(e.getKey()));
				} catch (SubmitterNotExistException e1) {
					e1.printStackTrace();
					log.error("SubmitterNotExistException:" + e1.getMessage());
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("Judge Service stopping...");
		// clean up
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

	/**
	 * Get a new crawler of specify Judge Source
	 * 
	 * @param judgeSource
	 * @return the Crawler you have require
	 * @throws CrawlerNotExistException
	 */
	public IProblemCrawler getCrawler(String judgeSource) throws CrawlerNotExistException {
		try {
			return applicationContext.getBean(judgeSource + "_Crawler", IProblemCrawler.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Fail to load crawler Judge Source #" + judgeSource);
			throw new CrawlerNotExistException("#" + judgeSource);
		}
	}

	/**
	 * Get a new submitter of specify Judge Source, please note that, the
	 * account information was not set.
	 * 
	 * @param judgeSource
	 * @return the Submitter you have require
	 * @throws SubmitterNotExistException
	 */
	public IProblemSubmitter getSubmitter(String judgeSource) throws SubmitterNotExistException {
		try {
			return applicationContext.getBean(judgeSource + "_Submitter", IProblemSubmitter.class);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Fail to load crawler Judge Source #" + judgeSource);
			throw new SubmitterNotExistException("#" + judgeSource);
		}
	}

	/**
	 * Load account information from WEB-INF/accounts.conf<br>
	 * Format:<br>
	 * #COMMENT<br>
	 * JUDGE_SOURCE USERNAME PASSWORD #COMMENT
	 */
	public void loadAccountInformation() {
		log.info("Loading account information..");
		try {
			accounts = new HashMap<String, LinkedBlockingQueue<String[]>>();
			Resource accountResource = applicationContext.getResource("WEB-INF/accounts.conf");
			Scanner accountInfo = new Scanner(accountResource.getInputStream());
			while (accountInfo.hasNext()) {
				String line = accountInfo.nextLine();
				if (line.startsWith("#"))
					continue;
				String[] segment = line.split("#")[0].split("\\s+");
				if (segment.length == 3) {
					String account[] = { segment[1], segment[2] };
					if (accounts.containsKey(segment[0])) {
						accounts.get(segment[0]).add(account);
					} else {
						if (judgerDAO.findByColumn("source", segment[0]).size() == 0) {
							// add judger information into the database
							Judger judger = new Judger();
							judger.setSource(segment[0]);
							judgerDAO.persist(judger);
							log.info("Persist new judger: #"+segment[0]);
						}
						LinkedBlockingQueue<String[]> accountQueue = new LinkedBlockingQueue<String[]>();
						accountQueue.add(account);
						accounts.put(segment[0], accountQueue);
					}
					log.info("Added " + segment[1] + " of " + segment[0] + " .");
				} else {
					log.error("Account file error: [" + line + "].");
				}
			}
			accountInfo.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * ask judge service to stop
	 */
	public void setStop() {
		needRunning = false;
		this.interrupt();
		SubmitWorker.setAllStop();
		CrawlWorker.setAllStop();
		log.info("wait 5s for some threads to stop...");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		log.info("wait 5s passed...");
	}

	/**
	 * TODO this method may lead the problem of database transaction, how about
	 * passing the solution id as the parameter? set up a judge job for service,
	 * please note that solution must be persisted.
	 * 
	 * @param solution
	 * @throws NotSupportJudgeSourceException
	 */
	public synchronized void putJudgeJob(Solution solution) throws NotSupportJudgeSourceException {
		String judgeSource = solution.getProblem().getJudger().getSource();
		if (accounts.get(judgeSource)==null)
			throw new NotSupportJudgeSourceException(judgeSource);
		if (judgeQueue.get(judgeSource)==null) {
			LinkedBlockingQueue<Solution> solutionQueue = new LinkedBlockingQueue<Solution>();
			solutionQueue.add(solution);
			judgeQueue.put(judgeSource, solutionQueue);
		} else {
			judgeQueue.get(judgeSource).add(solution);
		}
	}

	/**
	 * set up a crawl job for the service
	 * 
	 * @param judgeSource
	 * @param problem
	 * @throws NotSupportJudgeSourceException
	 */
	public synchronized void putCrawlJob(String judgeSource, String problem) throws NotSupportJudgeSourceException {
		if (accounts.get(judgeSource)==null)
			throw new NotSupportJudgeSourceException(judgeSource);
		if (crawlQueue.get(judgeSource)==null) {
			LinkedBlockingQueue<String> que = new LinkedBlockingQueue<String>();
			que.add(problem);
			crawlQueue.put(judgeSource, que);
		} else {
			crawlQueue.get(judgeSource).add(problem);
		}
	}

}
