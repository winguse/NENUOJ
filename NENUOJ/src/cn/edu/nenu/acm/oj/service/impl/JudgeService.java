package cn.edu.nenu.acm.oj.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.eto.CrawlerNotExistException;
import cn.edu.nenu.acm.oj.eto.NotSupportJudgeSourceException;
import cn.edu.nenu.acm.oj.eto.SubmitterNotExistException;
import cn.edu.nenu.acm.oj.service.IProblemCrawler;
import cn.edu.nenu.acm.oj.service.IProblemSubmitter;

@Service
@Scope("singleton")
public class JudgeService extends Thread implements ApplicationContextAware {

	protected static Logger log = LogManager.getLogger("JudgeService");

	private ApplicationContext applicationContext;
	private Map<String, LinkedBlockingQueue<Solution>> judgeQueue;
	private Map<String, LinkedBlockingQueue<String>> crawlQueue;
	private Map<String, LinkedBlockingQueue<String[]>> accounts;

	private int maxActiveCrawler = 5;
	private int maxActiveSubmitter = 5;

	private int activeCrawler = 0;
	private int activeSubmitter = 0;
	
	@Deprecated
	private Map<String, LinkedBlockingQueue<IProblemCrawler>> crawlers;
	@Deprecated
	private Map<String, LinkedBlockingQueue<IProblemSubmitter>> submitters;

	private boolean needRunning = true;

	@Deprecated
	public void __JudgeService() {
		judgeQueue = new HashMap<String, LinkedBlockingQueue<Solution>>();

		log.info("Initializing crawler...");
		crawlers = new HashMap<String, LinkedBlockingQueue<IProblemCrawler>>();
		ServiceLoader<IProblemCrawler> crawlerLoader = ServiceLoader.load(IProblemCrawler.class);
		for (IProblemCrawler crawler : crawlerLoader) {
			if (accounts.containsKey(crawler.getJudgerSource())) {
				LinkedBlockingQueue<IProblemCrawler> cralerQueue = new LinkedBlockingQueue<IProblemCrawler>();
				cralerQueue.add(crawler);// If one crawler is not enough, add
											// more in the queue.
				crawlers.put(crawler.getJudgerSource(), cralerQueue);
			} else {
				log.warn("No account information of #" + crawler.getJudgerSource() + ", the crawler was not in used.");
			}
		}

		log.info("Initializing submitter...");
		submitters = new HashMap<String, LinkedBlockingQueue<IProblemSubmitter>>();
		ServiceLoader<IProblemSubmitter> submitterLoader = ServiceLoader.load(IProblemSubmitter.class);
		for (IProblemSubmitter submitter : submitterLoader) {
			if (accounts.containsKey(submitter.getJudgerSource())) {
				LinkedBlockingQueue<IProblemSubmitter> submitterQueue = new LinkedBlockingQueue<IProblemSubmitter>();
				LinkedBlockingQueue<String[]> accountQueue = accounts.get(submitter.getJudgerSource());
				while (!accountQueue.isEmpty()) {
					String account[] = accountQueue.poll();
					try {
						IProblemSubmitter newSubmitter = submitter.getClass().newInstance();
						newSubmitter.setAccountInformation(account[0], account[1]);
						log.info("Added submitter #" + newSubmitter.getJudgerSource() + ", [" + account[0] + ","
								+ account[1] + "]");
						submitterQueue.add(newSubmitter);
					} catch (InstantiationException | IllegalAccessException e) {
						e.printStackTrace();
						log.error("Serious Error while initialze Submitter: #" + submitter.getJudgerSource() + ","
								+ e.getMessage());
					}
				}
				submitters.put(submitter.getJudgerSource(), submitterQueue);
			} else {
				log.warn("No account information of #" + submitter.getJudgerSource()
						+ ", the submitter was not in used.");
			}
		}
		log.info("Judge Service Init Finshed.");

	}

	public JudgeService() {
		judgeQueue = new HashMap<String, LinkedBlockingQueue<Solution>>();
		crawlQueue = new HashMap<String, LinkedBlockingQueue<String>>();
	}

	@Override
	public void run() {
		log.info("Judge Service started.");
		while (needRunning) {
			// TODO work
			
			for (Map.Entry<String, LinkedBlockingQueue<String>> e : crawlQueue.entrySet()) {
				if(activeCrawler >= maxActiveCrawler)continue;
				if(e.getValue().isEmpty())continue;
					//getCrawler(e.getKey())
			}
			for (Map.Entry<String, LinkedBlockingQueue<Solution>> e : judgeQueue.entrySet()) {
				if(activeSubmitter >= maxActiveSubmitter)continue;
				if(e.getValue().isEmpty())continue;
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		log.info("Judge Service stopping...");
		// TODO clean up
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
		this.loadAccountInformation();
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
		if (!accounts.containsKey(judgeSource))
			throw new NotSupportJudgeSourceException(judgeSource);
		if (judgeQueue.containsKey(judgeSource)) {
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
		if (!accounts.containsKey(judgeSource))
			throw new NotSupportJudgeSourceException(judgeSource);
		if (crawlQueue.containsKey(judgeSource)) {
			LinkedBlockingQueue<String> que = new LinkedBlockingQueue<String>();
			que.add(problem);
			crawlQueue.put(judgeSource, que);
		} else {
			crawlQueue.get(judgeSource).add(problem);
		}
	}
}
