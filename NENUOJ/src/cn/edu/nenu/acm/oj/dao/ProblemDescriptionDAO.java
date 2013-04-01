package cn.edu.nenu.acm.oj.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.dto.ProblemDescriptionSimpleDTO;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription_;
import cn.edu.nenu.acm.oj.entitybeans.Problem_;

@Repository
public class ProblemDescriptionDAO extends AbstractDAO<ProblemDescription> {
	ProblemDescriptionDAO() {
		super();
		super.setClazz(ProblemDescription.class);
	}

	public List<ProblemDescriptionSimpleDTO> getDescriptionList(int problemId) {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<ProblemDescription> query = cb.createQuery(ProblemDescription.class);
		Root<ProblemDescription> root = query.from(ProblemDescription.class);
		Predicate predicate = cb.equal(root.get(ProblemDescription_.problem).get(Problem_.id), problemId);
		query.select(root).where(predicate);
		List<ProblemDescriptionSimpleDTO> pdList = new ArrayList<ProblemDescriptionSimpleDTO>();
		for (ProblemDescription pd : em.createQuery(query).getResultList()) {
			pdList.add(new ProblemDescriptionSimpleDTO(pd.getId(), pd.getProblem().getId(), pd.isLocked(),
					pd.getVote(), pd.getTitle(), pd.getUser().getUsername(), pd.getLastUpdateTime().getTime(),
					(String) pd.getRemark().get("versionMark")));
		}
		return pdList;
	}
}
