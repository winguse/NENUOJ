package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(User.class)
public abstract class User_ {

	public static volatile SetAttribute<User, Solution> solutions;
	public static volatile SingularAttribute<User, Remark> remark;
	public static volatile SingularAttribute<User, Date> lastUpdateTime;
	public static volatile SingularAttribute<User, Integer> submitted;
	public static volatile SetAttribute<User, ProblemDescription> problemDescriptions;
	public static volatile SingularAttribute<User, Long> permission;
	public static volatile SingularAttribute<User, String> password;
	public static volatile SetAttribute<User, LoginLog> loginLogs;
	public static volatile SetAttribute<User, Message> messages;
	public static volatile SingularAttribute<User, Integer> id;
	public static volatile SingularAttribute<User, Message> message;
	public static volatile SingularAttribute<User, String> username;
	public static volatile SetAttribute<User, Contest> enteredContests;
	public static volatile SingularAttribute<User, String> school;
	public static volatile SetAttribute<User, Contest> hostedContests;
	public static volatile SingularAttribute<User, String> email;
	public static volatile SingularAttribute<User, Integer> sloved;
	public static volatile SingularAttribute<User, String> grade;
	public static volatile SingularAttribute<User, String> major;
	public static volatile SingularAttribute<User, String> salt;

}

