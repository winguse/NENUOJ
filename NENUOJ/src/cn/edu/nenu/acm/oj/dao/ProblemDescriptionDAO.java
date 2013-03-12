package cn.edu.nenu.acm.oj.dao;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;
@Repository
public class ProblemDescriptionDAO extends AbstractDAO<ProblemDescription> {
	ProblemDescriptionDAO() {
		super();
		super.setClazz(ProblemDescription.class);
	}
}
