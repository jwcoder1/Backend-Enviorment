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
@EntityInfo("应用管理")
@Table(name = "base_mapplication", indexes = { @Index(name = "mapplication_aid", columnList = "ma_aid"), @Index(name = "mapplication_eid", columnList = "ma_eid") })
@DynamicUpdate
public class Application extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("企业路径")
	@Column(name = "ma_eid", length = 255)
	private String eid;

	@FieldInfo("应用编号")
	@Column(name = "ma_aid", length = 10, nullable = false)
	private String aid;

	@FieldInfo("应用IP地址")
	@Column(name = "ma_url", length = 39, nullable = false)
	private String url;

	@FieldInfo("映射IP")
	@Column(name = "ma_mappingIp", length = 39)
	private String mappingIp;

	@FieldInfo("访问端口号")
	@Column(name = "ma_port", length = 5, nullable = false)
	private String port;

	@FieldInfo("门户域名")
	@Column(name = "ma_domain", length = 64, nullable = false)
	private String domain;

	@FieldInfo("单点根")
	@Column(name = "ma_singletree", length = 255, nullable = false)
	private String singletree;

	@FieldInfo("应用名称")
	@Column(name = "ma_name", length = 32, nullable = false)
	private String name;

	@FieldInfo("应用开发商")
	@Column(name = "ma_developer", length = 32)
	private String developer;

	@FieldInfo("联系人")
	@Column(name = "ma_linkman", length = 32)
	private String linkman;

	@FieldInfo("联系电话")
	@Column(name = "ma_phone", length = 32)
	private String phone;

	@FieldInfo("负责人")
	@Column(name = "ma_manager", length = 32)
	private String manager;

	@FieldInfo("应用登入接口url")
	@Column(name = "ma_loginUrl", length = 255)
	private String loginUrl;

	@FieldInfo("已启用")
	@Column(name = "ma_enable", nullable = false)
	private boolean enable = false;

	@FieldInfo("应用部署地址")
	@Column(name = "ma_deployUrl", length = 255)
	private String deployUrl;

	@FieldInfo("备注")
	@Column(name = "ma_remark", length = 255)
	private String remark;

	@FieldInfo("排序号")
	@Column(name = "ma_seq")
	private Integer seq;

	@JsonProperty
	@Transient
	private String[] groups;// 该应用在的应用分组的编号数组

	public Application() {
	}

	public String[] getGroups() {
		return groups;
	}

	public void setGroups(String[] groups) {
		this.groups = groups;
	}

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMappingIp() {
		return mappingIp;
	}

	public void setMappingIp(String mappingIp) {
		this.mappingIp = mappingIp;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSingletree() {
		return singletree;
	}

	public void setSingletree(String singletree) {
		this.singletree = singletree;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public String getLinkman() {
		return linkman;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getManager() {
		return manager;
	}

	public void setManager(String manager) {
		this.manager = manager;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

	public String getDeployUrl() {
		return deployUrl;
	}

	public void setDeployUrl(String deployUrl) {
		this.deployUrl = deployUrl;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public boolean isEnable() {
		return enable;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

}
