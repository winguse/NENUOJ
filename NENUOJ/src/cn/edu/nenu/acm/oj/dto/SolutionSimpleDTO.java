package cn.edu.nenu.acm.oj.dto;

import cn.edu.nenu.acm.oj.statuscode.ISolutionStatusCode;

public class SolutionSimpleDTO implements ISolutionStatusCode {

	private String runId;
	private String username;
	private String problemTitle;
	private String problemId;
	private int statusCode;
	private String statusDescription;
	private int memory;
	private int time;
	private String language;
	private int codeLength;
	private long submitTime;
	private int contestId;

	public SolutionSimpleDTO(String runId, String username, String problemTitle, String problemId, int statusCode,
			String statusDescription, int memory, int time, String language, int codeLength, long submitTime,
			int contestId) {
		super();
		this.runId = runId;
		this.username = username;
		this.problemTitle = problemTitle;
		this.problemId = problemId;
		this.statusCode = statusCode;
		this.statusDescription = statusDescription;
		this.memory = memory;
		this.time = time;
		this.language = language;
		this.codeLength = codeLength;
		this.submitTime = submitTime;
		this.contestId = contestId;
	}

	public String getRunId() {
		return runId;
	}

	public void setRunId(String runId) {
		this.runId = runId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProblemTitle() {
		return problemTitle;
	}

	public void setProblemTitle(String problemTitle) {
		this.problemTitle = problemTitle;
	}

	public String getProblemId() {
		return problemId;
	}

	public void setProblemId(String problemId) {
		this.problemId = problemId;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getCodeLength() {
		return codeLength;
	}

	public void setCodeLength(int codeLength) {
		this.codeLength = codeLength;
	}

	public long getSubmitTime() {
		return submitTime;
	}

	public void setSubmitTime(long submitTime) {
		this.submitTime = submitTime;
	}

	public int getContestId() {
		return contestId;
	}

	public void setContestId(int contestId) {
		this.contestId = contestId;
	}

}
