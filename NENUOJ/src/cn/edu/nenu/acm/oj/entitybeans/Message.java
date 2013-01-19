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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * @author Winguse
 */
@Entity
@Table(name = "message", catalog = "nenuoj")
@NamedQueries({
    @NamedQuery(name = "Message.findAll", query = "SELECT m FROM Message m"),
    @NamedQuery(name = "Message.findById", query = "SELECT m FROM Message m WHERE m.id = :id"),
    @NamedQuery(name = "Message.findByTitle", query = "SELECT m FROM Message m WHERE m.title = :title"),
    @NamedQuery(name = "Message.findByLastUpdateTime", query = "SELECT m FROM Message m WHERE m.lastUpdateTime = :lastUpdateTime")})
public class Message implements java.io.Serializable {

	private static final long serialVersionUID = -4439125551603411455L;
	private Integer id;
	private User user;
	private Message message;
	private String title;
	private String text;
	private Date lastUpdateTime;
	private Serializable remark;
	private Set<User> users = new HashSet<User>(0);
	private Set<Problem> problems = new HashSet<Problem>(0);
	private Set<Message> messages = new HashSet<Message>(0);
	private Set<Solution> solutions = new HashSet<Solution>(0);
	private Set<Contest> contests = new HashSet<Contest>(0);

	public Message() {
	}

	public Message(User user, String title, String text, Date lastUpdateTime) {
		this.user = user;
		this.title = title;
		this.text = text;
		this.lastUpdateTime = lastUpdateTime;
	}

	public Message(User user, Message message, String title, String text, Date lastUpdateTime, Serializable remark,
			Set<User> users, Set<Problem> problems, Set<Message> messages, Set<Solution> solutions,
			Set<Contest> contests) {
		this.user = user;
		this.message = message;
		this.title = title;
		this.text = text;
		this.lastUpdateTime = lastUpdateTime;
		this.remark = remark;
		this.users = users;
		this.problems = problems;
		this.messages = messages;
		this.solutions = solutions;
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
	@JoinColumn(name = "reply_id")
	public Message getMessage() {
		return this.message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	@Column(name = "title", nullable = false, length = 128)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "text", nullable = false, length = 65535)
	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Problem> getProblems() {
		return this.problems;
	}

	public void setProblems(Set<Problem> problems) {
		this.problems = problems;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Message> getMessages() {
		return this.messages;
	}

	public void setMessages(Set<Message> messages) {
		this.messages = messages;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Solution> getSolutions() {
		return this.solutions;
	}

	public void setSolutions(Set<Solution> solutions) {
		this.solutions = solutions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "message")
	public Set<Contest> getContests() {
		return this.contests;
	}

	public void setContests(Set<Contest> contests) {
		this.contests = contests;
	}

}
