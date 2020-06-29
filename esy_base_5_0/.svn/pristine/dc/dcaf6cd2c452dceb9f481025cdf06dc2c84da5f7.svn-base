package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;

/**
 * 
 * 脚本变量
 *
 * @author cx
 * @date 2016年9月13日 下午5:15:48 
 * @version v1.0
 */
@Entity
@EntityInfo("脚本变量")
@Table(name = "base_rulesvariables", indexes = { @Index(name = "rules_uid", columnList = "rules_uid") })
@DynamicUpdate
public class RulesVariables extends Base {

	private static final long serialVersionUID = 1206856931010954509L;

	@FieldInfo(value = "规则Uid", remark = "与规则库关联")
	@Column(name = "rules_uid", length = 32, nullable = false)
	private String rulesUid;

	@FieldInfo("规则变量名")
	@Column(name = "rv_name", length = 80, nullable = false)
	private String name;

	@FieldInfo("规则变量值")
	@Column(name = "rv_value", length = 100, nullable = false)
	private String value = "";

	public String getRulesUid() {
		return rulesUid;
	}

	public void setRulesUid(String rulesUid) {
		this.rulesUid = rulesUid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
