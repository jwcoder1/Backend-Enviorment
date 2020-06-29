package org.esy.base.entity.dto;

import java.io.Serializable;
import java.util.List;

import org.esy.base.entity.Identity;
import org.esy.base.entity.Person;

/**
 * uim.api.person
 * 
 * @author wwc
 *
 */
public class PersonIdentityDto implements Serializable {

	private static final long serialVersionUID = 8475478063416601666L;

	private Person person;

	private List<Identity> identitys;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public List<Identity> getIdentitys() {
		return identitys;
	}

	public void setIdentitys(List<Identity> identitys) {
		this.identitys = identitys;
	}

	public PersonIdentityDto(Person person, List<Identity> identitys) {
		super();
		this.person = person;
		this.identitys = identitys;
	}

	public PersonIdentityDto() {

	}

}
