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

import cn.edu.nenu.acm.oj.statuscode.IContestType;
import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "contest", catalog = "nenuoj")
@DynamicInsert
@DynamicUpdate
@NamedQueries({
		@NamedQuery(name = "Contest.findAll", query = "SELECT c FROM Contest c"),
		@NamedQuery(name = "Contest.findById", query = "SELECT c FROM Contest c WHERE c.id = :id"),
		@NamedQuery(name = "Contest.findByTitle", query = "SELECT c FROM Contest c WHERE c.title = :title"),
		@NamedQuery(name = "Contest.findByStartTime", query = "SELECT c FROM Contest c WHERE c.startTime = :startTime"),
		@NamedQuery(name = "Contest.findByEndTime", query = "SELECT c FROM Contest c WHERE c.endTime = :endTime"),
		@NamedQuery(name = "Contest.findByContestType", query = "SELECT c FROM Contest c WHERE c.contestType = :contestType"),
		@NamedQuery(name = "Contest.findByLastUpdateTime", query = "SELECT c FROM Contest c WHERE c.lastUpdateTime = :lastUpdateTime") })
public class Contest implements Serializable,IContestType {

	private static final long serialVersionUID = 4386374575031447891L;
	private Integer id;
	private Contest contest;
	private Message message;
	private User hostUser;
	private String title;
	private Date startTime;
	private Date endTime;
	private Integer contestType;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<Solution> solutions = new HashSet<Solution>(0);
	private Set<ContestRegister> contestRegisters = new HashSet<ContestRegister>(0);
	private Set<User> enteredUsers = new HashSet<User>(0);
	private Set<ProblemDescription> problemDescriptions = new HashSet<ProblemDescription>(0);
	private Set<Contest> contests = new HashSet<Contest>(0);

	public Contest() {
	}

	public Contest(User user, String title, Integer contestType, Date lastUpdateTime) {
		this.hostUser = user;
		this.title = title;
		this.contestType = contestType;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Contest(Contest contest, Message message, User user, String title, Date startTime, Date endTime,
			Integer contestType, Date lastUpdateTime, Remark remark, Set<Solution> solutions,Set<ContestRegister> contestRegisters, Set<User> users,
			Set<ProblemDescription> problemDescriptions, Set<Contest> contests) {
		this.contest = contest;
		this.message = message;
		this.hostUser = user;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.contestType = contestType;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.solutions = solutions;
		this.contestRegisters = contestRegisters;
		this.enteredUsers = users;
		this.problemDescriptions = problemDescriptions;
		this.contests = contests;
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
	@JoinColumn(name = "clone_id")
	public Contest getContest() {
		return this.contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "host_id", nullable = false)
	public User getHostUser() {
		return this.hostUser;
	}

	public void setHostUser(User user) {
		this.hostUser = user;
	}

	@Column(name = "title", nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "start_time", length = 19)
	public Date getStartTime() {
		return this.startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "end_time", length = 19)
	public Date getEndTime() {
		return this.endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	@Column(name = "contest_type", nullable = false)
	public Integer getContestType() {
		return this.contestType;
	}

	public void setContestType(Integer contestType) {
		this.contestType = contestType;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
	public Set<Solution> getSolutions() {
		return this.solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
	public Set<ContestRegister> getContestRegisters() {
		return this.contestRegisters;
	}

	public void setContestRegisters(Set<ContestRegister> contestRegisters) {
		this.contestRegisters = contestRegisters;
	}
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contest_has_user", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "contest_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "user_id", nullable = false, updatable = false) })
	public Set<User> getEnteredUsers() {
		return this.enteredUsers;
	}

	public void setEnteredUsers(Set<User> users) {
		this.enteredUsers = users;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contest_has_problem_description", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "contest_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "problem_description_id", nullable = false, updatable = false) })
	public Set<ProblemDescription> getProblemDescriptions() {
		return this.problemDescriptions;
	}

	public void setProblemDescriptions(Set<ProblemDescription> problemDescriptions) {
		this.problemDescriptions = problemDescriptions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "contest")
	public Set<Contest> getContests() {
		return this.contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests = contests;
	}

}
