package cn.edu.nenu.acm.oj.actions.contests.json;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.interceptor.SessionAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractJsonAction;
import cn.edu.nenu.acm.oj.dao.ContestDAO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.util.RankListCellExpression;
import cn.edu.nenu.acm.oj.util.RankListCellParser;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n") })
@Results({ @Result(name = "success", type = "json") })
public class ChooseReplayPatternAction extends AbstractJsonAction implements SessionAware {

	@Autowired
	private ContestDAO dao;

	private static final long serialVersionUID = -8077897542384482842L;
	private Map<String, Object> session;

	private List<String> regex;
	private List<Integer> idx;

	@Override
	public String execute() throws Exception {
		Map<Integer, RankListCellExpression> indexedExpression = (Map<Integer, RankListCellExpression>) session
				.get("indexedExpression");
		String cells[][] = (String[][]) session.get("rankListCells");
		int addedContestId = (int) session.get("addedContestId");
		Contest contest = dao.findById(addedContestId);
		JSONObject json = new JSONObject();
		boolean error = false;
		long contestLength = 0L;
		Map<String, RankListCellExpression> dueExpression = new HashMap<String, RankListCellExpression>();
		for (int i = 0; i < regex.size(); i++) {
			if (i >= idx.size()) {
				error = true;
				break;
			}
			dueExpression.put(regex.get(i), indexedExpression.get(idx.get(i)));
		}
		for (int i = 0; i < cells.length; i++) {
			List<String[]> teamStatus = new LinkedList<String[]>();
			for (int j = 1; j <= cells[i].length; j++) {
				String pattern = RankListCellParser.getPattern(cells[i][j]);
				if (!dueExpression.containsKey(pattern)) {
					error = true;
					break;
				}
				dueExpression.get(pattern).getInfo(RankListCellParser.getValues(cells[i][j]).toArray(new Integer[0]), contestLength);
			}
			if (error)
				break;
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

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	public void setRegex(List<String> regex) {
		this.regex = regex;
	}

	public void setIdx(List<Integer> idx) {
		this.idx = idx;
	}
}
