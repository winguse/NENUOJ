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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import cn.edu.nenu.acm.oj.util.Remark;

/**
 * @author Winguse
 */
@Entity
@Table(name = "problem_description", catalog = "nenuoj")
@NamedQueries({
    @NamedQuery(name = "ProblemDescription.findAll", query = "SELECT p FROM ProblemDescription p"),
    @NamedQuery(name = "ProblemDescription.findById", query = "SELECT p FROM ProblemDescription p WHERE p.id = :id"),
    @NamedQuery(name = "ProblemDescription.findByLocked", query = "SELECT p FROM ProblemDescription p WHERE p.locked = :locked"),
    @NamedQuery(name = "ProblemDescription.findByVote", query = "SELECT p FROM ProblemDescription p WHERE p.vote = :vote"),
    @NamedQuery(name = "ProblemDescription.findByTitle", query = "SELECT p FROM ProblemDescription p WHERE p.title = :title"),
    @NamedQuery(name = "ProblemDescription.findSystemCrawl", query = "SELECT p FROM ProblemDescription p WHERE p.problem = :problem"),
    @NamedQuery(name = "ProblemDescription.findByLastUpdateTime", query = "SELECT p FROM ProblemDescription p WHERE p.lastUpdateTime = :lastUpdateTime")})
public class ProblemDescription implements java.io.Serializable {

	private static final long serialVersionUID = -8923107045353190138L;
	private Integer id;
	private User user;
	private Problem problem;
	private boolean locked;
	private Integer vote;
	private String title;
	private String description;
	private String input;
	private String output;
	private String sampleIn;
	private String sampleOut;
	private String hint;
	private Date lastUpdateTime;
	private Remark remark;
	private Set<Contest> contests = new HashSet<Contest>(0);

	public ProblemDescription() {
	}

	public ProblemDescription(User user, Problem problem, boolean locked, Integer vote, String title,
			String description, String input, String output, String sampleIn, String sampleOut, String hint, Date lastUpdateTime) {
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
		this.lastUpdateTime = lastUpdateTime;
	}

	public ProblemDescription(User user, Problem problem, boolean locked, Integer vote, String title,
			String description, String input, String output, String sampleIn, String sampleOut, String hint,
			 Date lastUpdateTime, Remark remark, Set<Contest> contests) {
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
	@JoinColumn(name = "user_id")//, nullable = false //if user is null, means it's a system crawl
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
	public Integer getVote() {
		return this.vote;
	}

	public void setVote(Integer vote) {
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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "contest_has_problem_description", catalog = "nenuoj", joinColumns = { @JoinColumn(name = "problem_description_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "contest_id", nullable = false, updatable = false) })
	public Set<Contest> getContests() {
		return this.contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests = contests;
	}

}
