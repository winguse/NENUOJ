package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Contest.class)
public abstract class Contest_ {

	public static volatile SetAttribute<Contest, Contest> contests;
	public static volatile SingularAttribute<Contest, Remark> remark;
	public static volatile SetAttribute<Contest, Solution> solutions;
	public static volatile SingularAttribute<Contest, Date> lastUpdateTime;
	public static volatile SingularAttribute<Contest, Contest> contest;
	public static volatile SetAttribute<Contest, ProblemDescription> problemDescriptions;
	public static volatile SingularAttribute<Contest, User> hostUser;
	public static volatile SingularAttribute<Contest, Date> endTime;
	public static volatile SetAttribute<Contest, User> enteredUsers;
	public static volatile SingularAttribute<Contest, Date> startTime;
	public static volatile SingularAttribute<Contest, Integer> id;
	public static volatile SingularAttribute<Contest, Message> message;
	public static volatile SingularAttribute<Contest, String> title;
	public static volatile SingularAttribute<Contest, Integer> contestType;

}

