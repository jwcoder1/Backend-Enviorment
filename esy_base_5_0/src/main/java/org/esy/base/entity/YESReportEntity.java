package org.esy.base.entity;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class YESReportEntity {

	private static final long serialVersionUID = 1L;

	private String reportname;//报表名

	private String orgPack;//包名

	private String method;//方法名

	private Map<String, Object> reportParam;//报表参数

	private Map<String, Object> dataParam; //数据参数

	/**
	 * @return the orgPack
	 */
	public String getOrgPack() {
		return orgPack;
	}

	/**
	 * @param orgPack the orgPack to set
	 */
	public void setOrgPack(String orgPack) {
		this.orgPack = orgPack;
	}

	/**
	 * @return the reportParam
	 */
	public Map<String, Object> getReportParam() {
		return reportParam;
	}

	/**
	 * @param reportParam the reportParam to set
	 */
	public void setReportParam(Map<String, Object> reportParam) {
		this.reportParam = reportParam;
	}

	/**
	 * @return the dataParam
	 */
	public Map<String, Object> getDataParam() {
		return dataParam;
	}

	/**
	 * @param dataParam the dataParam to set
	 */
	public void setDataParam(Map<String, Object> dataParam) {
		this.dataParam = dataParam;
	}

	/**
	 * @return the reportname
	 */
	public String getReportname() {
		return reportname;
	}

	/**
	 * @param reportname the reportname to set
	 */
	public void setReportname(String reportname) {
		this.reportname = reportname;
	}

	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}

	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}

}
