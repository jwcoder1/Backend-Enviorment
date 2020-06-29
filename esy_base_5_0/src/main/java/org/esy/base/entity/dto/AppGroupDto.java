package org.esy.base.entity.dto;

import java.io.Serializable;
import java.util.List;

import org.esy.base.entity.Application;

public class AppGroupDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String agid;
	
	private String name;
	
	private Integer seq;
	
	private List<AppGroupDto> childrens;
	
	private List<Application> applications;

	public String getAgid() {
		return agid;
	}

	public void setAgid(String agid) {
		this.agid = agid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getSeq() {
		return seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
	}

	public List<AppGroupDto> getChildrens() {
		return childrens;
	}

	public void setChildrens(List<AppGroupDto> childrens) {
		this.childrens = childrens;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	

}
