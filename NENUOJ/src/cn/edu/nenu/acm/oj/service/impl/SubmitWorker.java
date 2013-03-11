package cn.edu.nenu.acm.oj.service.impl;

import java.util.Date;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.eto.LoginException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.SubmitException;
import cn.edu.nenu.acm.oj.service.IProblemSubmitter;

@Component
@Scope("prototype")
public class SubmitWorker extends Thread {

	protected static Logger log = LogManager.getLogger("SubmitWorker");
	private static int activeWorkerCount = 0;

	@PersistenceContext
	private EntityManager em;
	private IProblemSubmitter submitter;
	private LinkedBlockingQueue<Solution> queue;
	private LinkedBlockingQueue<String[]> accountQueue;
	private String[] account;
	private Judger judger = null;
	private Solution currentSolution = null;

	/**
	 * Init the submitter worker. Please be aware that:<br>
	 * 1. The account is designed to have been poll from the account. Here the
	 * accountQueue is just use to return the account after the thread is
	 * terminated.<br>
	 * 2. The submitter is not init with the account, and it will be set the
	 * account information here.
	 * 
	 * @param submitter
	 * @param queue
	 * @param account
	 * @param accountQueue
	 */
	public void init(IProblemSubmitter submitter, LinkedBlockingQueue<Solution> queue, String[] account,
			LinkedBlockingQueue<String[]> accountQueue) {
		this.submitter = submitter;
		this.queue = queue;
		this.account = account;
		this.accountQueue = accountQueue;
		// set the account information
		submitter.setAccountInformation(account[0], account[1]);
		// load the jueger
		String judgeSource = submitter.getJudgerSource();
		TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
		judgerQuery.setParameter("source", judgeSource);
		judger = judgerQuery.getSingleResult();
	}

	@Override
	public void run() {
		setActive();
		try {
			log.info("Login #" + account[0]);
			submitter.login();
			while (null != (currentSolution = queue.poll())) {
				int interval = 500, intervalStep = 500, maxTry = 50, tried = 0;
				log.info("Submit #" + currentSolution.getId());
				try {
					submitter.submit(currentSolution.getProblem().getNumber(), currentSolution.getSourceCode(),
							currentSolution.getLanguage());
					Thread.sleep(interval);
					while (submitter.getResult()) {
						tried++;
						if(maxTry>=tried){
							currentSolution.setStatusDescription(submitter.getStatusDescription()+" #try:"+tried+"/"+maxTry);
							currentSolution.setStatus(Solution.STATUS_PROCESSING);
							updateSolution();
						}else{
							break;
						}
						Thread.sleep(interval);
						interval+=intervalStep;
					}
					currentSolution.setJudgeTime(new Date());
					if(tried<=maxTry){
						currentSolution.setStatus(submitter.getStatus());
						currentSolution.setStatusDescription(submitter.getStatusDescription());
						currentSolution.setRunMemory(submitter.getMemory());
						currentSolution.setRunTime(submitter.getTime());
						if(submitter.getStatus()==Solution.STATUS_COMPLIE_ERROR){
							currentSolution.getRemark().set("AdditionalInformation", submitter.getAdditionalInformation());
						}else{
							currentSolution.getRemark().remove("AdditionalInformation");
						}
						currentSolution.getRemark().set("RemoteRunId", submitter.getRemoteRunId());
					}else{
						currentSolution.setStatusDescription(submitter.getStatusDescription()+" #Remote Judge Timeout.");
						currentSolution.setStatus(Solution.STATUS_JUDGE_ERROR);
					}
					updateSolution();
				} catch (SubmitException e) {
					e.printStackTrace();
					log.error("SubmitException: " + e.getMessage());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (NetworkException e) {
			e.printStackTrace();
			log.error("NetworkException:" + e.getMessage());
		} catch (LoginException e) {
			e.printStackTrace();
			log.error("LoginException:" + e.getMessage());
		} finally {
			setDisactive();
			accountQueue.add(account);// remember to return account
		}
	}

	@Transactional
	public void updateSolution() {
		currentSolution = em.merge(currentSolution);
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
