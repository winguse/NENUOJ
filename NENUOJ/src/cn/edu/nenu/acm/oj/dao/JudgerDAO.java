package cn.edu.nenu.acm.oj.dao;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.Judger;
@Repository
public class JudgerDAO extends AbstractDAO<Judger> {

	public JudgerDAO() {
		super();
		super.setClazz(Judger.class);
	}

}
