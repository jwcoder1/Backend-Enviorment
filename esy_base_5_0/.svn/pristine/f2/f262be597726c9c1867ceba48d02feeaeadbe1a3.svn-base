package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.esy.base.util.YESUtil;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 
 * Entity for 自动代码生成范例
 *
 */
@Entity
@EntityInfo("自动代码生成范例")
@Table(name = "base_codeGenerationSample", indexes = { @Index(name = "tc_string", columnList = "tc_string"),
		@Index(name = "tc_picture", columnList = "tc_picture"),
		@Index(name = "codeGenerationSample_created", columnList = "created"),
		@Index(name = "codeGenerationSample_updated", columnList = "updated") })
@DynamicUpdate
public class CodeGenerationSample extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("字符型变量")
	@Column(name = "tc_string", length = 32, nullable = false)
	private String stringValue = "Default String";

	@FieldInfo("单附件变量")
	@Column(name = "tc_attachment", length = 32)
	private String attachmentValue = "";

	@FieldInfo("多附件变量")
	@Column(name = "tc_attachments", length = 32)
	private String attachmentsValue = "";

	@FieldInfo("图片变量")
	@Column(name = "tc_picture", length = 32, nullable = false)
	private String pictureValue = "";

	@Lob
	@FieldInfo("备注型变量")
	@Column(name = "tc_text", nullable = false)
	private String textValue = "Default Text";

	@FieldInfo("整型变量")
	@Column(name = "tc_integer", nullable = false)
	private Integer integerValue = 20;

	@FieldInfo("长整型变量")
	@Column(name = "tc_long")
	private Long longValue = 100l;

	@FieldInfo("双精度型变量")
	@Column(name = "tc_Float")
	private Float FloatValue = 3.14f;

	@FieldInfo("逻辑型变量")
	@Column(name = "tc_boolean", nullable = false)
	private Boolean booleanValue = true;

	@FieldInfo("日期时间型变量")
	@Column(name = "tc_datetime")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date datetimeValue = YESUtil.getDateTimeByString("2015-10-08 09:00:00");

	@FieldInfo("时间型变量")
	@Column(name = "tc_time")
	@DateTimeFormat(pattern = "HH-mm-ss")
	@JsonFormat(pattern = "HH:mm:ss", timezone = "GMT+8")
	private Date timeValue = YESUtil.getTimeByString("09:00:00");

	@FieldInfo("日期型变量")
	@Column(name = "tc_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date dateValue = YESUtil.getDateByString("2015-10-08");

	/**
	 *
	 * 构造函数
	 *
	 */
	public CodeGenerationSample() {
		super();
	}

	/**
	 *
	 * 构造函数
	 *
	 * @param stringValue
	 *			字符型变量
	 * 
	 * @param attachmentValue
	 *			单附件变量
	 * 
	 * @param attachmentsValue
	 *			多附件变量
	 * 
	 * @param pictureValue
	 *			图片变量
	 * 
	 * @param textValue
	 *			备注型变量
	 * 
	 * @param integerValue
	 *			整型变量
	 * 
	 * @param longValue
	 *			长整型变量
	 * 
	 * @param FloatValue
	 *			双精度型变量
	 * 
	 * @param booleanValue
	 *			逻辑型变量
	 * 
	 * @param datetimeValue
	 *			日期时间型变量
	 * 
	 * @param timeValue
	 *			时间型变量
	 * 
	 * @param dateValue
	 *			日期型变量
	 * 
	 */
	public CodeGenerationSample(String stringValue, String attachmentValue, String attachmentsValue,
			String pictureValue, String textValue, Integer integerValue, Long longValue, Float FloatValue,
			Boolean booleanValue, Date datetimeValue, Date timeValue, Date dateValue) {
		super();
		this.stringValue = stringValue;
		this.attachmentValue = attachmentValue;
		this.attachmentsValue = attachmentsValue;
		this.pictureValue = pictureValue;
		this.textValue = textValue;
		this.integerValue = integerValue;
		this.longValue = longValue;
		this.FloatValue = FloatValue;
		this.booleanValue = booleanValue;
		this.datetimeValue = datetimeValue;
		this.timeValue = timeValue;
		this.dateValue = dateValue;
	}

	/**
	 * @return stringValue
	 *			字符型变量
	 */
	public String getStringValue() {
		return stringValue;
	}

	/**
	 * @param stringValue
	 *			字符型变量
	 */
	public void setStringValue(String stringValue) {
		this.stringValue = stringValue;
	}

	/**
	 * @return attachmentValue
	 *			单附件变量
	 */
	public String getAttachmentValue() {
		return attachmentValue;
	}

	/**
	 * @param attachmentValue
	 *			单附件变量
	 */
	public void setAttachmentValue(String attachmentValue) {
		this.attachmentValue = attachmentValue;
	}

	/**
	 * @return attachmentsValue
	 *			多附件变量
	 */
	public String getAttachmentsValue() {
		return attachmentsValue;
	}

	/**
	 * @param attachmentsValue
	 *			多附件变量
	 */
	public void setAttachmentsValue(String attachmentsValue) {
		this.attachmentsValue = attachmentsValue;
	}

	/**
	 * @return pictureValue
	 *			图片变量
	 */
	public String getPictureValue() {
		return pictureValue;
	}

	/**
	 * @param pictureValue
	 *			图片变量
	 */
	public void setPictureValue(String pictureValue) {
		this.pictureValue = pictureValue;
	}

	/**
	 * @return textValue
	 *			备注型变量
	 */
	public String getTextValue() {
		return textValue;
	}

	/**
	 * @param textValue
	 *			备注型变量
	 */
	public void setTextValue(String textValue) {
		this.textValue = textValue;
	}

	/**
	 * @return integerValue
	 *			整型变量
	 */
	public Integer getIntegerValue() {
		return integerValue;
	}

	/**
	 * @param integerValue
	 *			整型变量
	 */
	public void setIntegerValue(Integer integerValue) {
		this.integerValue = integerValue;
	}

	/**
	 * @return longValue
	 *			长整型变量
	 */
	public Long getLongValue() {
		return longValue;
	}

	/**
	 * @param longValue
	 *			长整型变量
	 */
	public void setLongValue(Long longValue) {
		this.longValue = longValue;
	}

	/**
	 * @return FloatValue
	 *			双精度型变量
	 */
	public Float getFloatValue() {
		return FloatValue;
	}

	/**
	 * @param FloatValue
	 *			双精度型变量
	 */
	public void setFloatValue(Float FloatValue) {
		this.FloatValue = FloatValue;
	}

	/**
	 * @return booleanValue
	 *			逻辑型变量
	 */
	public Boolean getBooleanValue() {
		return booleanValue;
	}

	/**
	 * @param booleanValue
	 *			逻辑型变量
	 */
	public void setBooleanValue(Boolean booleanValue) {
		this.booleanValue = booleanValue;
	}

	/**
	 * @return datetimeValue
	 *			日期时间型变量
	 */
	public Date getDatetimeValue() {
		return datetimeValue;
	}

	/**
	 * @param datetimeValue
	 *			日期时间型变量
	 */
	public void setDatetimeValue(Date datetimeValue) {
		this.datetimeValue = datetimeValue;
	}

	/**
	 * @return timeValue
	 *			时间型变量
	 */
	public Date getTimeValue() {
		return timeValue;
	}

	/**
	 * @param timeValue
	 *			时间型变量
	 */
	public void setTimeValue(Date timeValue) {
		this.timeValue = timeValue;
	}

	/**
	 * @return dateValue
	 *			日期型变量
	 */
	public Date getDateValue() {
		return dateValue;
	}

	/**
	 * @param dateValue
	 *			日期型变量
	 */
	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}

}
