package cn.edu.nenu.acm.oj.dto;

public class ProblemDescriptionDTO extends ProblemDescriptionSimpleDTO {

	private String description;
	private String input;
	private String output;
	private String sampleIn;
	private String sampleOut;
	private String hint;

	public ProblemDescriptionDTO() {
	}

	public ProblemDescriptionDTO(int id, int problemId, boolean locked, int vote, String title, String username,
			Long lastupdate, String versionMark, String description, String input, String output, String sampleIn,
			String sampleOut, String hint) {
		super(id, problemId, locked, vote, title, username, lastupdate, versionMark);
		this.description = description;
		this.input = input;
		this.output = output;
		this.sampleIn = sampleIn;
		this.sampleOut = sampleOut;
		this.hint = hint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public String getSampleIn() {
		return sampleIn;
	}

	public void setSampleIn(String sampleIn) {
		this.sampleIn = sampleIn;
	}

	public String getSampleOut() {
		return sampleOut;
	}

	public void setSampleOut(String sampleOut) {
		this.sampleOut = sampleOut;
	}

	public String getHint() {
		return hint;
	}

	public void setHint(String hint) {
		this.hint = hint;
	}

}
