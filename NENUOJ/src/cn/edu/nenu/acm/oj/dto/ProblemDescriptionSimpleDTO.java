package cn.edu.nenu.acm.oj.dto;

public class ProblemDescriptionSimpleDTO {

	private int id;
	private int problemId;
	private boolean locked;
	private int vote;
	private String title;
	private String username;
	private Long lastupdate;
	private String versionMark;
	
	public ProblemDescriptionSimpleDTO() {
	}

	public ProblemDescriptionSimpleDTO(int id, int problemId, boolean locked, int vote, String title, String username,
			Long lastupdate, String versionMark) {
		super();
		this.id = id;
		this.problemId = problemId;
		this.locked = locked;
		this.vote = vote;
		this.title = title;
		this.username = username;
		this.lastupdate = lastupdate;
		this.versionMark = versionMark;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getProblemId() {
		return problemId;
	}

	public void setProblemId(int problemId) {
		this.problemId = problemId;
	}

	public boolean isLocked() {
		return locked;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public int getVote() {
		return vote;
	}

	public void setVote(int vote) {
		this.vote = vote;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getLastupdate() {
		return lastupdate;
	}

	public void setLastupdate(Long lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getVersionMark() {
		return versionMark;
	}

	public void setVersionMark(String versionMark) {
		this.versionMark = versionMark;
	}


}
