package cn.edu.nenu.acm.oj.dao;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.User;

@Repository
@Scope("request")
public class UserDAO extends GenericDAO<User> {
	public UserDAO(){
		super();
		super.setClazz(User.class);
	}
	
	public boolean isUsernameExist(String username){
		Query query=em.createNamedQuery("User.findByUsername");
		query.setParameter("username", username);
		return query.getResultList().size()>0;
	}
}
