package cn.edu.nenu.acm.oj.dto;

public class ProblemSimpleDTO {

	private Integer id;
	private Boolean locked;
	private String title;
	private String judgerSource;
	private String number;
	private Integer accepted;
	private Integer submitted;
	private String source;

	public ProblemSimpleDTO(Integer id,Boolean locked, String title, String judgerSource, String number, Integer accepted,
			Integer submitted, String source) {
		super();
		this.id = id;
		this.locked = locked;
		this.title = title;
		this.judgerSource = judgerSource;
		this.number = number;
		this.accepted = accepted;
		this.submitted = submitted;
		this.source = source;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getLocked() {
		return locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getJudgerSource() {
		return judgerSource;
	}

	public void setJudgerSource(String judgerSource) {
		this.judgerSource = judgerSource;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public Integer getAccepted() {
		return accepted;
	}

	public void setAccepted(Integer accepted) {
		this.accepted = accepted;
	}

	public Integer getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Integer submitted) {
		this.submitted = submitted;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

}
