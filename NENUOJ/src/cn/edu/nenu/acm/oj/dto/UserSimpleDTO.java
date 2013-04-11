package cn.edu.nenu.acm.oj.dto;

import cn.edu.nenu.acm.oj.entitybeans.User;
import cn.edu.nenu.acm.oj.statuscode.IPermissionCode;
import cn.edu.nenu.acm.oj.util.Remark;

public class UserSimpleDTO implements IPermissionCode {
	private Integer id;
	private String username;
	private String nickname;
	private Long permission;

	public UserSimpleDTO(){
	}

	@Deprecated
	public UserSimpleDTO(User user){
		//TODO Reduce Coupling
		this.id=user.getId();
		this.username=user.getUsername();
		if(user.getRemark() instanceof Remark){
			this.nickname=(String)((Remark)(user.getRemark())).get("nickname");
		}else{
			this.nickname=this.username;
		}
		this.permission=user.getPermission();
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
