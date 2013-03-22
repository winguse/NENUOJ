package cn.edu.nenu.acm.oj.dao;

import javax.persistence.Query;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.entitybeans.Solution;
@Repository
public class SolutionDAO extends AbstractDAO<Solution> {

	public SolutionDAO() {
		super();
		super.setClazz(Solution.class);
	}
	
	@Transactional("transactionManager")
	public void markAllProcessingSolutionJudgeError() {
		Query query = em.createNamedQuery("Solution.updateAllStatusTo");
		query.setParameter("oldStatus", Solution.STATUS_PROCESSING);
		query.setParameter("newStatus", Solution.STATUS_JUDGE_ERROR);
		query.executeUpdate();
		query.setParameter("oldStatus", Solution.STATUS_PEDDING);
		query.setParameter("newStatus", Solution.STATUS_JUDGE_ERROR);
		query.executeUpdate();
	}
	
	
	@Transactional(readOnly=true)
	public String getJudgerSource(Integer solutionId){
		Solution solution = this.findById(solutionId);
		return solution.getProblem().getJudger().getSource();
	}

	@Transactional(readOnly=true)
	public String getProblemNumber(Integer solutionId) {
		Solution solution = this.findById(solutionId);
		return solution.getProblem().getNumber();
	}
}
