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
import javax.persistence.UniqueConstraint;

import cn.edu.nenu.acm.oj.statuscode.IProblemJudgeType;
import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "problem", catalog = "nenuoj", uniqueConstraints = @UniqueConstraint(columnNames = "source"))
@NamedQueries({
		@NamedQuery(name = "Problem.findAll", query = "SELECT p FROM Problem p"),
		@NamedQuery(name = "Problem.findById", query = "SELECT p FROM Problem p WHERE p.id = :id"),
		@NamedQuery(name = "Problem.findByTitle", query = "SELECT p FROM Problem p WHERE p.title = :title"),
		@NamedQuery(name = "Problem.findByNumber", query = "SELECT p FROM Problem p WHERE p.number = :number"),
		@NamedQuery(name = "Problem.findBySource", query = "SELECT p FROM Problem p WHERE p.source = :source"),
		@NamedQuery(name = "Problem.findByLocked", query = "SELECT p FROM Problem p WHERE p.locked = :locked"),
		@NamedQuery(name = "Problem.findByTimeLimit", query = "SELECT p FROM Problem p WHERE p.timeLimit = :timeLimit"),
		@NamedQuery(name = "Problem.findByMemoryLimit", query = "SELECT p FROM Problem p WHERE p.memoryLimit = :memoryLimit"),
		@NamedQuery(name = "Problem.findByAccepted", query = "SELECT p FROM Problem p WHERE p.accepted = :accepted"),
		@NamedQuery(name = "Problem.findBySubmitted", query = "SELECT p FROM Problem p WHERE p.submitted = :submitted"),
		@NamedQuery(name = "Problem.findByJudgingType", query = "SELECT p FROM Problem p WHERE p.judgingType = :judgingType"),
		@NamedQuery(name = "Problem.findByJudgerAndNumber", query = "SELECT p FROM Problem p WHERE p.judger = :judger AND p.number = :number"),
		@NamedQuery(name = "Problem.findByLastUpdateTime", query = "SELECT p FROM Problem p WHERE p.lastUpdateTime = :lastUpdateTime") })
public class Problem implements java.io.Serializable, IProblemJudgeType {

	private static final long serialVersionUID = 3828345133405527155L;

	private Integer id;
	private Message message;
	private Judger judger;
	private String title;
	private String number;
	private String source;
	private boolean locked;
	private Integer timeLimit;
	private Integer memoryLimit;
	private Integer accepted;
	private Integer submitted;
	private Integer judgingType;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<Solution> solutions = new HashSet<Solution>(0);
	private Set<Tag> tags = new HashSet<Tag>(0);
	private Set<ProblemDescription> problemDescriptions = new HashSet<ProblemDescription>(0);

	public Problem() {
	}

	public Problem(Judger judger, String title, String number, String source, boolean locked, Integer timeLimit,
			Integer memoryLimit, Integer accepted, Integer submitted, Integer judgingType, Date lastUpdateTime) {
		this.judger = judger;
		this.title = title;
		this.number = number;
		this.source = source;
		this.locked = locked;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.accepted = accepted;
		this.submitted = submitted;
		this.judgingType = judgingType;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Problem(Message message, Judger judger, String title, String number, String source, boolean locked,
			Integer timeLimit, Integer memoryLimit, Integer accepted, Integer submitted, Integer judgingType,
			Date lastUpdateTime, Remark remark, Set<Solution> solutions, Set<Tag> tags,
			Set<ProblemDescription> problemDescriptions) {
		this.message = message;
		this.judger = judger;
		this.title = title;
		this.number = number;
		this.source = source;
		this.locked = locked;
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.accepted = accepted;
		this.submitted = submitted;
		this.judgingType = judgingType;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.solutions = solutions;
		this.tags = tags;
		this.problemDescriptions = problemDescriptions;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "judger_id", nullable = false)
	public Judger getJudger() {
		return this.judger;
	}

	public void setJudger(Judger judger) {
		this.judger = judger;
	}

	@Column(name = "title", nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "number", nullable = false, length = 128)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	@Column(name = "source", nullable = false, length = 255)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "locked", nullable = false)
	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Column(name = "time_limit", nullable = false)
	public Integer getTimeLimit() {
		return this.timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	@Column(name = "memory_limit", nullable = false)
	public Integer getMemoryLimit() {
		return this.memoryLimit;
	}

	public void setMemoryLimit(Integer memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	@Column(name = "accepted", nullable = false)
	public Integer getAccepted() {
		return this.accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	@Column(name = "submitted", nullable = false)
	public Integer getSubmitted() {
		return this.submitted;
	}

	public void setSubmitted(Integer submitted) {
		this.submitted = submitted;
	}

	@Column(name = "judging_type", nullable = false)
	public Integer getJudgingType() {
		return this.judgingType;
	}

	public void setJudgingType(Integer judgingType) {
		this.judgingType = judgingType;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "problem")
	public Set<Solution> getSolutions() {
		return this.solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "problem_has_tag", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "problem_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "tag_id", nullable = false, updatable = false) })
	public Set<Tag> getTags() {
		return this.tags;
	}

	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "problem")
	public Set<ProblemDescription> getProblemDescriptions() {
		return this.problemDescriptions;
	}

	public void setProblemDescriptions(Set<ProblemDescription> problemDescriptions) {
		this.problemDescriptions = problemDescriptions;
	}

}
