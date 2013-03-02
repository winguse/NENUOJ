package cn.edu.nenu.acm.oj.service;

import cn.edu.nenu.acm.oj.eto.LoginException;
import cn.edu.nenu.acm.oj.eto.NetworkException;
import cn.edu.nenu.acm.oj.eto.SubmitException;
import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;

public interface IProblemSubmitter extends ISolutionStatusCode {
	
	public String getJudgerSource();
	public void setAccountInformation(String username,String password);
	public void login() throws NetworkException, LoginException;
	public void submit(String problem,String sourceCode,String language) throws SubmitException, NetworkException;
	public boolean getResult() throws NetworkException;
	public String getStatusDescription();
	public int getStatus() throws SubmitException;
	public int getRemoteRunId();
	public int getTime();
	public int getMemory();
	public String getAdditionalInformation() throws NetworkException;
	
}
