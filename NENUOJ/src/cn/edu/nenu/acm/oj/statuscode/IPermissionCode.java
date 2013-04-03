package cn.edu.nenu.acm.oj.statuscode;

public interface IPermissionCode {
	public static final long PERMISSION_LOGIN = 1L;
	public static final long PERMISSION_VIEW_ALL_SOURCE_CODE = 2L;
	public static final long PERMISSION_ADMIN_PRIVILEGE = 4L;
	public static final long PERMISSION_SEE_LOCKED_DESCRIPTION = 8L; 
	public static final long PERMISSION_SEE_LOCKED_PROBLEM = 16L; 
}
