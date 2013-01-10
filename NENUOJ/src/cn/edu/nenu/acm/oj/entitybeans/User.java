package cn.edu.nenu.acm.oj.entitybeans;

// Generated 2013-1-10 21:36:30 by Hibernate Tools 4.0.0

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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * User generated by hbm2java
 */
@Entity
@Table(name = "user", catalog = "nenuoj", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class User implements java.io.Serializable {

	private Integer id;
	private Message message;
	private String username;
	private String password;
	private String salt;
	private String school;
	private String grade;
	private int sloved;
	private int submitted;
	private long permission;
	private Date lastUpdateTime;
	private Serializable remark;
	private Set<ProblemDescription> problemDescriptions = new HashSet<ProblemDescription>(
			0);
	private Set<Solution> solutions = new HashSet<Solution>(0);
	private Set<Contest> contests = new HashSet<Contest>(0);
	private Set<Contest> contests_1 = new HashSet<Contest>(0);
	private Set<LoginLog> loginLogs = new HashSet<LoginLog>(0);
	private Set<Message> messages = new HashSet<Message>(0);

	public User() {
	}

	public User(String username, String password, String salt, String school,
			String grade, int sloved, int submitted, long permission,
			Date lastUpdateTime) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.school = school;
		this.grade = grade;
		this.sloved = sloved;
		this.submitted = submitted;
		this.permission = permission;
		this.lastUpdateTime = lastUpdateTime;
	}

	public User(Message message, String username, String password, String salt,
			String school, String grade, int sloved, int submitted,
			long permission, Date lastUpdateTime, Serializable remark,
			Set<ProblemDescription> problemDescriptions,
			Set<Solution> solutions, Set<Contest> contests,
			Set<Contest> contests_1, Set<LoginLog> loginLogs,
			Set<Message> messages) {
		this.message = message;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.school = school;
		this.grade = grade;
		this.sloved = sloved;
		this.submitted = submitted;
		this.permission = permission;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.problemDescriptions = problemDescriptions;
		this.solutions = solutions;
		this.contests = contests;
		this.contests_1 = contests_1;
		this.loginLogs = loginLogs;
		this.messages = messages;
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
	@JoinColumn(name = "message_id")
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Column(name = "username", unique = true, nullable = false, length = 45)
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(name = "password", nullable = false, length = 64)
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "salt", nullable = false, length = 64)
	public String getSalt() {
		return this.salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	@Column(name = "school", nullable = false, length = 64)
	public String getSchool() {
		return this.school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	@Column(name = "grade", nullable = false, length = 64)
	public String getGrade() {
		return this.grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@Column(name = "sloved", nullable = false)
	public int getSloved() {
		return this.sloved;
	}

	public void setSloved(int sloved) {
		this.sloved = sloved;
	}

	@Column(name = "submitted", nullable = false)
	public int getSubmitted() {
		return this.submitted;
	}

	public void setSubmitted(int submitted) {
		this.submitted = submitted;
	}

	@Column(name = "permission", nullable = false)
	public long getPermission() {
		return this.permission;
	}

	public void setPermission(long permission) {
		this.permission = permission;
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
	public Serializable getRemark() {
		return this.remark;
	}

	public void setRemark(Serializable remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<ProblemDescription> getProblemDescriptions() {
		return this.problemDescriptions;
	}

	public void setProblemDescriptions(
			Set<ProblemDescription> problemDescriptions) {
		this.problemDescriptions = problemDescriptions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Solution> getSolutions() {
		return this.solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contest_has_user", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "contest_id", nullable = false, updatable = false) })
	public Set<Contest> getContests() {
		return this.contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests = contests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Contest> getContests_1() {
		return this.contests_1;
	}

	public void setContests_1(Set<Contest> contests_1) {
		this.contests_1 = contests_1;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<LoginLog> getLoginLogs() {
		return this.loginLogs;
	}

	public void setLoginLogs(Set<LoginLog> loginLogs) {
		this.loginLogs = loginLogs;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

}
