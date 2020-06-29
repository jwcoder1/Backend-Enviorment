package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@EntityInfo("消息队列配置")
@Table(name = "base_mqueuecfg", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "qc_qid", columnList = "qc_qid"),
		@Index(name = "qc_mid", columnList = "qc_mid"),
		@Index(name = "qc_lastscheduler", columnList = "qc_lastscheduler") })
public class MQueueCfg extends Base {

	private static final long serialVersionUID = -6195719467773298202L;

	@FieldInfo(value = "模块编号")
	@Column(name = "qc_mid", length = 50, nullable = false)
	private String moduleId;

	@FieldInfo("队列编号")
	@Column(name = "qc_qid", length = 50, nullable = false)
	private String qid;

	@FieldInfo("名称")
	@Column(name = "qc_name", length = 100, nullable = false)
	private String name;

	@FieldInfo("分类")
	@Column(name = "qc_category", length = 100, nullable = false)
	private String category;

	@FieldInfo("类型")
	@Column(name = "qc_type", nullable = false)
	private int type = 0;

	@FieldInfo(value = "保存天数")
	@Column(name = "qc_storageday", nullable = false)
	private int storageDay;

	@FieldInfo(value = "配置参数")
	@Lob
	@Column(name = "qc_config")
	private String config;

	@FieldInfo(value = "已启动")
	@Column(name = "qc_enable", nullable = false)
	private boolean enable = false;

	@FieldInfo("IP地址")
	@Column(name = "qc_ip", length = 255)
	private String ipAddress;

	@FieldInfo("存储库")
	@Column(name = "qc_storage", length = 255)
	private String storage;

	@FieldInfo("最后调度时间")
	@Column(name = "qc_lastscheduler")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastScheduler = null;

	@FieldInfo("最后调度状态")
	@Column(name = "qc_laststate")
	private int lastState;

	@FieldInfo("最后调度信息")
	@Column(name = "qc_lastmessage", length = 255)
	private String lastMessage;

	/**
	 * @return 0:存储 1:分发 2:推送
	 */
	public String typeName() {
		switch (type) {
		case 0:
			return "存储";
		case 1:
			return "分发";
		case 2:
			return "推送";
		default:
			return "存储";
		}
	}

	/**
	 * 
	 */
	public MQueueCfg() {
		super();
	}

	/**
	 * @param moduleId
	 * @param qid
	 * @param name
	 * @param category
	 * @param type
	 * @param storageDay
	 * @param config
	 * @param enable
	 * @param ipAddress
	 * @param storage
	 * @param lastScheduler
	 * @param lastState
	 * @param lastMessage
	 */
	public MQueueCfg(String moduleId, String qid, String name, String category, int type, int storageDay,
			String config, boolean enable, String ipAddress, String storage, Date lastScheduler, int lastState,
			String lastMessage) {
		super();
		this.moduleId = moduleId;
		this.qid = qid;
		this.name = name;
		this.category = category;
		this.type = type;
		this.storageDay = storageDay;
		this.config = config;
		this.enable = enable;
		this.ipAddress = ipAddress;
		this.storage = storage;
		this.lastScheduler = lastScheduler;
		this.lastState = lastState;
		this.lastMessage = lastMessage;
	}

	/**
	 * @return the moduleId
	 */
	public final String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public final void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the qid
	 */
	public final String getQid() {
		return qid;
	}

	/**
	 * @param qid the qid to set
	 */
	public final void setQid(String qid) {
		this.qid = qid;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the category
	 */
	public final String getCategory() {
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public final void setCategory(String category) {
		this.category = category;
	}

	/**
	 * @return the type
	 */
	public final int getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public final void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the storageDay
	 */
	public final int getStorageDay() {
		return storageDay;
	}

	/**
	 * @param storageDay the storageDay to set
	 */
	public final void setStorageDay(int storageDay) {
		this.storageDay = storageDay;
	}

	/**
	 * @return the config
	 */
	public final String getConfig() {
		return config;
	}

	/**
	 * @param config the config to set
	 */
	public final void setConfig(String config) {
		this.config = config;
	}

	/**
	 * @return the enable
	 */
	public final boolean isEnable() {
		return enable;
	}

	/**
	 * @param enable the enable to set
	 */
	public final void setEnable(boolean enable) {
		this.enable = enable;
	}

	/**
	 * @return the ipAddress
	 */
	public final String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public final void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the storage
	 */
	public final String getStorage() {
		return storage;
	}

	/**
	 * @param storage the storage to set
	 */
	public final void setStorage(String storage) {
		this.storage = storage;
	}

	/**
	 * @return the lastScheduler
	 */
	public final Date getLastScheduler() {
		return lastScheduler;
	}

	/**
	 * @param lastScheduler the lastScheduler to set
	 */
	public final void setLastScheduler(Date lastScheduler) {
		this.lastScheduler = lastScheduler;
	}

	/**
	 * @return the lastState
	 */
	public final int getLastState() {
		return lastState;
	}

	/**
	 * @param lastState the lastState to set
	 */
	public final void setLastState(int lastState) {
		this.lastState = lastState;
	}

	/**
	 * @return the lastMessage
	 */
	public final String getLastMessage() {
		return lastMessage;
	}

	/**
	 * @param lastMessage the lastMessage to set
	 */
	public final void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

}
