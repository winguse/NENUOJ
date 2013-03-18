package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProblemDescription.class)
public abstract class ProblemDescription_ {

	public static volatile SetAttribute<ProblemDescription, Contest> contests;
	public static volatile SingularAttribute<ProblemDescription, Remark> remark;
	public static volatile SingularAttribute<ProblemDescription, String> sampleIn;
	public static volatile SingularAttribute<ProblemDescription, Integer> vote;
	public static volatile SingularAttribute<ProblemDescription, Date> lastUpdateTime;
	public static volatile SingularAttribute<ProblemDescription, String> hint;
	public static volatile SingularAttribute<ProblemDescription, Problem> problem;
	public static volatile SingularAttribute<ProblemDescription, Integer> id;
	public static volatile SingularAttribute<ProblemDescription, String> input;
	public static volatile SingularAttribute<ProblemDescription, String> title;
	public static volatile SingularAttribute<ProblemDescription, String> description;
	public static volatile SingularAttribute<ProblemDescription, Boolean> locked;
	public static volatile SingularAttribute<ProblemDescription, String> output;
	public static volatile SingularAttribute<ProblemDescription, User> user;
	public static volatile SingularAttribute<ProblemDescription, String> sampleOut;

}

