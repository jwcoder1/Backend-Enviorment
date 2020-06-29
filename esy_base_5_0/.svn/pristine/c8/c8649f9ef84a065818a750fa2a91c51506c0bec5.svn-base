package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.common.RulesType;
import org.esy.base.core.Base;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Type;

/**
 * 
 * 规则库
 *
 * @author cx
 * @date 2016年9月13日 下午4:37:38 
 * @version v1.0
 */
@Entity
@EntityInfo("规则库")
@Table(name = "base_rules", indexes = { @Index(name = "rules_no", columnList = "rules_no") })
@DynamicUpdate
public class Rules extends Base {

	private static final long serialVersionUID = -4140779102784567775L;

	@FieldInfo(value = "编号", remark = "需验证唯一，并新建后不可改")
	@Column(name = "rules_no", length = 20, nullable = false, unique = true)
	private String no;

	@FieldInfo("名称")
	@Column(name = "rules_name", length = 80, nullable = false)
	private String name;

	@FieldInfo(value = "类型", remark = "如果选定时器类型，那么需要填rulesTime字段(必须不为空)")
	@Column(name = "rules_type", nullable = false)
	private RulesType rulesType;

	@FieldInfo("定时器时间")
	@Column(name = "rules_time", length = 50)
	private String rulesTime;

	@FieldInfo(value = "状态", remark = "true-启用,false-停止,默认停止")
	@Column(name = "rules_state", nullable = false)
	private Boolean state = false;

	@FieldInfo(value = "分类", remark = "从数据字典中取")
	@Column(name = "rules_category", length = 32, nullable = false)
	private String category;

	@Lob
	@FieldInfo("脚本")
	@Type(type = "text")
	@Column(name = "rules_script")
	private String script;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public RulesType getRulesType() {
		return rulesType;
	}

	public void setRulesType(RulesType rulesType) {
		this.rulesType = rulesType;
	}

	public String getRulesTime() {
		return rulesTime;
	}

	public void setRulesTime(String rulesTime) {
		this.rulesTime = rulesTime;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getScript() {
		return script;
	}

	public void setScript(String script) {
		this.script = script;
	}

}
