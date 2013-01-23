package cn.edu.nenu.acm.oj.interceptors;

import java.util.Map;

import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class AdminLoginNeededInterceptor extends AbstractInterceptor {

	private static final long serialVersionUID = 6742128071361984185L;

	@Override
	public String intercept(ActionInvocation ict) throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		if (session.get("user") == null || !(session.get("user") instanceof UserSimpleDTO)) {
			return "reject";
		} else {
			String result = ict.invoke();
			return result;
		}
	}

}
