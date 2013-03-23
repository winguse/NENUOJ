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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.edu.nenu.acm.oj.util.Remark;
/**
 * @author Winguse
 */
@Entity
@Table(name = "judger", catalog = "nenuoj", uniqueConstraints = @UniqueConstraint(columnNames = "source"))
@DynamicInsert
@DynamicUpdate
@NamedQueries({
    @NamedQuery(name = "Judger.findAll", query = "SELECT j FROM Judger j"),
    @NamedQuery(name = "Judger.findById", query = "SELECT j FROM Judger j WHERE j.id = :id"),
    @NamedQuery(name = "Judger.findBySource", query = "SELECT j FROM Judger j WHERE j.source = :source"),
    @NamedQuery(name = "Judger.findByLastUpdateTime", query = "SELECT j FROM Judger j WHERE j.lastUpdateTime = :lastUpdateTime")})
public class Judger implements Serializable {

	private static final long serialVersionUID = -3539076244150999982L;
	private Integer id;
	private String source;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<Problem> problems = new HashSet<Problem>(0);

	public Judger() {
	}

	public Judger(String source, Date lastUpdateTime) {
		this.source = source;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Judger(String source, Date lastUpdateTime, Remark remark, Set<Problem> problems) {
		this.source = source;
		this.lastUpdateTime = lastUpdateTime;
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

	@Column(name = "source", nullable = false, unique = true, length = 45)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "judger")
	public Set<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

}
