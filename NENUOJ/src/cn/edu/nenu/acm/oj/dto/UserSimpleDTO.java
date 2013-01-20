package cn.edu.nenu.acm.oj.dto;

public class UserSimpleDTO {
	private Integer id; 
	private String username;
	private String nickname;
	private Long permission;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Long getPermission() {
		return permission;
	}
	public void setPermission(Long permission) {
		this.permission = permission;
	}
	
}
