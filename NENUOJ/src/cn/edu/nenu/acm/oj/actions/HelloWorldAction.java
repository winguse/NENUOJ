package cn.edu.nenu.acm.oj.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;
import cn.edu.nenu.acm.oj.entitybeans.User;

import com.opensymphony.xwork2.Action;

@Result(name="success",location="/helloworld-success.jsp")
public class HelloWorldAction extends AbstractAction {

	private String username;
	@Autowired(required=true)
	private EntityManagerFactory emf;
	
	private String message;
	
	@Override
	public String execute() throws Exception {
		EntityManager em=emf.createEntityManager();
		User user=em.find(User.class, 1);
		message="input username = "+username+", database username:"+user.getUsername();
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
