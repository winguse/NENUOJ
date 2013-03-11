package cn.edu.nenu.acm.oj.service;

import cn.edu.nenu.acm.oj.eto.LoginException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.SubmitException;
import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;

/**
 * Problem Submitter <br>
 * Submitter is reusable for many times <br>
 * Usage:<br>
 * 1. setAccountInformation<br>
 * 2. login<br>
 * 3. submit<br>
 * 4. getResult, loop this until true (judged)<br>
 * 5. get** you want<br>
 * 6. go back to 3 if needed<br>
 * 
 * @author Winguse
 * 
 */
public interface IProblemSubmitter extends ISolutionStatusCode {

	/**
	 * The judger source name, like HDU,POJ,etc.
	 * 
	 * @return source name
	 */
	public String getJudgerSource();

	/**
	 * init the account
	 * 
	 * @param username
	 * @param password
	 */
	public void setAccountInformation(String username, String password);

	/**
	 * Account information must be set.
	 * 
	 * @throws NetworkException
	 * @throws LoginException
	 */
	public void login() throws NetworkException, LoginException;

	/**
	 * must login first.
	 * 
	 * @param problem
	 * @param sourceCode
	 * @param language
	 * @throws SubmitException
	 * @throws NetworkException
	 */
	public void submit(String problem, String sourceCode, String language) throws SubmitException, NetworkException;

	/**
	 * it's designed to be used in while loop until solution is judge, and basic
	 * information like status description should be set here. don't forget to
	 * sleep.
	 * 
	 * @return true if the result is judged
	 * @throws NetworkException
	 * @throws SubmitException
	 */
	public boolean getResult() throws NetworkException, SubmitException;

	/**
	 * Status Description such as Accepted, Wrong Answer, Pedding all depends on
	 * remote. it's designed to let user know more information about the judging
	 * procedure.
	 * 
	 * @throws SubmitException
	 * @return Status Description
	 */
	public String getStatusDescription() throws SubmitException;

	/**
	 * @return preased status, base on ISolutionStatusCode
	 * @throws SubmitException
	 */
	public int getStatus() throws SubmitException;

	/**
	 * @return Remote Run Id
	 * @throws SubmitException
	 */
	public int getRemoteRunId() throws SubmitException;

	/**
	 * @return run time in ms
	 * @throws SubmitException
	 */
	public int getTime() throws SubmitException;

	/**
	 * @return run memory in kb
	 * @throws SubmitException
	 */
	public int getMemory() throws SubmitException;

	/**
	 * @return Additional Information about the judge, such detail Complication
	 *         Error
	 * @throws NetworkException
	 */
	public String getAdditionalInformation() throws NetworkException;

}
