package cn.edu.nenu.acm.oj.entitybeans;


import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "login_log", catalog = "nenuoj")
@NamedQueries({
    @NamedQuery(name = "LoginLog.findAll", query = "SELECT l FROM LoginLog l"),
    @NamedQuery(name = "LoginLog.findById", query = "SELECT l FROM LoginLog l WHERE l.id = :id"),
    @NamedQuery(name = "LoginLog.findByLoginTime", query = "SELECT l FROM LoginLog l WHERE l.loginTime = :loginTime"),
    @NamedQuery(name = "LoginLog.findByLogoutTime", query = "SELECT l FROM LoginLog l WHERE l.logoutTime = :logoutTime"),
    @NamedQuery(name = "LoginLog.findByIpaddr", query = "SELECT l FROM LoginLog l WHERE l.ipaddr = :ipaddr"),
    @NamedQuery(name = "LoginLog.findByLastUpdateTime", query = "SELECT l FROM LoginLog l WHERE l.lastUpdateTime = :lastUpdateTime")})
public class LoginLog implements java.io.Serializable {

	private static final long serialVersionUID = 7860780970991219533L;
	private Integer id;
	private User user;
	private Date loginTime;
	private Date logoutTime;
	private String ipaddr;
	private Date lastUpdateTime;
	private Remark remark;

	public LoginLog() {
	}

	public LoginLog(User user, String ipaddr, Date lastUpdateTime) {
		this.user = user;
		this.ipaddr = ipaddr;
		this.lastUpdateTime = lastUpdateTime;
	}

	public LoginLog(User user, Date loginTime, Date logoutTime, String ipaddr, Date lastUpdateTime, Remark remark) {
		this.user = user;
		this.loginTime = loginTime;
		this.logoutTime = logoutTime;
		this.ipaddr = ipaddr;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
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
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "login_time", length = 19)
	public Date getLoginTime() {
		return this.loginTime;
	}

	public void setLoginTime(Date loginTime) {
		this.loginTime = loginTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logout_time", length = 19)
	public Date getLogoutTime() {
		return this.logoutTime;
	}

	public void setLogoutTime(Date logoutTime) {
		this.logoutTime = logoutTime;
	}

	@Column(name = "ipaddr", nullable = false, length = 128)
	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
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
	public Serializable getRemark() {
		return this.remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

}
