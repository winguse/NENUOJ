package cn.edu.nenu.acm.oj.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.dao.GenericDAO;
import cn.edu.nenu.acm.oj.dao.SolutionDAO;
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
	private static HashMap<Long, SubmitWorker> us = new HashMap<Long, SubmitWorker>();

	@Autowired
	private SolutionDAO dao;
	
	private IProblemSubmitter submitter;
	private LinkedBlockingQueue<Integer> queue;
	private LinkedBlockingQueue<String[]> accountQueue;
	private String[] account;
	private boolean canRunning = true;

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
	public void init(IProblemSubmitter submitter, LinkedBlockingQueue<Integer> queue, String[] account,
			LinkedBlockingQueue<String[]> accountQueue) {
		this.submitter = submitter;
		this.queue = queue;
		this.account = account;
		this.accountQueue = accountQueue;
		// set the account information
		submitter.setAccountInformation(account[0], account[1]);
	}

	@Override
	public void run() {
		setActive(this);
		Integer solutionId = null;
		Solution solution = null;
		try {
			if (!canRunning)
				return;
			log.info("Login #" + account[0]);
			submitter.login();
			while (null != (solutionId = queue.poll())) {
				if (!canRunning)
					return;
				int interval = 500, intervalStep = 500, maxTry = 50, tried = 0;
				String problemNumber = dao.getProblemNumber(solutionId);
				log.info("Submit #" + solutionId);
				try {
					solution = dao.findById(solutionId);
					solution.setStatusDescription("Submitting");
					solution.setStatus(Solution.STATUS_PROCESSING);
					dao.merge(solution);
					if (!canRunning)
						return;
					submitter.submit(problemNumber, solution.getSourceCode(),
							solution.getLanguage());
					Thread.sleep(interval);
					while (submitter.getResult()) {
						tried++;
						if (maxTry >= tried) {
							solution = dao.findById(solutionId);
							solution.setStatusDescription(submitter.getStatusDescription() + " #try:" + tried
									+ "/" + maxTry);
							solution.setStatus(Solution.STATUS_PROCESSING);
							dao.merge(solution);
						} else {
							break;
						}
						Thread.sleep(interval);
						if (!canRunning)
							return;
						interval += intervalStep;
					}
					solution = dao.findById(solutionId);
					solution.setJudgeTime(new Date());
					if (tried <= maxTry) {
						solution.setStatus(submitter.getStatus());
						solution.setStatusDescription(submitter.getStatusDescription());
						solution.setRunMemory(submitter.getMemory());
						solution.setRunTime(submitter.getTime());
						if (submitter.getStatus() == Solution.STATUS_COMPLIE_ERROR) {
							solution.getRemark().set("AdditionalInformation",
									submitter.getAdditionalInformation());
						} else {
							solution.getRemark().remove("AdditionalInformation");
						}
						solution.getRemark().set("RemoteRunId", submitter.getRemoteRunId());
					} else {
						solution.setStatusDescription(submitter.getStatusDescription()
								+ " #Remote Judge Timeout.");
						solution.setStatus(Solution.STATUS_JUDGE_ERROR);
					}
					dao.merge(solution);
				} catch (SubmitException e) {
					e.printStackTrace();
					log.error("SubmitException: " + e.getMessage());
					solution = dao.findById(solutionId);
					solution.setStatusDescription("Judge Error: #"+ e.getMessage().substring(0,20));
					solution.setStatus(Solution.STATUS_JUDGE_ERROR);
					dao.merge(solution);
				} catch (InterruptedException e) {
					e.printStackTrace();
					solution = dao.findById(solutionId);
					solution.setStatusDescription("Judge Interrupted: #"+ e.getMessage().substring(0,20));
					solution.setStatus(Solution.STATUS_JUDGE_ERROR);
					dao.merge(solution);
				}catch (NetworkException e) {
					e.printStackTrace();
					solution = dao.findById(solutionId);
					solution.setStatusDescription("NetworkException: #"+ e.getMessage().substring(0,20));
					solution.setStatus(Solution.STATUS_JUDGE_ERROR);
					dao.merge(solution);
				}catch (Exception e){
					e.printStackTrace();
					solution = dao.findById(solutionId);
					solution.setStatusDescription("UnknowException: #"+ e.getMessage().substring(0,20));
					solution.setStatus(Solution.STATUS_JUDGE_ERROR);
					dao.merge(solution);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errorDescription = e.getClass().getSimpleName() +":"+ e.getMessage();
			log.error(errorDescription);
			while (null != (solutionId = queue.poll())) {
				solution = dao.findById(solutionId);
				if(solution==null)continue;
				solution.setStatusDescription(errorDescription.substring(0, 40));
				solution.setStatus(Solution.STATUS_JUDGE_ERROR);
				dao.merge(solution);
			}
		} finally {
			setDisactive(this);
			accountQueue.add(account);// remember to return account
		}
	}

	public static synchronized int getActiveWorkerCount() {
		return activeWorkerCount;
	}

	private static synchronized void setActive(SubmitWorker worker) {
		activeWorkerCount++;
		us.put(worker.getId(), worker);
	}

	private static synchronized void setDisactive(SubmitWorker worker) {
		activeWorkerCount--;
		us.remove(worker.getId());
	}

	public static synchronized void setAllStop() {
		for (Map.Entry<Long, SubmitWorker> e : us.entrySet()) {
			e.getValue().setStop();
		}
	}

	public void setStop() {
		canRunning = false;
		this.interrupt();
	}
}
