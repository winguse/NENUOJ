package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Problem.class)
public abstract class Problem_ {

	public static volatile SingularAttribute<Problem, Integer> timeLimit;
	public static volatile SetAttribute<Problem, Tag> tags;
	public static volatile SingularAttribute<Problem, Judger> judger;
	public static volatile SingularAttribute<Problem, Remark> remark;
	public static volatile SetAttribute<Problem, Solution> solutions;
	public static volatile SingularAttribute<Problem, Date> lastUpdateTime;
	public static volatile SingularAttribute<Problem, Integer> submitted;
	public static volatile SingularAttribute<Problem, String> number;
	public static volatile SetAttribute<Problem, ProblemDescription> problemDescriptions;
	public static volatile SingularAttribute<Problem, Integer> id;
	public static volatile SingularAttribute<Problem, Message> message;
	public static volatile SingularAttribute<Problem, String> title;
	public static volatile SingularAttribute<Problem, Integer> accepted;
	public static volatile SingularAttribute<Problem, String> source;
	public static volatile SingularAttribute<Problem, Boolean> locked;
	public static volatile SingularAttribute<Problem, Integer> judgingType;
	public static volatile SingularAttribute<Problem, Integer> memoryLimit;

}

