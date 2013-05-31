package cn.edu.nenu.acm.oj.dto;

import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;

public class UserSimpleDTO implements IPermissionCode {
	private Integer id = -1;
	private String username = "";
	private String nickname = "";
	private Long permission = 0L;// default user permission (guest)

	public UserSimpleDTO(){
	}

	public UserSimpleDTO(Integer id, String username, String nickname,
			Long permission) {
		super();
		this.id = id;
		this.username = username;
		this.nickname = nickname;
		this.permission = permission;
	}

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
