package cn.edu.nenu.acm.oj.dao;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.Problem;

@Repository
public class ProblemDAO extends AbstractDAO<Problem> {
	ProblemDAO(){
		super();
		super.setClazz(Problem.class);
	}
}
