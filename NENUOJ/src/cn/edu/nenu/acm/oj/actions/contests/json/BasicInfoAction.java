package cn.edu.nenu.acm.oj.actions.contests.json;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.ContestDAO;
import cn.edu.nenu.acm.oj.dto.ContestSimpleDTO;
import cn.edu.nenu.acm.oj.dto.UserSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.statuscode.IContestType;
import cn.edu.nenu.acm.oj.statuscode.ISessionField;
import cn.edu.nenu.acm.oj.util.Pair;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n") })
@Results({ @Result(name = "success", type = "json") })
public class BasicInfoAction extends AbstractJsonAction implements
		SessionAware, IContestType {

	private static final long serialVersionUID = 1727087661137013184L;

	@Autowired
	private ContestDAO dao;

	private int contestId;
	private Map<String, Object> session;

	@Override
	public String execute() throws Exception {
		UserSimpleDTO user = getUser(session);
		Object tmp = session.get(AUTH_CONTEST_ID);
		ContestSimpleDTO contest = dao.getContestBasicInfo(contestId);
		int contestType = contest.getContestType();
		if(contestType == CONTEST_TYPE_PRIVATE){
			HashSet<Integer> authContestIds;
			if (tmp != null && tmp instanceof HashSet<?>)
				authContestIds = (HashSet<Integer>) tmp;
			else
				authContestIds = new HashSet<Integer>();
			if(!authContestIds.contains(contestId)&&!contest.getHostUsername().equals(user.getUsername())){
				// need password
			}
		}
		return SUCCESS;
	}

	@Override
	public String getMessage() {
		return message;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	public void setContestId(int contestId) {
		this.contestId = contestId;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

}
