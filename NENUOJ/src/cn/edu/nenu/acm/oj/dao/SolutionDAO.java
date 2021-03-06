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

import cn.edu.nenu.acm.oj.dto.ContestSimpleDTO;
import cn.edu.nenu.acm.oj.dto.MessageDTO;
import cn.edu.nenu.acm.oj.dto.SolutionDTO;
import cn.edu.nenu.acm.oj.dto.SolutionSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.entitybeans.Contest_;
import cn.edu.nenu.acm.oj.entitybeans.Judger_;
import cn.edu.nenu.acm.oj.entitybeans.Message;
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

	// @Transactional
	// public void markJudgerSourceError(String judgerSource,String
	// statusDescription){
	// Query query = em.createNamedQuery("Solution.markJudgerSourceError");
	// query.setParameter("judgerSource", judgerSource);
	// query.setParameter("statusDescription", statusDescription);
	// query.executeUpdate();
	// NamedQuery reported a bug? or my fault? changed the solution into
	// criteria query
	// }

	@Transactional(readOnly = true)
	public List<SolutionSimpleDTO> getSolutionList(List<Integer> runIds,
			boolean includeCurrentContest) {
		List<SolutionSimpleDTO> result = new LinkedList<SolutionSimpleDTO>();
		long nowTimestamp = new Date().getTime();
		for (Integer runId : runIds) {
			Solution s = em.find(Solution.class, runId);
			if (!includeCurrentContest) {
				if (s.getContest() != null
						&& s.getContest().getEndTime().getTime() > nowTimestamp) {
					continue;
				}
			}
			result.add(solutionSimpleDTOAssembler(s));
		}
		return result;
	}

	@Transactional(readOnly = true)
	public Pair<Long, List<SolutionSimpleDTO>> getSolutionList(String username,
			String language, String judgerSource, String number, int status,
			int page, int pageSize, int orderIndex,
			boolean includeCurrentContest) {
		Pair<Long, List<SolutionSimpleDTO>> result = new Pair<Long, List<SolutionSimpleDTO>>();
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Pair<Predicate, Root<Solution>> countPair = getPredicate(username,
				language, judgerSource, number, status, includeCurrentContest,
				countQuery, cb);
		countQuery.select(cb.count(countPair.second)).where(countPair.first);
		result.first = em.createQuery(countQuery).getSingleResult();
		CriteriaQuery<Solution> query = cb.createQuery(Solution.class);
		Pair<Predicate, Root<Solution>> pair = getPredicate(username, language,
				judgerSource, number, status, includeCurrentContest, query, cb);
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
		List<Solution> solutionList = em.createQuery(query)
				.setFirstResult(page * pageSize).setMaxResults(pageSize)
				.getResultList();
		List<SolutionSimpleDTO> solutionDTOList = new LinkedList<SolutionSimpleDTO>();
		for (Solution s : solutionList) {
			solutionDTOList.add(solutionSimpleDTOAssembler(s));
		}
		result.second = solutionDTOList;
		return result;
	}

	private <T> Pair<Predicate, Root<Solution>> getPredicate(String username,
			String language, String judgerSource, String number, int status,
			boolean includeCurrentContest, CriteriaQuery<T> query,
			CriteriaBuilder cb) {
		Root<Solution> root = query.from(Solution.class);
		Predicate predicate = cb.conjunction();
		if (!includeCurrentContest) {
			Join<Solution, Contest> join = root.join(Solution_.contest,
					JoinType.LEFT);
			predicate = cb.and(predicate, cb.or(
					cb.isNull(join.get(Contest_.id)),
					cb.lessThan(join.get(Contest_.endTime), new Date())));
		}
		if (!"".equals(username)) {
			predicate = cb.and(predicate,
			// cb.like(root.get(Solution_.user).get(User_.username), "%"+
			// username + "%"));
					cb.equal(root.get(Solution_.user).get(User_.username),
							username));
		}
		if (!"".equals(language)) {
			predicate = cb.and(predicate,
					cb.equal(root.get(Solution_.language), language));
		}
		if (!"".equals(judgerSource)) {
			predicate = cb.and(
					predicate,
					cb.equal(root.get(Solution_.problem).get(Problem_.judger)
							.get(Judger_.source), judgerSource));
		}
		if (!"".equals(number)) {
			predicate = cb.and(predicate, cb.equal(root.get(Solution_.problem)
					.get(Problem_.number), number));
		}
		if (status > 0) {
			predicate = cb.and(predicate,
					cb.equal(root.get(Solution_.status), status));
		}
		Pair<Predicate, Root<Solution>> ret = new Pair<Predicate, Root<Solution>>();
		ret.first = predicate;
		ret.second = root;
		return ret;
	}

	@Transactional(readOnly = true)
	public SolutionDTO findSolution(int runId) {
		return solutionDTOAssembler(findById(runId));
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

	private static SolutionSimpleDTO solutionSimpleDTOAssembler(Solution s) {
		int remoteRunId = 0;
		try {
			remoteRunId = (int) s.getRemark().get("RemoteRunId");
		} catch (Exception e) {
		}
		return new SolutionSimpleDTO(s.getId(), s.getUser().getUsername(), s
				.getProblem().getJudger().getSource(), s.getProblem()
				.getNumber(), s.getProblem().getTitle(),
				s.getProblem().getId(), s.getStatus(),
				s.getStatusDescription(), s.getRunMemory(), s.getRunTime(),
				s.getLanguage(), s.getCodeLength(),
				s.getSubmitTime().getTime(), s.getContest() == null ? 0 : s
						.getContest().getId(), s.isShared(),remoteRunId);
	}

	private static SolutionDTO solutionDTOAssembler(Solution s) {
		if (s == null)
			return null;
		int remoteRunId = 0;
		try {
			remoteRunId = (int) s.getRemark().get("RemoteRunId");
		} catch (Exception e) {
		}
		String additionalInformation = "";
		Object tmp = s.getRemark().get("AdditionalInformation");
		if (tmp != null && (tmp instanceof String)) {
			additionalInformation = (String) tmp;
		}
		MessageDTO messageDTO = new MessageDTO();
		Message message = s.getMessage();
		if (message != null) {
			messageDTO.setReplyCount(message.getMessages().size());
		}
		return new SolutionDTO(s.getId(), s.getUser().getUsername(), s
				.getProblem().getJudger().getSource(), s.getProblem()
				.getNumber(), s.getProblem().getTitle(),
				s.getProblem().getId(), s.getStatus(),
				s.getStatusDescription(), s.getRunMemory(), s.getRunTime(),
				s.getLanguage(), s.getCodeLength(),
				s.getSubmitTime().getTime(), s.getContest() == null ? 0 : s
						.getContest().getId(), s.isShared(), remoteRunId,
				s.getSourceCode(), s.getJudgeTime().getTime(), s.getPassRate(),
				additionalInformation, s.getIpaddr(), messageDTO,
				contestSimpleDTOAssembler(s.getContest()));
	}

	private static ContestSimpleDTO contestSimpleDTOAssembler(Contest c) {
		if (c == null)
			return null;
		return new ContestSimpleDTO(c.getId(), c.getTitle(), c.getStartTime()
				.getTime(), c.getEndTime().getTime(), c.getHostUser()
				.getUsername(), c.getContestType());
	}
	
	@Transactional
	public Solution merge(Solution solution,int oldStatus) {
		solution = em.merge(solution);
		if(solution.getStatus()!=oldStatus){
			Problem problem = solution.getProblem();
			if(oldStatus == Solution.STATUS_ACCEPTED){
				problem.setAccepted(problem.getAccepted()-1);
			}
			if(solution.getStatus() == Solution.STATUS_ACCEPTED){
				problem.setAccepted(problem.getAccepted()+1);
			}
			em.merge(problem);
		}
		return solution;
	}
}
