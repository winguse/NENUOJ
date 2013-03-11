package cn.edu.nenu.acm.oj.statuscode;

public interface ISolutionStatusCode {
	/*Solution Status*/
	public final static int STATUS_PEDDING = 0;
	public final static int STATUS_PROCESSING = 1;
	public final static int STATUS_JUDGE_ERROR = 2;
	public final static int STATUS_ACCEPTED = 3;
	public final static int STATUS_PRESENTATION_ERROR = 4;
	public final static int STATUS_WRONG_ANSWER = 5;
	public final static int STATUS_TIME_LIMITED_EXCEED = 6;
	public final static int STATUS_MEMORY_LIMITED_EXCEED = 7;
	public final static int STATUS_OUTPUT_LIMITED_EXCEED = 8;
	public final static int STATUS_RUNTIME_ERROR = 9;
	public final static int STATUS_COMPLIE_ERROR = 10;
}
