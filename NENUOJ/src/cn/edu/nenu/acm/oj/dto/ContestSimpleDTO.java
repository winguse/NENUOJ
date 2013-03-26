package cn.edu.nenu.acm.oj.dto;

public class ContestSimpleDTO {

	private int id;
	private String title;
	private long startTime;
	private long endTime;
	private String hostUsername;
	private int contestType;
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
	public long getStartTime() {
		return startTime;
	}
	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
		this.endTime = endTime;
	}
	public String getHostUsername() {
		return hostUsername;
	}
	public void setHostUsername(String hostUsername) {
		this.hostUsername = hostUsername;
	}
	public int getContestType() {
		return contestType;
	}
	public void setContestType(int contestType) {
		this.contestType = contestType;
	}
	public ContestSimpleDTO(int id, String title, long startTime, long endTime,
			String hostUsername, int contestType) {
		super();
		this.id = id;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.hostUsername = hostUsername;
		this.contestType = contestType;
	}
	
}
