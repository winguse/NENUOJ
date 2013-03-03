package cn.edu.nenu.acm.oj.eto;

public class SubmitterNotExistException extends Exception {

	private static final long serialVersionUID = -1594556574711802650L;

	public SubmitterNotExistException() {
	}

	public SubmitterNotExistException(String arg0) {
		super(arg0);
	}

	public SubmitterNotExistException(Throwable arg0) {
		super(arg0);
	}

	public SubmitterNotExistException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public SubmitterNotExistException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

}
