package cn.edu.nenu.acm.oj.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.service.remote.IProblemCrawler;
import cn.edu.nenu.acm.oj.service.remote.IProblemSubmitter;

public class JudgeService extends Thread {

	private Map<String, LinkedBlockingQueue<IProblemCrawler>> crawlers;
	private Map<String, LinkedBlockingQueue<IProblemSubmitter>> submitters;
	protected static Logger log = LogManager.getLogger("JudgeService");
	private Queue<Solution> judgeQueue;

	private JudgeService() {
		judgeQueue = new LinkedBlockingQueue<Solution>();
		
		log.info("Loading account information..");
		Map<String, LinkedBlockingQueue<String[]>> accounts = new HashMap<String, LinkedBlockingQueue<String[]>>();
		Scanner accountInfo = new Scanner(JudgeService.class.getResourceAsStream("/accounts.conf"));
		while (accountInfo.hasNext()) {
			String line = accountInfo.nextLine();
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
			} else {
				log.error("Account file error: [" + line + "].");
			}
		}
		accountInfo.close();

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

	private static JudgeService judgeService = null;

	public static synchronized JudgeService getInstance() {
		if (judgeService == null || judgeService.getState() == State.TERMINATED)
			judgeService = new JudgeService();
		if (judgeService.getState() == State.NEW){
			judgeService.start();
			log.info("Judge Service Running #"+judgeService.getId());
		}
		return judgeService;
	}

	@Override
	public void run() {

	}

}
