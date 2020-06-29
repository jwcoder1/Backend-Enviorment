package org.esy.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.annotation.FilterInfo;
import org.esy.base.core.BaseProperties;

/**
 *  实体类
 * 
 * @author <a href="mailto:ardui@163.com"ardui</a
 *  @date Thu Nov 30 22:38:54 CST 2017
 */
@Entity
@Table(name = "sysvar", indexes = { @Index(name = "var_type", columnList = "var_type"),
		@Index(name = "var_typedesc", columnList = "var_typedesc"), @Index(name = "var_name", columnList = "var_name"),
		@Index(name = "var_desc", columnList = "var_desc"), @Index(name = "created", columnList = "created"),
		@Index(name = "updated", columnList = "updated") })
@EntityInfo("系统变数档")
public class Sysvar extends BaseProperties {

	private static final long serialVersionUID = 1L;

	@FieldInfo("分组编号")
	@FilterInfo(ListValue = "eq")
	@Column(name = "var_type", length = 32)
	private String var_type;

	@FieldInfo("分组说明")
	@Column(name = "var_typedesc", length = 32)
	private String var_typedesc;

	@FieldInfo("变数名")
	@FilterInfo(ListValue = "eq")
	@Column(name = "var_name", length = 32)
	private String var_name;

	@FieldInfo("变数说明")
	@Column(name = "var_desc", length = 32)
	private String var_desc;

	@FieldInfo("变数长度")
	@Column(name = "var_len")
	private Double var_len;

	@FieldInfo("变数值")
	@Column(name = "var_val", length = 128)
	private String var_val;

	@FieldInfo("sreadm类别")
	@FilterInfo(ListValue = "")
	@Column(name = "schemaType", length = 32)
	private String schemaType;

	@FieldInfo("组件代号")
	@FilterInfo(ListValue = "")
	@Column(name = "discretenessno", length = 32)
	private String discretenessno;

	@FieldInfo("lov类别")
	@FilterInfo(ListValue = "")
	@Column(name = "lovType", length = 32)
	private String lovType;

	/**
	*
	* 构造函数
	*
	*/
	public Sysvar() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 	 * @param var_type
	 *		  分组编号
	 * 
	 	 * @param var_typedesc
	 *		  分组说明
	 * 
	 	 * @param var_name
	 *		  变数名
	 * 
	 	 * @param var_desc
	 *		  变数说明
	 * 
	 	 * @param var_len
	 *		  变数长度
	 * 
	 	 * @param var_val
	 *		  变数值
	 * 
	 	 * @param schemaType
	 *		  sreadm类别
	 * 
	 	 * @param discretenessno
	 *		  组件代号
	 * 
	 	 * @param lovType
	 *		  lov类别
	 * 
	 	 */
	public Sysvar(String var_type, String var_typedesc, String var_name, String var_desc, Double var_len,
			String var_val, String schemaType, String discretenessno, String lovType) {
		super();
		this.var_type = var_type;
		this.var_typedesc = var_typedesc;
		this.var_name = var_name;
		this.var_desc = var_desc;
		this.var_len = var_len;
		this.var_val = var_val;
		this.schemaType = schemaType;
		this.discretenessno = discretenessno;
		this.lovType = lovType;
	}

	public Sysvar(String var_type, String var_name) {
		this.var_type = var_type;
		this.var_name = var_name;

	}

	/**
	* @return var_type
	*			分组编号
	*/
	public String getVar_type() {
		return var_type;
	}

	/**
	 * @param var_type
	 *			分组编号
	 */
	public void setVar_type(String Var_type) {
		this.var_type = Var_type;
	}

	/**
	* @return var_typedesc
	*			分组说明
	*/
	public String getVar_typedesc() {
		return var_typedesc;
	}

	/**
	 * @param var_typedesc
	 *			分组说明
	 */
	public void setVar_typedesc(String Var_typedesc) {
		this.var_typedesc = Var_typedesc;
	}

	/**
	* @return var_name
	*			变数名
	*/
	public String getVar_name() {
		return var_name;
	}

	/**
	 * @param var_name
	 *			变数名
	 */
	public void setVar_name(String Var_name) {
		this.var_name = Var_name;
	}

	/**
	* @return var_desc
	*			变数说明
	*/
	public String getVar_desc() {
		return var_desc;
	}

	/**
	 * @param var_desc
	 *			变数说明
	 */
	public void setVar_desc(String Var_desc) {
		this.var_desc = Var_desc;
	}

	/**
	* @return var_len
	*			变数长度
	*/
	public Double getVar_len() {
		return var_len;
	}

	/**
	 * @param var_len
	 *			变数长度
	 */
	public void setVar_len(Double Var_len) {
		this.var_len = Var_len;
	}

	/**
	* @return var_val
	*			变数值
	*/
	public String getVar_val() {
		return var_val;
	}

	/**
	 * @param var_val
	 *			变数值
	 */
	public void setVar_val(String Var_val) {
		this.var_val = Var_val;
	}

	/**
	* @return schemaType
	*			sreadm类别
	*/
	public String getSchemaType() {
		return schemaType;
	}

	/**
	 * @param schemaType
	 *			sreadm类别
	 */
	public void setSchemaType(String SchemaType) {
		this.schemaType = SchemaType;
	}

	/**
	* @return discretenessno
	*			组件代号
	*/
	public String getDiscretenessno() {
		return discretenessno;
	}

	/**
	 * @param discretenessno
	 *			组件代号
	 */
	public void setDiscretenessno(String Discretenessno) {
		this.discretenessno = Discretenessno;
	}

	/**
	* @return lovType
	*			lov类别
	*/
	public String getLovType() {
		return lovType;
	}

	/**
	 * @param lovType
	 *			lov类别
	 */
	public void setLovType(String LovType) {
		this.lovType = LovType;
	}

}
