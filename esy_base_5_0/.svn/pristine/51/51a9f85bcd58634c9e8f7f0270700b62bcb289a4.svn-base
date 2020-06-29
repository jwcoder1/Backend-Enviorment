package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@EntityInfo("接口信息")
@Table(name = "base_minterfaceInfo", indexes = { @Index(name = "mit_iid", columnList = "mit_iid"), @Index(name = "mit_name", columnList = "mit_name") })
@DynamicUpdate
public class InterfaceInfo extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("接口编号")
	@Column(name = "mit_iid", length = 50, nullable = false)
	private String iid;

	@FieldInfo("接口名称")
	@Column(name = "mit_name", length = 200, nullable = false)
	private String name;

	@FieldInfo("url")
	@Column(name = "mit_url", length = 200)
	private String url;

	@FieldInfo("方法")
	@Column(name = "mit_method", length = 50, nullable = false)
	private String method = "";

	@FieldInfo("接口识别码")
	@Column(name = "mit_password", length = 500)
	private String password;

	@FieldInfo("公共接口")
	@Column(name = "mit_opening")
	private Boolean opening = false;

	@FieldInfo("类型(访问/订阅)")
	@Column(name = "mit_type", length = 5)
	private String type;

	@FieldInfo("接口描述")
	@Column(name = "mit_memo", length = 200)
	private String memo;

	@FieldInfo("状态")
	@Column(name = "mit_enable", length = 200)
	private Boolean enable = true;

	public String getIid() {
		return iid;
	}

	public void setIid(String iid) {
		this.iid = iid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Boolean getOpening() {
		return opening;
	}

	public void setOpening(Boolean opening) {
		this.opening = opening;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	public InterfaceInfo(String iid, String name, String url, String method, String password, Boolean opening, String type, String memo, Boolean enable) {
		super();
		this.iid = iid;
		this.name = name;
		this.url = url;
		this.method = method;
		this.password = password;
		this.opening = opening;
		this.type = type;
		this.memo = memo;
		this.enable = enable;
	}

	public InterfaceInfo() {
		super();
	}
}
