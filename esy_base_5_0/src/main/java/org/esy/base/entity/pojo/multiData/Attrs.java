package org.esy.base.entity.pojo.multiData;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

public class Attrs {

	private List<Attr> attrs;

	public List<Attr> getAttrs() {
		return attrs;
	}

	@XmlElement(name = "Attr", required = false)
	public void setAttrs(List<Attr> attrs) {
		this.attrs = attrs;
	}

	public Attrs(List<Attr> attrs) {
		super();
		this.attrs = attrs;
	}

	public Attrs() {

	}

}
