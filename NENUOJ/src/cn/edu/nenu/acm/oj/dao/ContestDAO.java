package cn.edu.nenu.acm.oj.dao;

import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.Contest;
import cn.edu.nenu.acm.oj.entitybeans.Judger;
@Repository
public class ContestDAO extends AbstractDAO<Contest> {

	public ContestDAO() {
		super();
		super.setClazz(Contest.class);
	}

}
