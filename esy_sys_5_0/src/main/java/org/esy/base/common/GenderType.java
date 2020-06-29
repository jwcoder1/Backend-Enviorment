package org.esy.base.common;

/**
 * 
 * 性别
 *
 * @author cx
 * @date 2016年5月9日 上午10:33:11 
 * @version v1.0
 */
public enum GenderType {
	/**
	 * 男
	 */
	MEN("M"),
	/**
	 * 女
	 */
	FEMALE("F"),
	/**
	 * 其他
	 */
	OTHE("O");

	private final String gender;

	GenderType(String gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return this.gender;
	}

}
