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
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@EntityInfo("消息队列")
@Table(name = "base_mqueue", indexes = { @Index(name = "rlog_created", columnList = "created"),
		@Index(name = "rlog_updated", columnList = "updated"), @Index(name = "q_qid", columnList = "q_qid"),
		@Index(name = "q_lastscheduler", columnList = "q_lastscheduler"), @Index(name = "q_seq", columnList = "q_seq") })
public class MQueue extends Base {

	private static final long serialVersionUID = 1L;

	@FieldInfo("队列编号")
	@Column(name = "q_qid", length = 50, nullable = false)
	private String qid;

	@FieldInfo("消息序列")
	@Column(name = "q_seq", nullable = false)
	private long seq;

	@Lob
	@FieldInfo("消息内容")
	@Column(name = "q_data")
	private String data;

	@FieldInfo("状态")
	@Column(name = "q_state", length = 50)
	private String state;

	@FieldInfo("附件")
	@Column(name = "q_attachments", length = 50)
	private String attachments;

	@FieldInfo("消息原地址")
	@Column(name = "q_ip", length = 50)
	private String ipAddress;

	@FieldInfo("最后调度时间")
	@Column(name = "q_lastscheduler")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date lastScheduler = null;

	@FieldInfo("最后调度信息")
	@Column(name = "q_lastmessage", length = 50)
	private String lastMessage;

	@FieldInfo("已经失败次数")
	@Column(name = "q_errorCount")
	private int errorCount;

	/**
	 * @return the state name
	 */
	public final String getStateName() {
		if ("10".equals(state)) {
			return "排队";
		} else if ("20".equals(state)) {
			return "失败";
		} else {
			return "其他 " + state;
		}
	}

	/**
	 * 
	 */
	public MQueue() {
		super();
	}

	/**
	 * @param qid
	 * @param seq
	 * @param data
	 * @param state
	 * @param attachments
	 * @param ipAddress
	 * @param lastScheduler
	 * @param lastMessage
	 * @param errorCount
	 */
	public MQueue(String qid, long seq, String data, String state, String attachments, String ipAddress,
			Date lastScheduler, String lastMessage, int errorCount) {
		super();
		this.qid = qid;
		this.seq = seq;
		this.data = data;
		this.state = state;
		this.attachments = attachments;
		this.ipAddress = ipAddress;
		this.lastScheduler = lastScheduler;
		this.lastMessage = lastMessage;
		this.errorCount = errorCount;
	}

	/**
	 * @return the qid
	 */
	public final String getQid() {
		return qid;
	}

	/**
	 * @param qid the qid to set
	 */
	public final void setQid(String qid) {
		this.qid = qid;
	}

	/**
	 * @return the seq
	 */
	public final long getSeq() {
		return seq;
	}

	/**
	 * @param seq the seq to set
	 */
	public final void setSeq(long seq) {
		this.seq = seq;
	}

	/**
	 * @return the data
	 */
	public final String getData() {
		return data;
	}

	/**
	 * @param data the data to set
	 */
	public final void setData(String data) {
		this.data = data;
	}

	/**
	 * @return the state
	 */
	public final String getState() {
		return state;
	}

	/**
	 * @param state the state to set
	 */
	public final void setState(String state) {
		this.state = state;
	}

	/**
	 * @return the attachments
	 */
	public final String getAttachments() {
		return attachments;
	}

	/**
	 * @param attachments the attachments to set
	 */
	public final void setAttachments(String attachments) {
		this.attachments = attachments;
	}

	/**
	 * @return the ipAddress
	 */
	public final String getIpAddress() {
		return ipAddress;
	}

	/**
	 * @param ipAddress the ipAddress to set
	 */
	public final void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	/**
	 * @return the lastScheduler
	 */
	public final Date getLastScheduler() {
		return lastScheduler;
	}

	/**
	 * @param lastScheduler the lastScheduler to set
	 */
	public final void setLastScheduler(Date lastScheduler) {
		this.lastScheduler = lastScheduler;
	}

	/**
	 * @return the lastMessage
	 */
	public final String getLastMessage() {
		return lastMessage;
	}

	/**
	 * @param lastMessage the lastMessage to set
	 */
	public final void setLastMessage(String lastMessage) {
		this.lastMessage = lastMessage;
	}

	/**
	 * @return the errorCount
	 */
	public final int getErrorCount() {
		return errorCount;
	}

	/**
	 * @param errorCount the errorCount to set
	 */
	public final void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}
