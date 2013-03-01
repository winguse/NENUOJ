package cn.edu.nenu.acm.oj.dto;

import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;

public class SolutionSimpleDTO implements ISolutionStatusCode {
	
	private String runId;
	private String username;
	private int statusCode;
	private String statusDescription;
	private int memory;
	private int time;
	private String language;
	private int codeLength;
	private long submitTime;

}
