package cn.edu.nenu.acm.oj.eto;

public class LoginException extends Exception {

	private static final long serialVersionUID = -3302460436864417711L;

	public LoginException() {
	}

	public LoginException(String arg0) {
		super(arg0);
	}

	public LoginException(Throwable arg0) {
		super(arg0);
	}

	public LoginException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public LoginException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
