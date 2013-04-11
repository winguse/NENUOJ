package cn.edu.nenu.acm.oj.eto;

public class ReplayDataInvalidException extends Exception {

	private static final long serialVersionUID = 1L;

	public ReplayDataInvalidException() {
		super();
	}

	public ReplayDataInvalidException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public ReplayDataInvalidException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public ReplayDataInvalidException(String arg0) {
		super(arg0);
	}

	public ReplayDataInvalidException(Throwable arg0) {
		super(arg0);
	}

}
