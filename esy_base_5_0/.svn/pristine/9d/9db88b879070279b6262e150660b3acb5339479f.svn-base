package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;

@Entity
@EntityInfo("记录模块")
@Table(name = "base_mlogmodule", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "mlm_mid", columnList = "mlm_mid") })
public class LogModule extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo(value = "模块编号")
	@Column(name = "mlm_mid", length = 50, nullable = false)
	private String mId;

	@FieldInfo(value = "名称")
	@Column(name = "mlm_name", length = 100, nullable = false)
	private String name;

	@FieldInfo(value = "保存天数")
	@Column(name = "mlm_storageday", length = 50, nullable = false)
	private int storageDay;

	@FieldInfo(value = "已启动")
	@Column(name = "mlm_enable", nullable = false)
	private boolean enable;

	@Lob
	@Column(name = "mlm_memo", nullable = false)
	@FieldInfo(value = "描述")
	private String memo;

	public LogModule() {
		super();
	}

	/**
	 * @param mId
	 * @param name
	 * @param storageDay
	 * @param enable
	 * @param memo
	 */
	public LogModule(String mId, String name, int storageDay, boolean enable, String memo) {
		super();
		this.mId = mId;
		this.name = name;
		this.storageDay = storageDay;
		this.enable = enable;
		this.memo = memo;
	}

	/**
	 * @return the mId
	 */
	public String getmId() {
		return mId;
	}

	/**
	 * @param mId the mId to set
	 */
	public void setmId(String mId) {
		this.mId = mId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the storageDay
	 */
	public int getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to set
	 */
	public void setStorageDay(int storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the enable
	 */
	public boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}

	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}

}
