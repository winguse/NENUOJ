package cn.edu.nenu.acm.oj.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.Problem_;

@Repository
public class ProblemDAO extends AbstractDAO<Problem> {

	public static final int ORDER_BY_NUMBER = 1;
	public static final int ORDER_BY_ACCEPTED = 2;
	public static final int ORDER_BY_SUBMITTED = 3;
	public static final int ORDER_BY_AC_RATE = 4;

	ProblemDAO() {
		super();
		super.setClazz(Problem.class);
	}

	/**
	 * default not included locked problem and order by number
	 * @see ProblemDAO#getProblemList(String, String, int, int, boolean, int)
	 * @param judgerSource
	 * @param filterString
	 * @param page
	 * @param pageSize
	 * @return
	 */
	public List<ProblemSimpleDTO> getProblemList(String judgerSource, String filterString, int page, int pageSize) {
		return getProblemList(judgerSource, filterString, page, pageSize, false, ORDER_BY_NUMBER);
	}

	/**
	 * Select a list of ProblemSimpleDTO of some criteria.
	 * @param judgerSource
	 * @param filterString
	 * @param page
	 * @param pageSize
	 * @param includeLoocked
	 * @param orderIndex
	 * @return
	 */
	public List<ProblemSimpleDTO> getProblemList(String judgerSource, String filterString, int page, int pageSize,
			boolean includeLoocked, int orderIndex) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Problem> query = cb.createQuery(Problem.class);
		Root<Problem> problemRoot = query.from(Problem.class);
		Predicate predicate = cb.conjunction();
		if (!includeLoocked)
			predicate = cb.and(predicate, cb.equal(problemRoot.get(Problem_.locked), false));
		if (!"".equals(judgerSource)) {
			TypedQuery<Judger> judgerQuery = em.createNamedQuery("Judger.findBySource", Judger.class);
			judgerQuery.setParameter("source", judgerSource);
			Judger judger = null;
			try {
				judger = judgerQuery.getSingleResult();
			} catch (Exception e) {
			}
			if (judger != null) {
				predicate = cb.and(predicate, cb.equal(problemRoot.get(Problem_.judger), judger));
			}
		}
		if (!"".equals(filterString)) {
			filterString = "%" + filterString + "%";
			Predicate predicateOr = cb.conjunction();
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.title), filterString));
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.source), filterString));
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.number), filterString));
			predicate = cb.and(predicate, predicateOr);
		}
		query.select(problemRoot).where(predicate);
		switch (orderIndex) {
		case ORDER_BY_NUMBER:
			query.orderBy(cb.asc(problemRoot.get(Problem_.number)));
			break;
		case -ORDER_BY_NUMBER:
			query.orderBy(cb.desc(problemRoot.get(Problem_.number)));
			break;
		case ORDER_BY_ACCEPTED:
			query.orderBy(cb.asc(problemRoot.get(Problem_.accepted)));
			break;
		case -ORDER_BY_ACCEPTED:
			query.orderBy(cb.desc(problemRoot.get(Problem_.accepted)));
			break;
		case ORDER_BY_SUBMITTED:
			query.orderBy(cb.asc(problemRoot.get(Problem_.submitted)));
			break;
		case -ORDER_BY_SUBMITTED:
			query.orderBy(cb.desc(problemRoot.get(Problem_.submitted)));
			break;
		case ORDER_BY_AC_RATE:
			query.orderBy(cb.asc(cb.quot(problemRoot.get(Problem_.accepted), problemRoot.get(Problem_.submitted))));
			break;
		case -ORDER_BY_AC_RATE:
			query.orderBy(cb.desc(cb.quot(problemRoot.get(Problem_.accepted), problemRoot.get(Problem_.submitted))));
			break;
		default:
			//don't order
		}
		List<Problem> problemList = em.createQuery(query).setFirstResult(page * pageSize).setMaxResults(pageSize)
				.getResultList();
		List<ProblemSimpleDTO> problemDTOList = new LinkedList<ProblemSimpleDTO>();
		for (Problem p : problemList) {
			problemDTOList.add(new ProblemSimpleDTO(p.isLocked(), p.getTitle(), p.getJudger().getSource(), p
					.getNumber(), p.getAccepted(), p.getSubmitted(), p.getSource()));
		}
		return problemDTOList;
	}
}
