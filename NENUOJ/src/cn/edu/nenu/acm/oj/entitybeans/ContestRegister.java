package cn.edu.nenu.acm.oj.entitybeans;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "contest_register", catalog = "nenuoj", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "contest_id"}))
@DynamicInsert
@DynamicUpdate
@NamedQueries({
		@NamedQuery(name = "ContestRegister.findAll", query = "SELECT c FROM ContestRegister c"),
		@NamedQuery(name = "ContestRegister.findById", query = "SELECT c FROM ContestRegister c WHERE c.id = :id"),
		@NamedQuery(name = "ContestRegister.findByContest", query = "SELECT c FROM ContestRegister c WHERE c.contest = :contest"),
		@NamedQuery(name = "ContestRegister.findByUser", query = "SELECT c FROM ContestRegister c WHERE c.user = :user")
})
public class ContestRegister implements Serializable {

	private static final long serialVersionUID = -545566856987026075L;

	private Integer id;
	private User user;
	private Contest contest;
	private Integer status;
	private Date lastUpdateTime;
	private Remark remark;

	public ContestRegister() {
	}

	public ContestRegister(Integer id, User user, Contest contest, Integer status, Date lastUpdateTime) {
		this.id = id;
		this.user = user;
		this.contest = contest;
		this.status = status;
		this.lastUpdateTime = lastUpdateTime;
	}

	public ContestRegister(Integer id, User user, Contest contest, Integer status, Date lastUpdateTime, Remark remark) {
		this.id = id;
		this.user = user;
		this.contest = contest;
		this.status = status;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
	}

	@Id
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contest_id", nullable = false)
	public Contest getContest() {
		return this.contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

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
		return this.remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

}
