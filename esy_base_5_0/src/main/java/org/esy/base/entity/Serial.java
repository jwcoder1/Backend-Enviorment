package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;

@Entity
@EntityInfo("序号控制")
@Table(name = "base_rserial", indexes = { @Index(name = "rserial_created", columnList = "created"),
		@Index(name = "rserial_updated", columnList = "updated"),
		@Index(name = "ms_moduleName", columnList = "ms_moduleName"),
		@Index(name = "ms_entityname", columnList = "ms_entityname"),
		@Index(name = "ms_serialkey", columnList = "ms_serialkey") })
public class Serial extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("模块名")
	@Column(name = "ms_moduleName", length = 64)
	private String moduleName;

	@FieldInfo("实体类")
	@Column(name = "ms_entityname", length = 64)
	private String entityName;

	@FieldInfo("业务号")
	@Column(name = "ms_serialkey", length = 64)
	private String serialKey;

	@FieldInfo("序列号")
	@Column(name = "ms_serialvalue")
	private long serialValue;

	/**
	 * 
	 */
	public Serial() {
		super();
	}

	/**
	 * @param moduleName
	 * @param entityName
	 * @param serialKey
	 * @param serialValue
	 */
	public Serial(String moduleName, String entityName, String serialKey, long serialValue) {
		super();
		this.moduleName = moduleName;
		this.entityName = entityName;
		this.serialKey = serialKey;
		this.serialValue = serialValue;
	}

	/**
	 * @return the moduleName
	 */
	public String getModuleName() {
		return moduleName;
	}

	/**
	 * @param moduleName the moduleName to set
	 */
	public void setTableName(String moduleName) {
		this.moduleName = moduleName;
	}

	/**
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String fieldName) {
		this.entityName = fieldName;
	}

	/**
	 * @return the serialKey
	 */
	public String getSerialKey() {
		return serialKey;
	}

	/**
	 * @param serialKey the serialKey to set
	 */
	public void setSerialKey(String serialKey) {
		this.serialKey = serialKey;
	}

	/**
	 * @return the serialValue
	 */
	public long getSerialValue() {
		return serialValue;
	}

	/**
	 * @param serialValue the serialValue to set
	 */
	public void setSerialValue(long serialValue) {
		this.serialValue = serialValue;
	}

}
