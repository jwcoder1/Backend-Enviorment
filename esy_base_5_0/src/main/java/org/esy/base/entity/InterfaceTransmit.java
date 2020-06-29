package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;

import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("接口订阅(转发)")
@Table(name = "base_minterfacetransmit", indexes = { @Index(name = "mis_iid", columnList = "mis_iid"), @Index(name = "mis_aid", columnList = "mis_aid") })
public class InterfaceTransmit extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("接口编号")
	@Column(name = "mis_iid", length = 50, nullable = false)
	private String iid;

	@FieldInfo("应用编号")
	@Column(name = "mis_aid", length = 50, nullable = false)
	private String aid;

	@FieldInfo("IP")
	@Column(name = "mis_ip", length = 100)
	private String ip;

	@FieldInfo("密钥")
	@Column(name = "mis_key", length = 255)
	private String key;

	@FieldInfo("可订阅来源")
	@Column(name = "mis_fromaids", length = 255)
	private String fromaids;

	@FieldInfo("状态")
	@Column(name = "mit_enable", length = 200)
	private Boolean enable = true;

	@FieldInfo("方法")
	@Column(name = "mis_method", length = 50, nullable = false)
	private String method;

	@FieldInfo("url")
	@Column(name = "mis_url", length = 200)
	private String url;

	@FieldInfo("转发的帐号 ")
	@Column(name = "mis_account", length = 100)
	private String account;

	@FieldInfo("转发的密码 ")
	@Column(name = "mis_password", length = 200)
	private String password;

	@FieldInfo("描述")
	@Column(name = "mis_memo", length = 255)
	private String memo;

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

	public String getFromaids() {
		return fromaids;
	}

	public void setFromaids(String fromaids) {
		this.fromaids = fromaids;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public InterfaceTransmit(String iid, String aid, String ip, String key, String fromaids, Boolean enable, String method, String url, String account, String password, String memo) {
		super();
		this.iid = iid;
		this.aid = aid;
		this.ip = ip;
		this.key = key;
		this.fromaids = fromaids;
		this.enable = enable;
		this.method = method;
		this.url = url;
		this.account = account;
		this.password = password;
		this.memo = memo;
	}

	public InterfaceTransmit() {
		super();
	}

	public InterfaceTransmit(String uid, String iid, String aid, String ip, String key, String fromaids, Boolean enable, String method, String url, String account, String password, String memo, String name) {
		super();
		this.setUid(uid);
		this.iid = iid;
		this.aid = aid;
		this.ip = ip;
		this.key = key;
		this.fromaids = fromaids;
		this.enable = enable;
		this.method = method;
		this.url = url;
		this.account = account;
		this.password = password;
		this.memo = memo;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
