package org.esy.base.entity.dto;

public class IdentOrg {

	private String eid;

	// 企业名称
	private String enterpriseName;

	// 组织类型
	private String rootpid;

	private String rootName;

	// 组织
	private String oid;

	private String oName;

	private String path;

	public String getEid() {
		return eid;
	}

	public void setEid(String eid) {
		this.eid = eid;
	}

	public String getEnterpriseName() {
		return enterpriseName;
	}

	public void setEnterpriseName(String enterpriseName) {
		this.enterpriseName = enterpriseName;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public String getoName() {
		return oName;
	}

	public void setoName(String oName) {
		this.oName = oName;
	}

	public String getRootpid() {
		return rootpid;
	}

	public void setRootpid(String rootpid) {
		this.rootpid = rootpid;
	}

	public String getRootName() {
		return rootName;
	}

	public void setRootName(String rootName) {
		this.rootName = rootName;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public IdentOrg() {
	}

	public IdentOrg(String eid, String enterpriseName, String rootpid, String rootName, String oid, String oName, String path) {
		super();
		this.eid = eid;
		this.enterpriseName = enterpriseName;
		this.rootpid = rootpid;
		this.rootName = rootName;
		this.oid = oid;
		this.oName = oName;
		this.path = path;
	}

}
