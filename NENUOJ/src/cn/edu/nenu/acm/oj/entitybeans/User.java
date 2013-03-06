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
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "user", catalog = "nenuoj", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
@NamedQueries({
		@NamedQuery(name = "User.findAll", query = "SELECT u FROM User u"),
		@NamedQuery(name = "User.findById", query = "SELECT u FROM User u WHERE u.id = :id"),
		@NamedQuery(name = "User.findByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
		@NamedQuery(name = "User.findByPassword", query = "SELECT u FROM User u WHERE u.password = :password"),
		@NamedQuery(name = "User.findBySalt", query = "SELECT u FROM User u WHERE u.salt = :salt"),
		@NamedQuery(name = "User.findByEmail", query = "SELECT u FROM User u WHERE u.email = :email"),
		@NamedQuery(name = "User.findBySchool", query = "SELECT u FROM User u WHERE u.school = :school"),
		@NamedQuery(name = "User.findByGrade", query = "SELECT u FROM User u WHERE u.grade = :grade"),
		@NamedQuery(name = "User.findByMajor", query = "SELECT u FROM User u WHERE u.major = :major"),
		@NamedQuery(name = "User.findBySloved", query = "SELECT u FROM User u WHERE u.sloved = :sloved"),
		@NamedQuery(name = "User.findBySubmitted", query = "SELECT u FROM User u WHERE u.submitted = :submitted"),
		@NamedQuery(name = "User.findByPermission", query = "SELECT u FROM User u WHERE u.permission = :permission"),
		@NamedQuery(name = "User.findByLastUpdateTime", query = "SELECT u FROM User u WHERE u.lastUpdateTime = :lastUpdateTime") })
public class User implements java.io.Serializable {

	private static final long serialVersionUID = -2468070911644628921L;
	private Integer id;
	private Message message;
	private String username;
	private String password;
	private String salt;
	private String email;
	private String school;
	private String grade;
	private String major;
	private Integer sloved;
	private Integer submitted;
	private Long permission;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<ProblemDescription> problemDescriptions = new HashSet<ProblemDescription>(0);
	private Set<Solution> solutions = new HashSet<Solution>(0);
	private Set<Contest> enteredContests = new HashSet<Contest>(0);
	private Set<Contest> hostedContests = new HashSet<Contest>(0);
	private Set<LoginLog> loginLogs = new HashSet<LoginLog>(0);
	private Set<Message> messages = new HashSet<Message>(0);

	public User() {
	}

	public User(String username, String password, String salt, String email, String school, String grade, String major,
			Integer sloved, Integer submitted, Long permission, Date lastUpdateTime) {
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.school = school;
		this.grade = grade;
		this.major = major;
		this.sloved = sloved;
		this.submitted = submitted;
		this.permission = permission;
		this.lastUpdateTime = lastUpdateTime;
	}

	public User(Message message, String username, String password, String salt, String email, String school,
			String grade, String major, Integer sloved, Integer submitted, Long permission, Date lastUpdateTime,
			Remark remark, Set<ProblemDescription> problemDescriptions, Set<Solution> solutions,
			Set<Contest> contests, Set<Contest> contests_1, Set<LoginLog> loginLogs, Set<Message> messages) {
		this.message = message;
		this.username = username;
		this.password = password;
		this.salt = salt;
		this.email = email;
		this.school = school;
		this.grade = grade;
		this.major = major;
		this.sloved = sloved;
		this.submitted = submitted;
		this.permission = permission;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.problemDescriptions = problemDescriptions;
		this.solutions = solutions;
		this.enteredContests = contests;
		this.hostedContests = contests_1;
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

	@Column(name = "email", nullable = false, length = 64)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	@Column(name = "major", nullable = false, length = 64)
	public String getMajor() {
		return this.major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	@Column(name = "sloved", nullable = false)
	public Integer getSloved() {
		return this.sloved;
	}

	public void setSloved(Integer sloved) {
		this.sloved = sloved;
	}

	@Column(name = "submitted", nullable = false)
	public Integer getSubmitted() {
		return this.submitted;
	}

	public void setSubmitted(Integer submitted) {
		this.submitted = submitted;
	}

	@Column(name = "permission", nullable = false)
	public Long getPermission() {
		return this.permission;
	}

	public void setPermission(Long permission) {
		this.permission = permission;
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
		return this.remark;
	}

	public void setRemark(Remark remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	public Set<ProblemDescription> getProblemDescriptions() {
		return this.problemDescriptions;
	}

	public void setProblemDescriptions(Set<ProblemDescription> problemDescriptions) {
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
	public Set<Contest> getEnteredContests() {
		return this.enteredContests;
	}

	public void setEnteredContests(Set<Contest> enteredContests) {
		this.enteredContests = enteredContests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "hostUser")
	public Set<Contest> getHostedContests() {
		return this.hostedContests;
	}

	public void setHostedContests(Set<Contest> hostedContests) {
		this.hostedContests = hostedContests;
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
