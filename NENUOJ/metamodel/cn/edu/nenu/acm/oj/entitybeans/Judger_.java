package cn.edu.nenu.acm.oj.entitybeans;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Judger.class)
public abstract class Judger_ {

	public static volatile SingularAttribute<Judger, Integer> id;
	public static volatile SetAttribute<Judger, Problem> problems;
	public static volatile SingularAttribute<Judger, String> source;
	public static volatile SingularAttribute<Judger, Date> lastUpdateTime;

}

