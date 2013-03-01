package cn.edu.nenu.acm.oj.test;

import java.util.ServiceLoader;

import cn.edu.nenu.acm.oj.service.remote.IProblemCrawler;

public class Normal {

	public static void main(String[] args) {
		ServiceLoader<IProblemCrawler> loader=ServiceLoader.load(IProblemCrawler.class);
		System.out.println("Run...");
		for(IProblemCrawler crawler: loader){
			//crawler.getClass()
			System.out.println("# "+crawler.getJudgerSource());
		}
		System.out.println("Terminated...");
	}

}
