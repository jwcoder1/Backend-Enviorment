package org.esy.base.entity;

import java.util.List;
import java.util.Map;

public class ReportEntity {

	private static final long serialVersionUID = 1L;

	private Map<String, Object> param; // 静态变量

	private List<?> list;

	public ReportEntity(Map<String, Object> param, List<?> list) {
		super();
		this.param = param;
		this.list = list;
	}

	public ReportEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Map<String, Object> getParam() {
		return param;
	}

	public void setParam(Map<String, Object> param) {
		this.param = param;
	}

	public List<?> getList() {
		return list;
	}

	public void setList(List<?> list) {
		this.list = list;
	}

}