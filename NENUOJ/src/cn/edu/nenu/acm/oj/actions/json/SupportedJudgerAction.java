package cn.edu.nenu.acm.oj.actions.json;

import java.util.List;

import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;
import cn.edu.nenu.acm.oj.statuscode.IContestType;

@ParentPackage("json-default")
@InterceptorRefs({ @InterceptorRef("i18n")})
@Results({ @Result(name = "success", type = "json")})
public class SupportedJudgerAction extends AbstractAction implements IContestType {
	@Autowired
	private JudgeService judgeService;

	private List<String> judgerSourceList;

	private static final long serialVersionUID = -8077897542384482842L;

	@Override
	public String execute() throws Exception {
		judgerSourceList = judgeService.getJudgerSourceList();
		return SUCCESS;
	}

	public List<String> getJudgerSourceList() {
		return judgerSourceList;
	}

}
