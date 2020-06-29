package org.esy.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.esy.base.annotation.EntityInfo;
import org.esy.base.annotation.FieldInfo;
import org.esy.base.core.Base;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@EntityInfo("附件")
@Table(name = "base_mattatchment", indexes = { @Index(name = "mattatchment_created", columnList = "created"),
		@Index(name = "mattatchment_updated", columnList = "updated"),
		@Index(name = "atm_attachmentid", columnList = "atm_attachmentid") })
public class Attachment extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("文件名称")
	@Column(name = "atm_filename")
	private String fileName;

	@FieldInfo("文件路径")
	@Column(name = "atm_filepath")
	private String filePath;

	@FieldInfo("文件大小")
	@Column(name = "atm_filesize")
	private Long fileSize;

	@FieldInfo("状态")
	@Column(name = "atm_status")
	private String status;//1. 临时， 2. 正式， 3. 过期已删除

	@FieldInfo("类型")
	@Column(name = "atm_type")
	private String type;

	@FieldInfo("过期时间")
	@Column(name = "atm_expired")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date expired = null;

	@FieldInfo("附件编号")
	@Column(name = "atm_attachmentid")
	private String attachmentId;

	@FieldInfo("顺序号")
	@Column(name = "atm_sequence")
	private Integer sequence;//同一附件编号的顺序号

	@FieldInfo("表名")
	@Column(name = "atm_entityname")
	private String entityName;//关联表的名字  用于查找已删除的文件清理

	@FieldInfo("表主键")
	@Column(name = "atm_entityuid")
	private String entityUid;//关联表的主键

	@FieldInfo("表字段")
	@Column(name = "atm_fieldname")
	private String fieldName;//管理表的字段

	@FieldInfo("物理文件名")
	@Column(name = "atm_physicsname")
	private String physicsName;

	@FieldInfo("备注")
	@Column(name = "remark")
	private String remark;

	@FieldInfo("是图片夹的图片(表示有缩略图)")
	@Column(name = "atm_isImage")
	private Boolean isImage;

	@FieldInfo("符加对像")
	@Transient
	@JsonProperty("entitymsg")
	private Object entitymsg;

	public Attachment() {
		super();
	}

	public Attachment(String fileName, String filePath, Long fileSize, String status, String type, Date expired,
			String attachmentId, Integer sequence, String entityName, String entityUid, String fieldName,
			String physicsName) {
		super();
		this.fileName = fileName;
		this.filePath = filePath;
		this.fileSize = fileSize;
		this.status = status;
		this.type = type;
		this.expired = expired;
		this.attachmentId = attachmentId;
		this.sequence = sequence;
		this.entityName = entityName;
		this.entityUid = entityUid;
		this.fieldName = fieldName;
		this.physicsName = physicsName;
	}

	public Boolean getIsImage() {
		return isImage;
	}

	public void setIsImage(Boolean isImage) {
		this.isImage = isImage;
	}

	/**
	 * 
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * 
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * 
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * 
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * 
	 * @return the fileSize
	 */
	public Long getFileSize() {
		return fileSize;
	}

	/**
	 * 
	 * @param fileSize the fileSize to set
	 */
	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	/**
	 * 
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * 
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * 
	 * @return the expired
	 */
	public Date getExpired() {
		return expired;
	}

	/**
	 * 
	 * @param expired the expired to set
	 */
	public void setExpired(Date expired) {
		this.expired = expired;
	}

	/**
	 * 
	 * @return the attachmentId
	 */
	public String getAttachmentId() {
		return attachmentId;
	}

	/**
	 * 
	 * @param attachmentId the attachmentId to set
	 */
	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * 
	 * @return the sequence
	 */
	public Integer getSequence() {
		return sequence;
	}

	/**
	 * 
	 * @param sequence the sequence to set
	 */
	public void setSequence(Integer sequence) {
		this.sequence = sequence;
	}

	/**
	 * 
	 * @return the entityName
	 */
	public String getEntityName() {
		return entityName;
	}

	/**
	 * 
	 * @param entityName the entityName to set
	 */
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	/**
	 * 
	 * @return the entityUid
	 */
	public String getEntityUid() {
		return entityUid;
	}

	/**
	 * 
	 * @param entityUid the entitUid to set
	 */
	public void setEntityUid(String entityUid) {
		this.entityUid = entityUid;
	}

	/**
	 * 
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * 
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * 
	 * @return the physicsName
	 */
	public String getPhysicsName() {
		return physicsName;
	}

	/**
	 * 
	 * @param physicsName the physicsName to set
	 */
	public void setPhysicsName(String physicsName) {
		this.physicsName = physicsName;
	}

	/**
	 * @return the entitymsg
	 */
	public Object getEntitymsg() {
		return entitymsg;
	}

	/**
	 * @param entitymsg the entitymsg to set
	 */
	public void setEntitymsg(Object entitymsg) {
		this.entitymsg = entitymsg;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
