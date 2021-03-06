package cn.edu.nenu.acm.oj.actions.problems;

import java.util.List;

import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.actions.AbstractAction;
import cn.edu.nenu.acm.oj.service.impl.JudgeService;

@ParentPackage("struts-default")
@Result(name="success",location="list-success.jsp")
public class ListAction extends AbstractAction {

	private static final long serialVersionUID = -8077897542384482842L;
	
	@Autowired
	private JudgeService judgeService;
	
	private List<String> judgerSourceList;
	
	@Override
	public String execute() throws Exception {
		judgerSourceList = judgeService.getJudgerSourceList();
		return SUCCESS;
	}

	public List<String> getJudgerSourceList() {
		return judgerSourceList;
	}	
}
