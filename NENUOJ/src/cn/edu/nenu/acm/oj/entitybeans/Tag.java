package cn.edu.nenu.acm.oj.entitybeans;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "tag", catalog = "nenuoj")
@DynamicInsert
@DynamicUpdate
@NamedQueries({
		@NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
		@NamedQuery(name = "Tag.findById", query = "SELECT t FROM Tag t WHERE t.id = :id"),
		@NamedQuery(name = "Tag.findByText", query = "SELECT t FROM Tag t WHERE t.text = :text"),
		@NamedQuery(name = "Tag.findByLastUpdateTime", query = "SELECT t FROM Tag t WHERE t.lastUpdateTime = :lastUpdateTime") })
public class Tag implements java.io.Serializable {

	private static final long serialVersionUID = 4738888691530651460L;
	private Integer id;
	private Tag superTag;
	private String text;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<Tag> subTags = new HashSet<Tag>(0);
	private Set<Problem> problems = new HashSet<Problem>(0);

	public Tag() {
	}

	public Tag(String text, Date lastUpdateTime) {
		this.text = text;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Tag(Tag tag, String text, Date lastUpdateTime, Remark remark, Set<Tag> tags, Set<Problem> problems) {
		this.superTag = tag;
		this.text = text;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.subTags = tags;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "super_id")
	public Tag getSuperTag() {
		return this.superTag;
	}

	public void setSuperTag(Tag superTag) {
		this.superTag = superTag;
	}

	@Column(name = "text", nullable = false, length = 45)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Version
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_update_time", nullable = false, length = 19)
	public Date getLastUpdateTime() {
		return this.lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
		this.lastUpdateTime = lastUpdateTime;
	}

	@Column(name = "remark")
	public Remark getRemark() {
		if(!(remark instanceof Remark)||remark==null){
			remark=new Remark();
		}
		return remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "superTag")
	public Set<Tag> getSubTags() {
		return this.subTags;
	}

	public void setSubTags(Set<Tag> subTags) {
		this.subTags = subTags;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "problem_has_tag", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "tag_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "problem_id", nullable = false, updatable = false) })
	public Set<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

}
