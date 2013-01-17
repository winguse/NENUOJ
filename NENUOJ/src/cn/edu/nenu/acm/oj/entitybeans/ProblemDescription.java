package cn.edu.nenu.acm.oj.entitybeans;

// Generated 2013-1-17 9:19:30 by Hibernate Tools 4.0.0

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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * ProblemDescription generated by hbm2java
 */
@Entity
@Table(name = "problem_description", catalog = "nenuoj")
public class ProblemDescription implements java.io.Serializable {

	private Integer id;
	private User user;
	private Problem problem;
	private boolean locked;
	private int vote;
	private String title;
	private String description;
	private String input;
	private String output;
	private String sampleIn;
	private String sampleOut;
	private String hint;
	private String source;
	private Date lastUpdateTime;
	private Serializable remark;
	private Set<Contest> contests = new HashSet<Contest>(0);

	public ProblemDescription() {
	}

	public ProblemDescription(User user, Problem problem, boolean locked,
			int vote, String title, String description, String input,
			String output, String sampleIn, String sampleOut, String hint,
			String source, Date lastUpdateTime) {
		this.user = user;
		this.problem = problem;
		this.locked = locked;
		this.vote = vote;
		this.title = title;
		this.description = description;
		this.input = input;
		this.output = output;
		this.sampleIn = sampleIn;
		this.sampleOut = sampleOut;
		this.hint = hint;
		this.source = source;
		this.lastUpdateTime = lastUpdateTime;
	}

	public ProblemDescription(User user, Problem problem, boolean locked,
			int vote, String title, String description, String input,
			String output, String sampleIn, String sampleOut, String hint,
			String source, Date lastUpdateTime, Serializable remark,
			Set<Contest> contests) {
		this.user = user;
		this.problem = problem;
		this.locked = locked;
		this.vote = vote;
		this.title = title;
		this.description = description;
		this.input = input;
		this.output = output;
		this.sampleIn = sampleIn;
		this.sampleOut = sampleOut;
		this.hint = hint;
		this.source = source;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
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
	@JoinColumn(name = "user_id", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "problem_id", nullable = false)
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@Column(name = "locked", nullable = false)
	public boolean isLocked() {
		return this.locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	@Column(name = "vote", nullable = false)
	public int getVote() {
		return this.vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	@Column(name = "title", nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "description", nullable = false, length = 65535)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "input", nullable = false, length = 65535)
	public String getInput() {
		return this.input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	@Column(name = "output", nullable = false, length = 65535)
	public String getOutput() {
		return this.output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	@Column(name = "sample_in", nullable = false, length = 65535)
	public String getSampleIn() {
		return this.sampleIn;
	}

	public void setSampleIn(String sampleIn) {
		this.sampleIn = sampleIn;
	}

	@Column(name = "sample_out", nullable = false, length = 65535)
	public String getSampleOut() {
		return this.sampleOut;
	}

	public void setSampleOut(String sampleOut) {
		this.sampleOut = sampleOut;
	}

	@Column(name = "hint", nullable = false, length = 65535)
	public String getHint() {
		return this.hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

	@Column(name = "source", nullable = false, length = 65535)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contest_has_problem_description", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "problem_description_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "contest_id", nullable = false, updatable = false) })
	public Set<Contest> getContests() {
		return this.contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests = contests;
	}

}
