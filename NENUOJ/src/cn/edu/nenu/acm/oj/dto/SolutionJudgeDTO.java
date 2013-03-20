package cn.edu.nenu.acm.oj.dto;

public class SolutionJudgeDTO {
	private String judgerSource;
	private String problem;
	private String sourceCode;
	private String language;

	public SolutionJudgeDTO(String judgerSource, String problem, String sourceCode, String language) {
		super();
		this.judgerSource = judgerSource;
		this.problem = problem;
		this.sourceCode = sourceCode;
		this.language = language;
	}

	public String getJudgerSource() {
		return judgerSource;
	}

	public void setJudgerSource(String judgerSource) {
		this.judgerSource = judgerSource;
	}

	public String getProblem() {
		return problem;
	}

	public void setProblem(String problem) {
		this.problem = problem;
	}

	public String getSourceCode() {
		return sourceCode;
	}

	public void setSourceCode(String sourceCode) {
		this.sourceCode = sourceCode;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
