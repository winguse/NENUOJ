package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Message.class)
public abstract class Message_ {

	public static volatile SingularAttribute<Message, Message> message;
	public static volatile SingularAttribute<Message, Integer> id;
	public static volatile SetAttribute<Message, User> users;
	public static volatile SingularAttribute<Message, String> title;
	public static volatile SingularAttribute<Message, String> text;
	public static volatile SetAttribute<Message, Contest> contests;
	public static volatile SetAttribute<Message, Problem> problems;
	public static volatile SetAttribute<Message, Solution> solutions;
	public static volatile SingularAttribute<Message, Remark> remark;
	public static volatile SingularAttribute<Message, Date> lastUpdateTime;
	public static volatile SingularAttribute<Message, User> user;
	public static volatile SetAttribute<Message, Message> messages;

}

