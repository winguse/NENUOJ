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

/**
 * @author Winguse
 */
@Entity
@Table(name = "solution", catalog = "nenuoj")
@NamedQueries({
		@NamedQuery(name = "Solution.findAll", query = "SELECT s FROM Solution s"),
		@NamedQuery(name = "Solution.findById", query = "SELECT s FROM Solution s WHERE s.id = :id"),
		@NamedQuery(name = "Solution.findByCodeLength", query = "SELECT s FROM Solution s WHERE s.codeLength = :codeLength"),
		@NamedQuery(name = "Solution.findByLanguage", query = "SELECT s FROM Solution s WHERE s.language = :language"),
		@NamedQuery(name = "Solution.findByRunTime", query = "SELECT s FROM Solution s WHERE s.runTime = :runTime"),
		@NamedQuery(name = "Solution.findByRunMemory", query = "SELECT s FROM Solution s WHERE s.runMemory = :runMemory"),
		@NamedQuery(name = "Solution.findByStatus", query = "SELECT s FROM Solution s WHERE s.status = :status"),
		@NamedQuery(name = "Solution.findByIpaddr", query = "SELECT s FROM Solution s WHERE s.ipaddr = :ipaddr"),
		@NamedQuery(name = "Solution.findByShared", query = "SELECT s FROM Solution s WHERE s.shared = :shared"),
		@NamedQuery(name = "Solution.findBySubmitTime", query = "SELECT s FROM Solution s WHERE s.submitTime = :submitTime"),
		@NamedQuery(name = "Solution.findByJudgeTime", query = "SELECT s FROM Solution s WHERE s.judgeTime = :judgeTime"),
		@NamedQuery(name = "Solution.findByPassRate", query = "SELECT s FROM Solution s WHERE s.passRate = :passRate"),
		@NamedQuery(name = "Solution.findByLastUpdateTime", query = "SELECT s FROM Solution s WHERE s.lastUpdateTime = :lastUpdateTime") })
public class Solution implements java.io.Serializable {

	private static final long serialVersionUID = -106769681594205859L;
	private Integer id;
	private User user;
	private Message message;
	private Problem problem;
	private Contest contest;
	private String sourceCode;
	private Integer codeLength;
	private String language;
	private Integer runTime;
	private Integer runMemory;
	private Integer status;
	private String ipaddr;
	private boolean shared;
	private Date submitTime;
	private Date judgeTime;
	private float passRate;
	private Date lastUpdateTime;
	private Serializable remark;

	public Solution() {
	}

	public Solution(User user, Problem problem, String sourceCode, Integer codeLength, String language,
			Integer runTime, Integer runMemory, Integer status, String ipaddr, boolean shared, float passRate,
			Date lastUpdateTime) {
		this.user = user;
		this.problem = problem;
		this.sourceCode = sourceCode;
		this.codeLength = codeLength;
		this.language = language;
		this.runTime = runTime;
		this.runMemory = runMemory;
		this.status = status;
		this.ipaddr = ipaddr;
		this.shared = shared;
		this.passRate = passRate;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Solution(User user, Message message, Problem problem, Contest contest, String sourceCode,
			Integer codeLength, String language, Integer runTime, Integer runMemory, Integer status, String ipaddr,
			boolean shared, Date submitTime, Date judgeTime, float passRate, Date lastUpdateTime, Serializable remark) {
		this.user = user;
		this.message = message;
		this.problem = problem;
		this.contest = contest;
		this.sourceCode = sourceCode;
		this.codeLength = codeLength;
		this.language = language;
		this.runTime = runTime;
		this.runMemory = runMemory;
		this.status = status;
		this.ipaddr = ipaddr;
		this.shared = shared;
		this.submitTime = submitTime;
		this.judgeTime = judgeTime;
		this.passRate = passRate;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "message_id")
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "problem_id", nullable = false)
	public Problem getProblem() {
		return this.problem;
	}

	public void setProblem(Problem problem) {
		this.problem = problem;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "contest_id")
	public Contest getContest() {
		return this.contest;
	}

	public void setContest(Contest contest) {
		this.contest = contest;
	}

	@Column(name = "source_code", nullable = false, length = 65535)
	public String getSourceCode() {
		return this.sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	@Column(name = "code_length", nullable = false)
	public Integer getCodeLength() {
		return this.codeLength;
	}

	public void setCodeLength(Integer codeLength) {
		this.codeLength = codeLength;
	}

	@Column(name = "language", nullable = false, length = 45)
	public String getLanguage() {
		return this.language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "run_time", nullable = false)
	public Integer getRunTime() {
		return this.runTime;
	}

	public void setRunTime(Integer runTime) {
		this.runTime = runTime;
	}

	@Column(name = "run_memory", nullable = false)
	public Integer getRunMemory() {
		return this.runMemory;
	}

	public void setRunMemory(Integer runMemory) {
		this.runMemory = runMemory;
	}

	@Column(name = "status", nullable = false)
	public Integer getStatus() {
		return this.status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Column(name = "ipaddr", nullable = false, length = 128)
	public String getIpaddr() {
		return this.ipaddr;
	}

	public void setIpaddr(String ipaddr) {
		this.ipaddr = ipaddr;
	}

	@Column(name = "shared", nullable = false)
	public boolean isShared() {
		return this.shared;
	}

	public void setShared(boolean shared) {
		this.shared = shared;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "submit_time", length = 19)
	public Date getSubmitTime() {
		return this.submitTime;
	}

	public void setSubmitTime(Date submitTime) {
		this.submitTime = submitTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "judge_time", length = 19)
	public Date getJudgeTime() {
		return this.judgeTime;
	}

	public void setJudgeTime(Date judgeTime) {
		this.judgeTime = judgeTime;
	}

	@Column(name = "pass_rate", nullable = false, precision = 12, scale = 0)
	public float getPassRate() {
		return this.passRate;
	}

	public void setPassRate(float passRate) {
		this.passRate = passRate;
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

	public void setRemark(Serializable remark) {
		this.remark = remark;
	}

}
