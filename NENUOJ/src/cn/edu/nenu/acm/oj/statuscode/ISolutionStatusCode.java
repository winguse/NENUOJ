package cn.edu.nenu.acm.oj.statuscode;

public interface ISolutionStatusCode {
	/*Solution Status*/
	public final static int STATUS_PROCESSING = 0;
	public final static int STATUS_JUDGE_ERROR = 1;
	public final static int STATUS_ACCEPTED = 2;
	public final static int STATUS_PRESENTATION_ERROR = 3;
	public final static int STATUS_WRONG_ANSWER = 4;
	public final static int STATUS_TIME_LIMITED_EXCEED = 5;
	public final static int STATUS_MEMORY_LIMITED_EXCEED = 6;
	public final static int STATUS_OUTPUT_LIMITED_EXCEED = 7;
	public final static int STATUS_RUNTIME_ERROR = 8;
	public final static int STATUS_COMPLIE_ERROR = 9;
}
