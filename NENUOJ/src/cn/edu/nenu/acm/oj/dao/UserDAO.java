package cn.edu.nenu.acm.oj.dao;

import java.util.List;

import javax.persistence.Query;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Repository;

import cn.edu.nenu.acm.oj.entitybeans.User;

@Repository
public class UserDAO extends AbstractDAO<User> {
	public UserDAO(){
		super();
		super.setClazz(User.class);
	}
	
	public boolean isUsernameExist(String username){
		return findUserByUsername(username)!=null;
	}
	
	public User findUserByUsername(String username){
		Query query=em.createNamedQuery("User.findByUsername");
		query.setParameter("username", username);
		@SuppressWarnings("unchecked")
		List<User> lstUser=query.getResultList();
		return lstUser.size()==0?null:lstUser.get(0);
	}
}
