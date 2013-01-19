package cn.edu.nenu.acm.oj.actions;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.struts2.convention.annotation.Result;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.nenu.acm.oj.Site;
import cn.edu.nenu.acm.oj.dao.DAOTesting;
import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.util.Remark;

import com.opensymphony.xwork2.Action;

@Result(name="success",location="helloworld-success.jsp")
public class HelloWorldAction extends AbstractAction {

	private static final long serialVersionUID = 4163264957914832012L;
	
//	private String username;
//	@Autowired(required=true)
//	private EntityManagerFactory emf;
	@Autowired(required=true)
	private DAOTesting t;
	
	private String message;
	
	@Override
	public String execute() throws Exception {
	//	EntityManager em=emf.createEntityManager();
	//	User user=em.find(User.class, 1);
	//	message="input username = "+username+", database username<>:"+user.getUsername()+getText("helloWorld")+" +!";
//		log.info("Hello Terminated.");
//		log.debug("debug");
		User newUser=new User();
		newUser.setUsername(site.generateSalt());
		String salt = site.generateSalt();
		String password = site.hash("", salt);
		newUser.setPassword(password);
		newUser.setSalt(salt);
		newUser.setEmail("");
		newUser.setSchool("");
		newUser.setMajor("");
		newUser.setGrade("");
		Remark remark = new Remark();
		remark.set("nickname", "");
		newUser.setRemark(remark);
		newUser.setPermission((long) 0);
		newUser.setSloved(0);
		newUser.setSubmitted(0);
		t.persist(newUser);
		
		return SUCCESS;
	}

	public String getMessage() {
		return message;
	}
//
//	public void setUsername(String username) {
//		this.username = username;
//	}

	public DAOTesting getT() {
		return t;
	}

	public void setT(DAOTesting t) {
		this.t = t;
	}

}
