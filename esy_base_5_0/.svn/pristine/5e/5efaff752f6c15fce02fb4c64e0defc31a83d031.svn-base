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
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("接口消息记录")
@Table(name = "base_minterfacelog", indexes = { @Index(name = "mil_iid", columnList = "mil_iid"), @Index(name = "mil_aid", columnList = "mil_aid") })
@DynamicUpdate
public class InterfaceLog extends Base {

	private static final long serialVersionUID = 5347198004013300494L;

	@FieldInfo("发生时间")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	@Column(name = "mil_date")
	private Date logDate;

	@FieldInfo("访问接口ip")
	@Column(name = "mil_ip", length = 20)
	private String ip;

	@FieldInfo("类型")
	@Column(name = "mil_type", length = 5)
	private String type;

	@FieldInfo("接口编号")
	@Column(name = "mil_iid", length = 50)
	private String iid;

	@FieldInfo("应用id")
	@Column(name = "mil_aid", length = 50)
	private String aid;

	// 0-失败 1-成功 3-发送中
	@FieldInfo("状态")
	@Column(name = "mil_status", length = 2)
	private String status;

	@FieldInfo("转发url")
	@Column(name = "mil_url", length = 200)
	private String url;

	@FieldInfo("发送内容")
	@Lob
	@Column(name = "mil_send")
	private String send;

	@FieldInfo("接收内容")
	@Lob
	@Column(name = "mil_recv")
	private String recv;

	// 应用名称
	@Transient
	@JsonProperty("aname")
	private String aname;

	// 接口名称
	@Transient
	@JsonProperty("iname")
	private String iname;

	public Date getLogDate() {
		return logDate;
	}

	public void setLogDate(Date logDate) {
		this.logDate = logDate;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSend() {
		return send;
	}

	public void setSend(String send) {
		this.send = send;
	}

	public String getRecv() {
		return recv;
	}

	public void setRecv(String recv) {
		this.recv = recv;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public InterfaceLog(Date logDate, String ip, String type, String iid, String aid, String url, String send, String recv) {
		super();
		this.logDate = logDate;
		this.ip = ip;
		this.type = type;
		this.iid = iid;
		this.aid = aid;
		this.url = url;
		this.send = send;
		this.recv = recv;
	}

	public InterfaceLog(String iid, String aid, String url, String send, String recv) {
		this.iid = iid;
		this.aid = aid;
		this.url = url;
		this.send = send;
		this.recv = recv;
	}

	public InterfaceLog() {
		super();
	}

	public String getAname() {
		return aname;
	}

	public void setAname(String aname) {
		this.aname = aname;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public InterfaceLog(String uid, Date logDate, String ip, String type, String iid, String aid, String url, String send, String recv, String aname, String iname) {
		super();
		this.setUid(uid);
		this.logDate = logDate;
		this.ip = ip;
		this.type = type;
		this.iid = iid;
		this.aid = aid;
		this.url = url;
		this.send = send;
		this.recv = recv;
		this.aname = aname;
		this.iname = iname;
	}

	public InterfaceLog(Date logDate, String ip, String type, String iid, String aid, String status, String url, String send, String recv, String aname, String iname) {
		super();
		this.logDate = logDate;
		this.ip = ip;
		this.type = type;
		this.iid = iid;
		this.aid = aid;
		this.status = status;
		this.url = url;
		this.send = send;
		this.recv = recv;
		this.aname = aname;
		this.iname = iname;
	}

}
