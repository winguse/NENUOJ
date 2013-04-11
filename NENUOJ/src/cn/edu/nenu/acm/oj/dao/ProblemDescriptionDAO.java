package cn.edu.nenu.acm.oj.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.dto.ProblemDescriptionSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.Judger_;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription_;
import cn.edu.nenu.acm.oj.entitybeans.Problem_;
import cn.edu.nenu.acm.oj.entitybeans.User_;

@Repository
public class ProblemDescriptionDAO extends AbstractDAO<ProblemDescription> {
	ProblemDescriptionDAO() {
		super();
		super.setClazz(ProblemDescription.class);
	}

	/**
	 * 
	 * @param problemNumber
	 * @param includeLockedProblem if is false, only ADMIN can achive the problem and problem description
	 * @param judgerSource
	 * @param includeLockedDescription if include the locked problem
	 * @param userId if includeLocked is false, only the locked of the owner will be return
	 * @return a list of problem description
	 */
	public List<ProblemDescriptionSimpleDTO> getDescriptionList(String problemNumber,boolean includeLockedProblem, String judgerSource,
			boolean includeLockedDescription, int userId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProblemDescription> query = cb.createQuery(ProblemDescription.class);
		Root<ProblemDescription> root = query.from(ProblemDescription.class);
		Predicate predicate = cb.equal(root.get(ProblemDescription_.problem).get(Problem_.number), problemNumber);
		predicate = cb.and(predicate,
				cb.equal(root.get(ProblemDescription_.problem).get(Problem_.judger).get(Judger_.source), judgerSource));
		if(!includeLockedProblem){
			predicate = cb.and(predicate,cb.equal(root.get(ProblemDescription_.problem).get(Problem_.locked), false));
		}
		if (!includeLockedDescription) {
			predicate = cb.and(
					predicate,
					cb.or(cb.equal(root.get(ProblemDescription_.user).get(User_.id), userId),
							cb.equal(root.get(ProblemDescription_.locked), false)));
		}
		query.select(root).where(predicate);
		List<ProblemDescriptionSimpleDTO> pdList = new ArrayList<ProblemDescriptionSimpleDTO>();
		for (ProblemDescription pd : em.createQuery(query).getResultList()) {
			String username = pd.getUser() == null ? "System" : pd.getUser().getUsername();
			pdList.add(new ProblemDescriptionSimpleDTO(pd.getId(), pd.getProblem().getId(), pd.isLocked(),
					pd.getVote(), pd.getTitle(), username, pd.getLastUpdateTime().getTime(), (String) pd.getRemark()
							.get("versionMark")));
		}
		return pdList;
	}
}
