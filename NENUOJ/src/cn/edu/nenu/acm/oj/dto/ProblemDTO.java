package cn.edu.nenu.acm.oj.dto;

public class ProblemDTO extends ProblemSimpleDTO {
	private Integer timeLimit;
	private Integer memoryLimit;
	private String longIntFormat;//from judger
	private Integer judgingType;
	private DescriptionDTO description;//may be here need to be the list of simpleDTO.
}
