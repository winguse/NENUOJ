package cn.edu.nenu.acm.oj.eto;

public class SubmitException extends Exception {

	private static final long serialVersionUID = -431959470772941518L;

	public SubmitException() {
	}

	public SubmitException(String message) {
		super(message);
	}

	public SubmitException(Throwable cause) {
		super(cause);
	}

	public SubmitException(String message, Throwable cause) {
		super(message, cause);
	}

	public SubmitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
