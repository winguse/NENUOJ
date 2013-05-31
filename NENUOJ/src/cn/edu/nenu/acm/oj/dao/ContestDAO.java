package cn.edu.nenu.acm.oj.dao;

import java.util.Date;
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

	public static final int LIST_PUBLIC = 1;
	public static final int LIST_PRIVATE = 2;
	public static final int LIST_REGISTERATION = 4;
	public static final int LIST_REPLAY = 8;
	public static final int LIST_RUNNING = 16;
	public static final int LIST_PASSED = 32;
	public static final int LIST_PEDDING = 64;

	public ContestDAO() {
		super();
		super.setClazz(Contest.class);
	}

	@Transactional(readOnly = true)
	public Pair<Long, List<ContestSimpleDTO>> getContestList(int contestStatus,
			String filterString, int page, int pageSize, int orderIndex) {
		Pair<Long, List<ContestSimpleDTO>> result = new Pair<Long, List<ContestSimpleDTO>>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Contest> query = cb.createQuery(Contest.class);
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Pair<Predicate, Root<Contest>> pair = getPredicate(contestStatus,
				filterString, query, cb);
		Root<Contest> root = pair.second;
		Predicate predicate = pair.first;
		Pair<Predicate, Root<Contest>> countPair = getPredicate(contestStatus,
				filterString, countQuery, cb);
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

	protected <T> Pair<Predicate, Root<Contest>> getPredicate(
			int contestStatus, String filterString, CriteriaQuery<T> query,
			CriteriaBuilder cb) {
		Root<Contest> contestRoot = query.from(Contest.class);
		Predicate predicate = cb.conjunction();
		if (contestStatus > 0) {
			boolean used = false;
			Predicate predicateOrForType = cb.disjunction();
			if ((contestStatus & LIST_PUBLIC) == LIST_PUBLIC) {
				predicateOrForType = cb.or(predicateOrForType, cb.equal(
						contestRoot.get(Contest_.contestType),
						Contest.CONTEST_TYPE_PUBLIC));
				used = true;
			}
			if ((contestStatus & LIST_PRIVATE) == LIST_PRIVATE) {
				predicateOrForType = cb.or(predicateOrForType, cb.equal(
						contestRoot.get(Contest_.contestType),
						Contest.CONTEST_TYPE_PRIVATE));
				used = true;
			}
			if ((contestStatus & LIST_REGISTERATION) == LIST_REGISTERATION) {
				predicateOrForType = cb.or(predicateOrForType, cb.equal(
						contestRoot.get(Contest_.contestType),
						Contest.CONTEST_TYPE_REGISTRATION_NEEDED));
				used = true;
			}
			if ((contestStatus & LIST_REPLAY) == LIST_REPLAY) {
				predicateOrForType = cb.or(predicateOrForType, cb.equal(
						contestRoot.get(Contest_.contestType),
						Contest.CONTEST_TYPE_REPLAY));
				used = true;
			}
			if (used)
				predicate = cb.and(predicate, predicateOrForType);
			used = false;
			Predicate predicateOrForTime = cb.disjunction();
			Date now = new Date();
			if ((contestStatus & LIST_RUNNING) == LIST_RUNNING) {
				predicateOrForTime = cb
						.or(predicateOrForTime, cb.and(cb.lessThan(
								contestRoot.get(Contest_.startTime), now), cb
								.greaterThan(contestRoot.get(Contest_.endTime),
										now)));
				used = true;
			}
			if ((contestStatus & LIST_PASSED) == LIST_PASSED) {
				predicateOrForTime = cb.or(predicateOrForTime,
						cb.lessThan(contestRoot.get(Contest_.endTime), now));
				used = true;
			}
			if ((contestStatus & LIST_PEDDING) == LIST_PEDDING) {
				predicateOrForTime = cb.or(predicateOrForTime, cb.greaterThan(
						contestRoot.get(Contest_.startTime), now));
				used = true;
			}
			if (used)
				predicate = cb.and(predicate, predicateOrForTime);
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
	
	@Transactional(readOnly=true)
	public ContestSimpleDTO getContestBasicInfo(int contestId){
		
		return null;
	} 
}
