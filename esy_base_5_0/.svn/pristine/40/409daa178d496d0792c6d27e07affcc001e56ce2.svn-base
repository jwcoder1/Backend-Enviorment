package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.Base;
import org.esy.base.core.BaseProperties;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("记录信息")
@Table(name = "base_rlog", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "rl_mid", columnList = "rl_mid"),
		@Index(name = "rl_eid", columnList = "rl_eid"), @Index(name = "rl_time", columnList = "rl_time"),
		@Index(name = "rl_user", columnList = "rl_user") })
public class Log extends BaseProperties {

	private static final long serialVersionUID = 1L;

	@FieldInfo(value = "模块编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "rl_mid", length = 50, nullable = false)
	private String moduleId;

	@FieldInfo(value = "事件编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "rl_eid", length = 50, nullable = false)
	private String eventId;

	@FieldInfo(value = "操作用户")
	@FilterInfo(ListValue = "eq")
	@Column(name = "rl_user", length = 50, nullable = false)
	private String user;

	@FieldInfo(value = "操作时间")
	@FilterInfo(ListValue = "gte,lte", FieldsValue = "time,timeb")
	@Column(name = "rl_time", nullable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date time;

	@FieldInfo(value = "本地Ip")
	@FilterInfo(ListValue = "eq")
	@Column(name = "rl_localIp", nullable = false)
	private String localIp;

	@Column(name = "rl_info", length = 1000, nullable = false)
	@FilterInfo(ListValue = "match")
	private String info;

	@FieldInfo(value = "远程记录")
	@Column(name = "rl_remote", nullable = false)
	private boolean remote;

	@FieldInfo(value = "远程Ip")
	@FilterInfo(ListValue = "eq")
	@Column(name = "rl_remoteIp", nullable = false)
	private String remoteIp;

	@Transient
	@FieldInfo("操作时间")
	@JsonProperty("timeb")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date timeb;

	public Log() {
		super();
	}

	/**
	 * @param moduleId
	 * @param eventId
	 * @param user
	 * @param time
	 * @param localIp
	 * @param info
	 * @param remote
	 * @param remoteIp
	 */
	public Log(String moduleId, String eventId, String user, Date time, String localIp, String info, boolean remote,
			String remoteIp) {
		super();
		this.moduleId = moduleId;
		this.eventId = eventId;
		this.user = user;
		this.time = time;
		this.localIp = localIp;
		this.info = info;
		this.remote = remote;
		this.remoteIp = remoteIp;
	}

	/**
	 * @return the moduleId
	 */
	public final String getModuleId() {
		return moduleId;
	}

	/**
	 * @param moduleId
	 *            the moduleId to set
	 */
	public final void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	/**
	 * @return the eventId
	 */
	public final String getEventId() {
		return eventId;
	}

	/**
	 * @param eventId
	 *            the eventId to set
	 */
	public final void setEventId(String eventId) {
		this.eventId = eventId;
	}

	/**
	 * @return the user
	 */
	public final String getUser() {
		return user;
	}

	/**
	 * @param user
	 *            the user to set
	 */
	public final void setUser(String user) {
		this.user = user;
	}

	/**
	 * @return the time
	 */
	public final Date getTime() {
		return time;
	}

	/**
	 * @param time
	 *            the time to set
	 */
	public final void setTime(Date time) {
		this.time = time;
	}

	/**
	 * @return the localIp
	 */
	public final String getLocalIp() {
		return localIp;
	}

	/**
	 * @param localIp
	 *            the localIp to set
	 */
	public final void setLocalIp(String localIp) {
		this.localIp = localIp;
	}

	/**
	 * @return the info
	 */
	public final String getInfo() {
		return info;
	}

	/**
	 * @param info
	 *            the info to set
	 */
	public final void setInfo(String info) {
		this.info = info;
	}

	/**
	 * @return the remote
	 */
	public final boolean isRemote() {
		return remote;
	}

	/**
	 * @param remote
	 *            the remote to set
	 */
	public final void setRemote(boolean remote) {
		this.remote = remote;
	}

	/**
	 * @return the remoteIp
	 */
	public final String getRemoteIp() {
		return remoteIp;
	}

	/**
	 * @param remoteIp
	 *            the remoteIp to set
	 */
	public final void setRemoteIp(String remoteIp) {
		this.remoteIp = remoteIp;
	}

	public Date getTimeb() {
		return timeb;
	}

	public void setTimeb(Date timeb) {
		this.timeb = timeb;
	}

}
