package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Solution.class)
public abstract class Solution_ {

	public static volatile SingularAttribute<Solution, Boolean> shared;
	public static volatile SingularAttribute<Solution, String> sourceCode;
	public static volatile SingularAttribute<Solution, Integer> codeLength;
	public static volatile SingularAttribute<Solution, Integer> status;
	public static volatile SingularAttribute<Solution, Remark> remark;
	public static volatile SingularAttribute<Solution, Date> lastUpdateTime;
	public static volatile SingularAttribute<Solution, Date> submitTime;
	public static volatile SingularAttribute<Solution, Integer> runMemory;
	public static volatile SingularAttribute<Solution, Contest> contest;
	public static volatile SingularAttribute<Solution, Integer> runTime;
	public static volatile SingularAttribute<Solution, Problem> problem;
	public static volatile SingularAttribute<Solution, Float> passRate;
	public static volatile SingularAttribute<Solution, Integer> id;
	public static volatile SingularAttribute<Solution, Message> message;
	public static volatile SingularAttribute<Solution, String> statusDescription;
	public static volatile SingularAttribute<Solution, String> ipaddr;
	public static volatile SingularAttribute<Solution, Date> judgeTime;
	public static volatile SingularAttribute<Solution, String> language;
	public static volatile SingularAttribute<Solution, User> user;

}

