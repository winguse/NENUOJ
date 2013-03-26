package cn.edu.nenu.acm.oj.dto;

public class MessageDTO {
	private int id = 0;
	private String title = "";
	private String content = "";
	private int replyCount = 0;

	public MessageDTO() {

	}

	public MessageDTO(int id, String title, String content, int replyCount) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.replyCount = replyCount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReplyCount() {
		return replyCount;
	}

	public void setReplyCount(int replyCount) {
		this.replyCount = replyCount;
	}

}
