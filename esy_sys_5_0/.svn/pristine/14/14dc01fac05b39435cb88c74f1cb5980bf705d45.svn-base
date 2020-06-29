package org.esy.base.entity.pojo;

import java.io.Serializable;
import java.util.List;

import org.esy.base.entity.Person;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.dto.PersonAccountDto;

/**
 * Identity 列表，用于前台上传
 */
public class IdentityPojo implements Serializable {

	private static final long serialVersionUID = -8080399807215808910L;

	private Person person;

	private List<IdentityDto> identitys;

	private PersonAccountDto personAccountDto;

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public IdentityPojo() {
	}

	public PersonAccountDto getPersonAccountDto() {
		return personAccountDto;
	}

	public void setPersonAccountDto(PersonAccountDto personAccountDto) {
		this.personAccountDto = personAccountDto;
	}

	public List<IdentityDto> getIdentitys() {
		return identitys;
	}

	public void setIdentitys(List<IdentityDto> identitys) {
		this.identitys = identitys;
	}

	public IdentityPojo(Person person, List<IdentityDto> identitys, PersonAccountDto personAccountDto) {
		super();
		this.person = person;
		this.identitys = identitys;
		this.personAccountDto = personAccountDto;
	}
}
