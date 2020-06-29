package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("接口访问授权")
@Table(name = "base_minterfaceauthorized", indexes = { @Index(name = "mia_iid", columnList = "mia_iid"), @Index(name = "mia_aid", columnList = "mia_aid") })
@DynamicUpdate
public class InterfaceAuthorized extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("接口编号")
	@Column(name = "mia_iid", length = 50, nullable = false)
	private String iid;

	@FieldInfo("应用编号")
	@Column(name = "mia_aid", length = 50, nullable = false)
	private String aid;

	@FieldInfo("接口识别码 ")
	@Column(name = "mia_password", length = 255)
	private String password;

	@FieldInfo("mia_IP")
	@Column(name = "mia_ip", length = 100)
	private String ip;

	@FieldInfo("密钥")
	@Column(name = "mia_key", length = 255)
	private String key;

	@FieldInfo("状态")
	@Column(name = "mit_enable")
	private Boolean enable = false;

	@Transient
	@JsonProperty("url")
	private String url;

	@Transient
	@JsonProperty("name")
	private String name;

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public InterfaceAuthorized() {
		super();
	}

	public InterfaceAuthorized(String iid, String aid, String password, String ip, String key, Boolean enable) {
		this.iid = iid;
		this.aid = aid;
		this.password = password;
		this.ip = ip;
		this.key = key;
		this.enable = enable;
	}

	public InterfaceAuthorized(String uid, String iid, String aid, String password, String ip, String key, Boolean enable, String url, String name) {
		super();
		this.setUid(uid);
		this.iid = iid;
		this.aid = aid;
		this.password = password;
		this.ip = ip;
		this.key = key;
		this.enable = enable;
		this.url = url;
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
