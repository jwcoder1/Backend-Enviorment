package org.esy.base.entity.pojo.multiData;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.namespace.QName;

import org.esy.base.util.XmlUtil;

@XmlRootElement(name = "Msg")
@XmlType(propOrder = { "head", "data" })
public class MultiMsg implements Serializable {

	private static final long serialVersionUID = 1L;

	private Head head;

	private Data data;

	public Head getHead() {
		return head;
	}

	@XmlElement(name = "Head")
	public void setHead(Head head) {
		this.head = head;
	}

	@XmlElement(name = "DATA", required = false)
	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}

	public MultiMsg() {

	}

}
