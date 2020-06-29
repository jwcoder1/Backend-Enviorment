package org.esy.base.entity.pojo.multiData;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

public class Attr {

	private String desc;

	private String illus;

	private String value;

	public String getDesc() {
		return desc;
	}

	@XmlAttribute(name = "desc", required = false)
	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getIllus() {
		return illus;
	}

	@XmlAttribute(name = "illus", required = false)
	public void setIllus(String illus) {
		this.illus = illus;
	}

	public String getValue() {
		return value;
	}

	@XmlValue
	public void setValue(String value) {
		this.value = value;
	}

	public Attr() {
	}

	public Attr(String desc, String illus, String value) {
		super();
		this.desc = desc;
		this.illus = illus;
		this.value = value;
	}

}
