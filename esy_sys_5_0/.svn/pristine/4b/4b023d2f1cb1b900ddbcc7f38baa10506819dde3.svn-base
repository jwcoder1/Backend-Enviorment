package org.esy.base.core;

import java.text.SimpleDateFormat;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;

public class HibernateAwareObjectMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6008186990665884362L;

	public HibernateAwareObjectMapper() {
		registerModule(new Hibernate4Module());
		//	this.getSerializationConfig().getDateFormat().getDateTimeInstance()
		//		DateFormat dd = this.getDateFormat();
		this.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

}
