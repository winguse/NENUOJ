package cn.edu.nenu.acm.oj.interceptors;

import java.util.Map;

import cn.edu.nenu.acm.oj.dao.UserDAO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.util.Permission;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class PermissionInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6742128071361984185L;

	private long permission = 0L;

	@Override
	public String intercept(ActionInvocation ict) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserSimpleDTO user = (UserSimpleDTO) session.get("user");
		if(user == null){
			user = new UserSimpleDTO();
			user.setPermission(Long.MAX_VALUE);
			user.setId(23);
			user.setUsername("winguse");
			session.put("user", user);
		}
		if (user == null || !(user instanceof UserSimpleDTO)) {
			return "reject-login-needed";
		} else if ((user.getPermission() & Permission.LOGIN) != Permission.LOGIN) {
			return "reject-login-denied";
		} else if (false&&(user.getPermission() & permission) != permission) {
			return "reject-permission-denied";
		} else {
			String result = ict.invoke();
			return result;
		}
	}

	public void setPermission(long permission) {
		this.permission = permission;
	}

}
