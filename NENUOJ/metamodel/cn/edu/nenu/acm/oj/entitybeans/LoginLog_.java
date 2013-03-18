package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(LoginLog.class)
public abstract class LoginLog_ {

	public static volatile SingularAttribute<LoginLog, Integer> id;
	public static volatile SingularAttribute<LoginLog, Date> loginTime;
	public static volatile SingularAttribute<LoginLog, String> ipaddr;
	public static volatile SingularAttribute<LoginLog, Remark> remark;
	public static volatile SingularAttribute<LoginLog, Date> lastUpdateTime;
	public static volatile SingularAttribute<LoginLog, Date> logoutTime;
	public static volatile SingularAttribute<LoginLog, User> user;

}

