package org.esy.base.core;

import net.sf.json.JSONObject;

public class MQueueBody {

	private String qid;

	private String attachmentId;

	private JSONObject jsonData;

	public MQueueBody() {
		super();
	}

	/**
	 * @param qid
	 * @param attachmentId
	 * @param jsonData
	 */
	public MQueueBody(String qid, String attachmentId, JSONObject jsonData) {
		super();
		this.qid = qid;
		this.attachmentId = attachmentId;
		this.jsonData = jsonData;
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
	 * @return the attachmentId
	 */
	public final String getAttachmentId() {
		return attachmentId;
	}

	/**
	 * @param attachmentId the attachmentId to set
	 */
	public final void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	/**
	 * @return the jsonData
	 */
	public final JSONObject getJsonData() {
		return jsonData;
	}

	/**
	 * @param jsonData the jsonData to set
	 */
	public final void setJsonData(JSONObject jsonData) {
		this.jsonData = jsonData;
	}

}
