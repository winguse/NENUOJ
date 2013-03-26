package cn.edu.nenu.acm.oj.dto;

public class SolutionDTO extends SolutionSimpleDTO {
	private int remoteRunId;
	private String sourceCode;
	private long judgeTime;
	private double passrate;
	private String additionalInformation;
	private String ipAddress;
	private MessageDTO message;
	private ContestSimpleDTO contest;

	public int getRemoteRunId() {
		return remoteRunId;
	}

	public void setRemoteRunId(int remoteRunId) {
		this.remoteRunId = remoteRunId;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public long getJudgeTime() {
		return judgeTime;
	}

	public void setJudgeTime(long judgeTime) {
		this.judgeTime = judgeTime;
	}

	public double getPassrate() {
		return passrate;
	}

	public void setPassrate(double passrate) {
		this.passrate = passrate;
	}

	public String getAdditionalInformation() {
		return additionalInformation;
	}

	public void setAdditionalInformation(String additionalInformation) {
		this.additionalInformation = additionalInformation;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public MessageDTO getMessage() {
		return message;
	}

	public void setMessage(MessageDTO message) {
		this.message = message;
	}

	public ContestSimpleDTO getContest() {
		return contest;
	}

	public void setContest(ContestSimpleDTO contest) {
		this.contest = contest;
	}

	public SolutionDTO(int runId, String username, String judgerSource,
			String prublemNumber, String problemTitle, int problemId,
			int statusCode, String statusDescription, int memory, int time,
			String language, int codeLength, long submitTime, int contestId,
			boolean shared, int remoteRunId, String sourceCode, long judgeTime,
			double passrate, String additionalInformation, String ipAddress,
			MessageDTO message, ContestSimpleDTO contest) {
		super(runId, username, judgerSource, prublemNumber, problemTitle,
				problemId, statusCode, statusDescription, memory, time,
				language, codeLength, submitTime, contestId, shared);
		this.remoteRunId = remoteRunId;
		this.sourceCode = sourceCode;
		this.judgeTime = judgeTime;
		this.passrate = passrate;
		this.additionalInformation = additionalInformation;
		this.ipAddress = ipAddress;
		this.message = message;
		this.contest = contest;
	}

}
