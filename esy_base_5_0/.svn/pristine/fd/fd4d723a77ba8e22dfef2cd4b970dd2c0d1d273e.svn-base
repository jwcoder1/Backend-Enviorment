package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;

@Entity
@EntityInfo("记录事件")
@Table(name = "base_mlogevent", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "mle_mid", columnList = "mle_mid"),
		@Index(name = "mle_eid", columnList = "mle_eid") })
public class LogEvent extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo(value = "模块编号")
	@Column(name = "mle_mid", length = 50, nullable = false)
	private String moduleId;

	@FieldInfo(value = "事件编号")
	@Column(name = "mle_eid", length = 50, nullable = false)
	private String eventId;

	@FieldInfo(value = "状态")
	@Column(name = "mle_enable", nullable = false)
	private boolean enable;

	@FieldInfo(value = "本地消息")
	@Column(name = "mle_local", nullable = false)
	private boolean local;

	@FieldInfo(value = "事件描述")
	@Column(name = "mle_description", length = 255, nullable = false)
	private String description;

	public LogEvent() {
		super();
	}

	/**
	 * @param moduleId
	 * @param eventId
	 * @param enable
	 * @param local
	 * @param description
	 */
	public LogEvent(String moduleId, String eventId, boolean enable, boolean local, String description) {
		super();
		this.moduleId = moduleId;
		this.eventId = eventId;
		this.enable = enable;
		this.local = local;
		this.description = description;
	}

	/**
	 * @return the moduleId
	 */
	public String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId the moduleId to set
	 */
	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the eventId
	 */
	public String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId the eventId to set
	 */
	public void setEventId(String eventId) {
		this.eventId = eventId;
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
	 * @return the local
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * @param local the local to set
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
