package cn.edu.nenu.acm.oj.statuscode;

import java.util.HashMap;
import java.util.Map;

public interface ISolutionStatusCode {
	/* Solution Status */
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

	public static final Map<Integer, String> statusMapping = new HashMap<Integer, String>() {
		{
			put(STATUS_PEDDING, "Pedding");
			put(STATUS_PROCESSING, "Processing");
			put(STATUS_JUDGE_ERROR, "Judge Error");
			put(STATUS_ACCEPTED, "Accepted");
			put(STATUS_COMPLIE_ERROR, "Compilation Error");
			put(STATUS_MEMORY_LIMITED_EXCEED, "Memory Limit Exceeded");
			put(STATUS_OUTPUT_LIMITED_EXCEED, "Output Limit Exceeded");
			put(STATUS_PRESENTATION_ERROR, "Presentation Error");
			put(STATUS_RUNTIME_ERROR, "Runtime Error");
			put(STATUS_TIME_LIMITED_EXCEED, "Time Limit Exceeded");
			put(STATUS_WRONG_ANSWER, "Wrong Answer");
		}
	};
}
