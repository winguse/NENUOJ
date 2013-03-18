package cn.edu.nenu.acm.oj.entitybeans;

import cn.edu.nenu.acm.oj.util.Remark;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Tag.class)
public abstract class Tag_ {

	public static volatile SingularAttribute<Tag, Integer> id;
	public static volatile SingularAttribute<Tag, String> text;
	public static volatile SetAttribute<Tag, Problem> problems;
	public static volatile SingularAttribute<Tag, Remark> remark;
	public static volatile SingularAttribute<Tag, Date> lastUpdateTime;
	public static volatile SetAttribute<Tag, Tag> subTags;
	public static volatile SingularAttribute<Tag, Tag> superTag;

}

