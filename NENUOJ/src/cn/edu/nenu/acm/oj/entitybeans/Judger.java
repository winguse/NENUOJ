package cn.edu.nenu.acm.oj.entitybeans;

// Generated 2013-1-10 21:36:30 by Hibernate Tools 4.0.0

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Judger generated by hbm2java
 */
@Entity
@Table(name = "judger", catalog = "nenuoj")
public class Judger implements java.io.Serializable {

	private Integer id;
	private String source;
	private Serializable remark;
	private Set<Problem> problems = new HashSet<Problem>(0);

	public Judger() {
	}

	public Judger(String source) {
		this.source = source;
	}

	public Judger(String source, Serializable remark, Set<Problem> problems) {
		this.source = source;
		this.remark = remark;
		this.problems = problems;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "source", nullable = false, length = 45)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "remark")
	public Serializable getRemark() {
		return this.remark;
	}

	public void setRemark(Serializable remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "judger")
	public Set<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

}
