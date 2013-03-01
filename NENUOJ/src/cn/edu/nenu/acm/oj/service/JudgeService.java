package cn.edu.nenu.acm.oj.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;
import java.util.ServiceLoader;
import java.util.concurrent.LinkedBlockingQueue;

import cn.edu.nenu.acm.oj.service.remote.IProblemCrawler;
import cn.edu.nenu.acm.oj.service.remote.IProblemSubmitter;

public class JudgeService extends Thread {

	private Queue judgeQueue;
	private Map<String,LinkedBlockingQueue<String[]>> accounts;
	private Map<String,LinkedBlockingQueue<IProblemCrawler>> crawlers;
	private Map<String,LinkedBlockingQueue<IProblemSubmitter>> submitters;
	
	private JudgeService() {
		judgeQueue = new LinkedBlockingQueue();
		//load account properties
		Scanner accountInfo=new Scanner(JudgeService.class.getResourceAsStream("/accounts.conf"));
		while(accountInfo.hasNext()){
			System.out.println(accountInfo.nextLine());
		}
		accountInfo.close();
		crawlers=new HashMap<String,LinkedBlockingQueue<IProblemCrawler>>();
		
		System.out.println("Judge Service Init...");
		
		ServiceLoader<IProblemCrawler> loader=ServiceLoader.load(IProblemCrawler.class);
		for(IProblemCrawler crawler: loader){
			System.out.println("Find Crawler #"+crawler.getJudgerSource());
		}
		System.out.println("Judge Service Init Finshed...");
		
	}
	
	private static JudgeService judgeService=null;
	public static synchronized JudgeService getInstance(){
		if(judgeService==null||judgeService.getState()==State.TERMINATED)
			judgeService=new JudgeService();
		if(judgeService.getState()==State.NEW)
			judgeService.start();
		return judgeService;
	}

	@Override
	public void run(){
		
	}
	
}
