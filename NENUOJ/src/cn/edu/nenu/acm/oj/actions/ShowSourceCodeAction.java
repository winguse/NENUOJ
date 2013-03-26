package cn.edu.nenu.acm.oj.actions;

import java.util.Date;
import java.util.Map;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.ActionContext;

import cn.edu.nenu.acm.oj.dao.SolutionDAO;
import cn.edu.nenu.acm.oj.dto.SolutionDTO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;

@ParentPackage("default")
@Result(name = "success", location = "show-source-code-success.jsp")
@Namespace("/")
@InterceptorRefs({
	@InterceptorRef("i18n"),
	@InterceptorRef("params"),
		@InterceptorRef("permissionInterceptor") })
public class ShowSourceCodeAction extends AbstractAction implements
		IPermissionCode {

	private static final long serialVersionUID = 1L;

	@Autowired
	private SolutionDAO dao;
	private int runId = 0;
	private SolutionDTO solution;
	private boolean adminPrivilege = false;
	
	@Override
	public String execute() throws Exception {
		Map<String, Object> session = ActionContext.getContext().getSession();
		UserSimpleDTO user = (UserSimpleDTO) session.get("user");
		solution = dao.findSolution(runId);
		adminPrivilege = (user.getPermission()&PERMISSION_ADMIN_PRIVILEGE) == PERMISSION_ADMIN_PRIVILEGE;
		if (solution == null)
			return "404";
		if ((user.getPermission() & PERMISSION_VIEW_ALL_SOURCE_CODE) != PERMISSION_VIEW_ALL_SOURCE_CODE)
			return SUCCESS;
		if (!solution.isShared()
				&& !user.getUsername().endsWith(solution.getUsername()))
			return "reject-permission-denied";
		if (solution.getContest() != null
				&& solution.getContest().getEndTime() > new Date().getTime())
			return "reject-view-source-code-after-contest";
		return SUCCESS;
	}

	public void setRunId(int runId) {
		this.runId = runId;
	}

	public SolutionDTO getSolution() {
		return solution;
	}

	public boolean isAdminPrivilege() {
		return adminPrivilege;
	}

}
