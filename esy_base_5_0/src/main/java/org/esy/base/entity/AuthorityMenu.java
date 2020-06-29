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
 * Entity for 群组成员
 *
 */
@Entity
@EntityInfo("权限子表")
@Table(name = "base_mauthoritymenu", indexes = {
			@Index(name = "mgm_aid", columnList = "mgm_aid"),
			})
@DynamicUpdate
public class AuthorityMenu extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("关联 - 权主限表aid编号")
	@Column(name = "mgm_aid", length = 32, nullable = false)
	private String aid;
		
	@FieldInfo("关联 - 菜单表")
	@Column(name = "mgm_mid", length = 32, nullable = false)
	private String mid;
	
	/**
	 *
	 * 构造函数
	 *
	 */
	public AuthorityMenu() {
		super();
	}

	public String getAid() {
		return aid;
	}

	public void setAid(String aid) {
		this.aid = aid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}
	
}
