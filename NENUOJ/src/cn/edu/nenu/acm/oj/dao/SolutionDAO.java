package cn.edu.nenu.acm.oj.dao;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.dto.SolutionSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.entitybeans.Contest_;
import cn.edu.nenu.acm.oj.entitybeans.Judger_;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.Problem_;
import cn.edu.nenu.acm.oj.entitybeans.Solution;
import cn.edu.nenu.acm.oj.entitybeans.Solution_;
import cn.edu.nenu.acm.oj.entitybeans.User_;
import cn.edu.nenu.acm.oj.util.Pair;

@Repository
public class SolutionDAO extends AbstractDAO<Solution> {

	public static final int ORDER_BY_MEMORY = 5;
	public static final int ORDER_BY_RUNTIME = 6;
	public static final int ORDER_BY_CODE_LENGTH = 8;

	public SolutionDAO() {
		super();
		super.setClazz(Solution.class);
	}

	@Transactional
	public void markAllProcessingSolutionJudgeError() {
		Query query = em.createNamedQuery("Solution.updateAllStatusTo");
		query.setParameter("oldStatus", Solution.STATUS_PROCESSING);
		query.setParameter("newStatus", Solution.STATUS_JUDGE_ERROR);
		query.executeUpdate();
		query.setParameter("oldStatus", Solution.STATUS_PEDDING);
		query.setParameter("newStatus", Solution.STATUS_JUDGE_ERROR);
		query.executeUpdate();
	}

	@Transactional(readOnly = true)
	public Pair<Long, List<SolutionSimpleDTO>> getSolutionList(String username, String language, String judgerSource,
			String number, int status, int page, int pageSize, int orderIndex, boolean includeCurrentContest) {
		Pair<Long, List<SolutionSimpleDTO>> result = new Pair<Long, List<SolutionSimpleDTO>>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Pair<Predicate, Root<Solution>> countPair = getPredicate(username, language, judgerSource, number, status,
				includeCurrentContest, countQuery, cb);
		countQuery.select(cb.count(countPair.second)).where(countPair.first);
		result.first = em.createQuery(countQuery).getSingleResult();
		CriteriaQuery<Solution> query = cb.createQuery(Solution.class);
		Pair<Predicate, Root<Solution>> pair = getPredicate(username, language, judgerSource, number, status,
				includeCurrentContest, query, cb);
		Root<Solution> root = pair.second;
		switch (orderIndex) {
		case ORDER_BY_MEMORY:
			query.orderBy(cb.asc(root.get(Solution_.runMemory)));
			break;
		case -ORDER_BY_MEMORY:
			query.orderBy(cb.desc(root.get(Solution_.runMemory)));
			break;
		case ORDER_BY_RUNTIME:
			query.orderBy(cb.asc(root.get(Solution_.runTime)));
			break;
		case -ORDER_BY_RUNTIME:
			query.orderBy(cb.desc(root.get(Solution_.runTime)));
			break;
		case ORDER_BY_CODE_LENGTH:
			query.orderBy(cb.asc(root.get(Solution_.codeLength)));
			break;
		case -ORDER_BY_CODE_LENGTH:
			query.orderBy(cb.desc(root.get(Solution_.codeLength)));
			break;
		default:
			query.orderBy(cb.desc(root.get(Solution_.id)));
		}
		query.select(root).where(pair.first);
		List<Solution> solutionList = em.createQuery(query).setFirstResult(page * pageSize).setMaxResults(pageSize)
				.getResultList();
		List<SolutionSimpleDTO> solutionDTOList = new LinkedList<SolutionSimpleDTO>();
		for (Solution s : solutionList) {
			solutionDTOList
					.add(new SolutionSimpleDTO(s.getId(), s.getUser().getUsername(), s.getProblem().getJudger()
							.getSource(), s.getProblem().getNumber(), s.getProblem().getTitle(),
							s.getProblem().getId(), s.getStatus(), s.getStatusDescription(), s.getRunMemory(), s
									.getRunTime(), s.getLanguage(), s.getCodeLength(), s.getSubmitTime().getTime(), s
									.getContest() == null ? 0 : s.getContest().getId()));
		}
		result.second = solutionDTOList;
		return result;
	}

	private <T> Pair<Predicate, Root<Solution>> getPredicate(String username, String language, String judgerSource,
			String number, int status, boolean includeCurrentContest, CriteriaQuery<T> query, CriteriaBuilder cb) {
		Root<Solution> root = query.from(Solution.class);
		Predicate predicate = cb.conjunction();
		if (!includeCurrentContest) {
			Join<Solution, Contest> join = root.join(Solution_.contest, JoinType.LEFT);
			predicate = cb.and(predicate,
					cb.or(cb.isNull(join.get(Contest_.id)), cb.lessThan(join.get(Contest_.endTime), new Date())));
		}
		if (!"".equals(username)) {
			predicate = cb.and(predicate, cb.like(root.get(Solution_.user).get(User_.username), "%" + username + "%"));
		}
		if (!"".equals(language)) {
			predicate = cb.and(predicate, cb.equal(root.get(Solution_.language), language));
		}
		if (!"".equals(judgerSource)) {
			predicate = cb.and(predicate,
					cb.equal(root.get(Solution_.problem).get(Problem_.judger).get(Judger_.source), judgerSource));
		}
		if (!"".equals(number)) {
			predicate = cb.and(predicate, cb.equal(root.get(Solution_.problem).get(Problem_.number), number));
		}
		if (status > 0) {
			predicate = cb.and(predicate, cb.equal(root.get(Solution_.status), status));
		}
		Pair<Predicate, Root<Solution>> ret = new Pair<Predicate, Root<Solution>>();
		ret.first = predicate;
		ret.second = root;
		return ret;
	}

	@Transactional(readOnly = true)
	public String getJudgerSource(Integer solutionId) {
		Solution solution = this.findById(solutionId);
		return solution.getProblem().getJudger().getSource();
	}

	@Transactional(readOnly = true)
	public String getProblemNumber(Integer solutionId) {
		Solution solution = this.findById(solutionId);
		return solution.getProblem().getNumber();
	}
}
