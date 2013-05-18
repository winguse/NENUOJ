package cn.edu.nenu.acm.oj.dao;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import cn.edu.nenu.acm.oj.dto.ContestSimpleDTO;
import cn.edu.nenu.acm.oj.dto.ProblemSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.entitybeans.Contest_;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
import cn.edu.nenu.acm.oj.entitybeans.User_;
import cn.edu.nenu.acm.oj.util.Pair;

@Repository
public class ContestDAO extends AbstractDAO<Contest> {

	public static final int ORDER_BY_ID = 1;
	public static final int ORDER_BY_TITLE = 2;
	public static final int ORDER_BY_START_TIME = 3;
	public static final int ORDER_BY_TYPE = 5;

	public ContestDAO() {
		super();
		super.setClazz(Contest.class);
	}

	@Transactional(readOnly = true)
	public Pair<Long, List<ContestSimpleDTO>> getContestList(int contestType,
			String filterString, int page, int pageSize, int orderIndex) {
		Pair<Long, List<ContestSimpleDTO>> result = new Pair<Long, List<ContestSimpleDTO>>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contest> query = cb.createQuery(Contest.class);
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Pair<Predicate, Root<Contest>> pair = getPredicate(contestType,filterString, query,
				cb);
		Root<Contest> root = pair.second;
		Predicate predicate = pair.first;
		Pair<Predicate, Root<Contest>> countPair = getPredicate(contestType,filterString,
				countQuery, cb);
		countQuery.select(cb.count(countPair.second)).where(countPair.first);
		result.first = em.createQuery(countQuery).getSingleResult();
		switch (orderIndex) {
		case ORDER_BY_ID:
			query.orderBy(cb.asc(root.get(Contest_.id)));
			break;
		case -ORDER_BY_ID:
			query.orderBy(cb.desc(root.get(Contest_.id)));
			break;
		case ORDER_BY_TITLE:
			query.orderBy(cb.asc(root.get(Contest_.title)));
			break;
		case -ORDER_BY_TITLE:
			query.orderBy(cb.desc(root.get(Contest_.title)));
			break;
		case ORDER_BY_START_TIME:
			query.orderBy(cb.asc(root.get(Contest_.startTime)));
			break;
		case -ORDER_BY_START_TIME:
			query.orderBy(cb.desc(root.get(Contest_.startTime)));
			break;
		case ORDER_BY_TYPE:
			query.orderBy(cb.asc(root.get(Contest_.contestType)));
			break;
		case -ORDER_BY_TYPE:
			query.orderBy(cb.desc(root.get(Contest_.contestType)));
			break;
		}
		query.select(root).where(predicate);
		List<Contest> contestList = em.createQuery(query)
				.setFirstResult(page * pageSize).setMaxResults(pageSize)
				.getResultList();
		List<ContestSimpleDTO> contestDTOList = new LinkedList<ContestSimpleDTO>();
		for (Contest c : contestList) {
			contestDTOList.add(new ContestSimpleDTO(c.getId(), c.getTitle(), c
					.getStartTime().getTime(), c.getEndTime().getTime(), c
					.getHostUser().getUsername(), c.getContestType()));
		}
		result.second = contestDTOList;
		return result;
	}

	protected <T> Pair<Predicate, Root<Contest>> getPredicate(int contestType,
			String filterString, CriteriaQuery<T> query, CriteriaBuilder cb) {
		Root<Contest> contestRoot = query.from(Contest.class);
		Predicate predicate = cb.conjunction();
		if(contestType>=0){
			predicate = cb.and(predicate,cb.equal(contestRoot.get(Contest_.contestType), contestType));
		}
		if (!"".equals(filterString)) {
			Predicate predicateOr = cb.disjunction();
			predicateOr = cb.or(predicateOr,
					cb.like(contestRoot.get(Contest_.title), filterString));
			predicateOr = cb.or(
					predicateOr,
					cb.like(contestRoot.get(Contest_.hostUser).get(
							User_.username), filterString));
			predicate = cb.and(predicate, predicateOr);
		}
		return new Pair<Predicate, Root<Contest>>(predicate, contestRoot);
	}
}
