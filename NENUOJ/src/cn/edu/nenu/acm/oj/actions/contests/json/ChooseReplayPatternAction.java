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
import cn.edu.nenu.acm.oj.util.Remark;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n"),
	@InterceptorRef("jsonValidationWorkflowStack") })
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
		if(session == null){
			code = CODE_ERROR;
			message = _("Could not find any session.");
			return SUCCESS;
		}
		if(!session.containsKey("indexedExpression")||!session.containsKey("rankListCells")||!session.containsKey("addedContestId")){
			code = CODE_ERROR;
			message = _("Could not find any replay data to be choosen.");
			return SUCCESS;
		}
		Map<Integer, RankListCellExpression> indexedExpression = (Map<Integer, RankListCellExpression>) session
				.get("indexedExpression");
		session.remove("indexedExpression");
		String cells[][] = (String[][]) session.get("rankListCells");
		session.remove("rankListCells");
		int addedContestId = (int) session.get("addedContestId");
		session.remove("addedContestId");
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
			List<long[]> teamStatus = new LinkedList<long[]>();
			for (int j = 1; j < cells[i].length; j++) {
				String pattern = RankListCellParser.getPattern(cells[i][j]);
				if(cells[i][j].trim().equals("")){
					teamStatus.add(new long[]{});
				}else if (!dueExpression.containsKey(pattern)) {
					error = true;
					break;
				}else{
					long[] tmp = dueExpression.get(pattern).getInfo(RankListCellParser.getValues(cells[i][j]).toArray(new Integer[0]), contestLength);
					if(tmp[1]==0)//submit is 0 time
						teamStatus.add(new long[]{});
					else
						teamStatus.add(tmp);
				}
			}
			if (error)
				break;
			json.put(cells[i][0], teamStatus);
		}
		if(error){
			code = CODE_ERROR;
			message = _("Something gone wrong.. How about you submit data, is it correct?");
		}else{
			code = CODE_SUCCESS;
			message = _("Replay Data added successfully.");
			Remark remark = contest.getRemark();
			remark.set("rank", json);
			System.out.println(json.toString());
			contest.setRemark(remark);
			dao.merge(contest);
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

	public void setRegex(List<String> regex) {
		this.regex = regex;
	}

	public void setIdx(List<Integer> idx) {
		this.idx = idx;
	}

	@Override
	public void setSession(Map<String, Object> arg0) {
		this.session = arg0;
	}
}
