package org.esy.base.entity.dto;

import java.io.Serializable;

public class PersonAccountDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String password;

	// 自动设置密码
	private Boolean randomPassword;

	// 下次登录要提醒更换密码
	private Boolean remind;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getRandomPassword() {
		return randomPassword;
	}

	public void setRandomPassword(Boolean randomPassword) {
		this.randomPassword = randomPassword;
	}

	public Boolean getRemind() {
		return remind;
	}

	public void setRemind(Boolean remind) {
		this.remind = remind;
	}

	public PersonAccountDto(String password, Boolean randomPassword, Boolean remind) {
		super();
		this.password = password;
		this.randomPassword = randomPassword;
		this.remind = remind;
	}

	public PersonAccountDto() {
	}

}
