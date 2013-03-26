package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ContestRegister.class)
public abstract class ContestRegister_ {

	public static volatile SingularAttribute<ContestRegister, Integer> id;
	public static volatile SingularAttribute<ContestRegister, Integer> status;
	public static volatile SingularAttribute<ContestRegister, Remark> remark;
	public static volatile SingularAttribute<ContestRegister, Date> lastUpdateTime;
	public static volatile SingularAttribute<ContestRegister, Contest> contest;
	public static volatile SingularAttribute<ContestRegister, User> user;

}

