package cn.edu.nenu.acm.oj.dao;

import cn.edu.nenu.acm.oj.entitybeans.ProblemDescription;

public class ProblemDescriptionDAO extends AbstractDAO<ProblemDescription> {
	ProblemDescriptionDAO() {
		super();
		super.setClazz(ProblemDescription.class);
	}
}
