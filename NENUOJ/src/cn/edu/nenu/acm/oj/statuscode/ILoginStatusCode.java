package cn.edu.nenu.acm.oj.statuscode;

public interface ILoginStatusCode {
	static final int CODE_SUCCESS=0;
	static final int USERNAME_NOT_EXIST=1;
	static final int PASSWORD_NOT_MATCH=2;
	static final int VIRIFY_CODE_ERROR=3;
}
