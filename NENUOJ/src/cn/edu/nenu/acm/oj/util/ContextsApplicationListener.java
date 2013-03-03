package cn.edu.nenu.acm.oj.util;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Scope;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.ContextStoppedEvent;
import org.springframework.stereotype.Service;

import cn.edu.nenu.acm.oj.service.impl.JudgeService;
@Service
@Scope("singleton")
public class ContextsApplicationListener implements ApplicationListener<ApplicationContextEvent> {
	
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		ApplicationContext ctx = event.getApplicationContext();
		if(event instanceof ContextRefreshedEvent){
			System.out.println("ContextRefreshedEvent");
			ctx.getBean(JudgeService.class).start(); // start judge service when spring started.
		}else if(event instanceof ContextStartedEvent){
			System.out.println("ContextStartedEvent");//only awared when context.start(), so useless to me. 
		}else if(event instanceof ContextStoppedEvent){
			System.out.println("ContextStoppedEvent");//same as above
		}else if(event instanceof ContextClosedEvent){
			System.out.println("ContextClosedEvent");
			ctx.getBean(JudgeService.class).setStop();// stop judge service 
		}else{
			System.out.println("WTF?? "+event.getClass().getName());
		}
		
	}

}
