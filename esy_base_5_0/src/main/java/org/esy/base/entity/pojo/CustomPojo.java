package org.esy.base.entity.pojo;

import java.io.Serializable;

/**
 * 
 * 通用的简单查询封装类
 *
 */
public class CustomPojo implements Serializable {

	private static final long serialVersionUID = 3194379975406286141L;

	private String str1;

	private String str2;

	private Integer str3;

	public String getStr1() {
		return str1;
	}

	public void setStr1(String str1) {
		this.str1 = str1;
	}

	public String getStr2() {
		return str2;
	}

	public void setStr2(String str2) {
		this.str2 = str2;
	}

	public CustomPojo() {
	}

	public CustomPojo(String str1, String str2, Integer str3) {
		super();
		this.str1 = str1;
		this.str2 = str2;
		this.str3 = str3;
	}

	public CustomPojo(String str1, String str2) {
		super();
		this.str1 = str1;
		this.str2 = str2;
	}

	public Integer getStr3() {
		return str3;
	}

	public void setStr3(Integer str3) {
		this.str3 = str3;
	}

}
