package cn.edu.nenu.acm.oj.dto;

public class ProblemDTO extends ProblemSimpleDTO {
	private Integer timeLimit;
	private Integer memoryLimit;
	private String longIntFormat;//from judger
	private Integer judgingType;
	private DescriptionDTO description;//may be here need to be the list of simpleDTO.
	public ProblemDTO(Boolean locked, String title, String judgerSource, String number, Integer accepted,
			Integer submitted, String source, Integer timeLimit, Integer memoryLimit, String longIntFormat,
			Integer judgingType, DescriptionDTO description) {
		super(locked, title, judgerSource, number, accepted, submitted, source);
		this.timeLimit = timeLimit;
		this.memoryLimit = memoryLimit;
		this.longIntFormat = longIntFormat;
		this.judgingType = judgingType;
		this.description = description;
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
	public DescriptionDTO getDescription() {
		return description;
	}
	public void setDescription(DescriptionDTO description) {
		this.description = description;
	}


}
