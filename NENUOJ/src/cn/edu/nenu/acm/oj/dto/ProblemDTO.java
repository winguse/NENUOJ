package cn.edu.nenu.acm.oj.dto;

import java.util.List;

public class ProblemDTO extends ProblemSimpleDTO {
	private Integer timeLimit;
	private Integer memoryLimit;
	private String longIntFormat;// from judger
	private Integer judgingType;
	private ProblemDescriptionDTO description;// main description.
	private List<ProblemDescriptionSimpleDTO> descriptionList;// list of the
																// other
																// description

	public ProblemDTO(Integer id, Boolean locked, String title, String judgerSource, String number, Integer accepted,
			Integer submitted, String source, Integer timeLimit, Integer memoryLimit, String longIntFormat,
			Integer judgingType, ProblemDescriptionDTO description, List<ProblemDescriptionSimpleDTO> descriptionList) {
		super(id, locked, title, judgerSource, number, accepted, submitted, source);
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.longIntFormat = longIntFormat;
		this.judgingType = judgingType;
		this.description = description;
		this.descriptionList = descriptionList;
	}

	public Integer getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(Integer timeLimit) {
		this.timeLimit = timeLimit;
	}

	public Integer getMemoryLimit() {
		return memoryLimit;
	}

	public void setMemoryLimit(Integer memoryLimit) {
		this.memoryLimit = memoryLimit;
	}

	public String getLongIntFormat() {
		return longIntFormat;
	}

	public void setLongIntFormat(String longIntFormat) {
		this.longIntFormat = longIntFormat;
	}

	public Integer getJudgingType() {
		return judgingType;
	}

	public void setJudgingType(Integer judgingType) {
		this.judgingType = judgingType;
	}

	public ProblemDescriptionDTO getDescription() {
		return description;
	}

	public void setDescription(ProblemDescriptionDTO description) {
		this.description = description;
	}

	public List<ProblemDescriptionSimpleDTO> getDescriptionList() {
		return descriptionList;
	}

	public void setDescriptionList(List<ProblemDescriptionSimpleDTO> descriptionList) {
		this.descriptionList = descriptionList;
	}

}
