package cn.edu.nenu.acm.oj.dao;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.core.Filter.Result;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.dto.ProblemDTO;
import cn.edu.nenu.acm.oj.dto.ProblemDescriptionDTO;
import cn.edu.nenu.acm.oj.dto.ProblemDescriptionSimpleDTO;
import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.Problem;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.entitybeans.Problem_;
import cn.edu.nenu.acm.oj.util.Pair;

@Repository
public class ProblemDAO extends AbstractDAO<Problem> {

	public static final int ORDER_BY_JUDGER_SOURCE = 1;
	public static final int ORDER_BY_NUMBER = 2;
	public static final int ORDER_BY_TITLE = 3;
	public static final int ORDER_BY_ACCEPTED = 4;
	public static final int ORDER_BY_SUBMITTED = 5;
	public static final int ORDER_BY_AC_RATE = 6;
	public static final int ORDER_BY_SOURCE = 7;

	ProblemDAO() {
		super();
		super.setClazz(Problem.class);
	}

	/**
	 * return a problem dto contains a list of description simple dto and a
	 * description dto. if the descriptionId is not valid, ie, descriptionId<=0,
	 * the system crawl is returned.
	 * 
	 * @param problemId
	 * @param descriptionId
	 * @param includeLocked
	 *            if true return the description is locked, be aware of
	 *            permission
	 * @return
	 */
	@Transactional(readOnly = true)
	public ProblemDTO getProblemAndDescription(int problemId, int descriptionId, boolean includeLocked) {
		Problem problem = em.find(Problem.class, problemId);
		if (problem == null)
			return null;
		ProblemDescription problemDescription = null;
		List<ProblemDescriptionSimpleDTO> lstPDS = new ArrayList<ProblemDescriptionSimpleDTO>();
		for (ProblemDescription pd : problem.getProblemDescriptions()) {
			if (!includeLocked && pd.isLocked())
				continue;
			lstPDS.add(new ProblemDescriptionSimpleDTO(pd.getId(), problem.getId(), pd.isLocked(), pd.getVote(), pd
					.getTitle(),pd.getUser()!=null?pd.getUser().getUsername():"System",pd.getLastUpdateTime().getTime(),(String)pd.getRemark().get("versionMark")));
			if(descriptionId==pd.getId()||(descriptionId <= 0 && pd.getUser()==null)){
				problemDescription=pd;
			}
		}
		ProblemDescriptionDTO problemDescriptionDTO = null;
		if (problemDescription != null)
			problemDescriptionDTO = new ProblemDescriptionDTO(problemDescription.getId(), problem.getId(),
					problemDescription.isLocked(), problemDescription.getVote(), problemDescription.getTitle(),"System",problemDescription.getLastUpdateTime().getTime(),(String)problemDescription.getRemark().get("versionMark"),
					problemDescription.getDescription(), problemDescription.getInput(), problemDescription.getOutput(),
					problemDescription.getSampleIn(), problemDescription.getSampleOut(), problemDescription.getHint());
		return new ProblemDTO(problem.getId(), problem.isLocked(), problem.getTitle(), problem.getJudger().getSource(),
				problem.getNumber(), problem.getAccepted(), problem.getSubmitted(), problem.getSource(),
				problem.getTimeLimit(), problem.getMemoryLimit(), (String) problem.getJudger().getRemark()
						.get("longIntFormat"), problem.getJudgingType(), problemDescriptionDTO, lstPDS);
	}

	/**
	 * Select a list of ProblemSimpleDTO of some criteria.
	 * 
	 * @param judgerSource
	 * @param filterString
	 * @param page
	 * @param pageSize
	 * @param includeLoocked
	 * @param orderIndex
	 * @return
	 */
	@Transactional(readOnly = true)
	public Pair<Long, List<ProblemSimpleDTO>> getProblemList(String judgerSource, String filterString, int page,
			int pageSize, boolean includeLoocked, int orderIndex) {
		System.out.println(orderIndex + "#" + filterString + "#" + judgerSource);
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Problem> query = cb.createQuery(Problem.class);
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Problem> problemRoot = query.from(Problem.class);
		Root<Problem> countProblemRoot = countQuery.from(Problem.class);
		Predicate predicate = cb.conjunction();
		Predicate countPredicate = cb.conjunction();
		if (!includeLoocked) {
			predicate = cb.and(predicate, cb.equal(problemRoot.get(Problem_.locked), false));
			countPredicate = cb.and(countPredicate, cb.equal(countProblemRoot.get(Problem_.locked), false));
		}
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
				countPredicate = cb.and(countPredicate, cb.equal(countProblemRoot.get(Problem_.judger), judger));
			}
		}
		if (!"".equals(filterString)) {
			filterString = "%" + filterString + "%";
			System.out.println(filterString);
			Predicate predicateOr = cb.disjunction();
			Predicate countPredicateOr = cb.disjunction();
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.title), filterString));
			countPredicateOr = cb.or(countPredicateOr, cb.like(countProblemRoot.get(Problem_.title), filterString));
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.source), filterString));
			countPredicateOr = cb.or(countPredicateOr, cb.like(countProblemRoot.get(Problem_.source), filterString));
			predicateOr = cb.or(predicateOr, cb.like(problemRoot.get(Problem_.number), filterString));
			countPredicateOr = cb.or(countPredicateOr, cb.like(countProblemRoot.get(Problem_.number), filterString));
			predicate = cb.and(predicate, predicateOr);
			countPredicate = cb.and(countPredicate, countPredicateOr);
		}
		countQuery.select(cb.count(countProblemRoot)).where(countPredicate);
		Pair<Long, List<ProblemSimpleDTO>> result = new Pair<Long, List<ProblemSimpleDTO>>();
		result.first = em.createQuery(countQuery).getSingleResult();
		switch (orderIndex) {
		case ORDER_BY_JUDGER_SOURCE:
			query.orderBy(cb.asc(problemRoot.get(Problem_.judger)));
			break;
		case -ORDER_BY_JUDGER_SOURCE:
			query.orderBy(cb.desc(problemRoot.get(Problem_.judger)));
			break;
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
		case ORDER_BY_TITLE:
			query.orderBy(cb.asc(problemRoot.get(Problem_.title)));
			break;
		case -ORDER_BY_TITLE:
			query.orderBy(cb.desc(problemRoot.get(Problem_.title)));
			break;
		case ORDER_BY_SOURCE:
			query.orderBy(cb.asc(problemRoot.get(Problem_.source)));
			break;
		case -ORDER_BY_SOURCE:
			query.orderBy(cb.desc(problemRoot.get(Problem_.source)));
			break;
		default:
			System.out.println("Not ordered");
			// don't order
		}
		query.select(problemRoot).where(predicate);
		List<Problem> problemList = em.createQuery(query).setFirstResult(page * pageSize).setMaxResults(pageSize)
				.getResultList();
		List<ProblemSimpleDTO> problemDTOList = new LinkedList<ProblemSimpleDTO>();
		for (Problem p : problemList) {
			problemDTOList.add(new ProblemSimpleDTO(p.getId(), p.isLocked(), p.getTitle(), p.getJudger().getSource(), p
					.getNumber(), p.getAccepted(), p.getSubmitted(), p.getSource()));
		}
		result.second = problemDTOList;
		return result;
	}
}
