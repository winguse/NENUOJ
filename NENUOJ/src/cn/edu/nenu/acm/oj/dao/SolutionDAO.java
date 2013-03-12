package cn.edu.nenu.acm.oj.dao;

import javax.persistence.Query;

import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.entitybeans.Solution;

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
}
