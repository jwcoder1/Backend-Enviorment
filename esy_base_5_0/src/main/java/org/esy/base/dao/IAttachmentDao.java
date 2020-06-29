package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Attachment;
import org.esy.base.entity.dto.NameValue;

/**
 * @author 颜惠强 2015-08-14
 */
public interface IAttachmentDao extends IBaseDao<Attachment> {
	/**
	 * 通过entityUid获取附件信息
	 * 
	 * @param entityUid
	 * @return
	 */
	List<Attachment> search(String entityUid);

	/**
	 * 通过attachment获取附件信息
	 * 
	 * @param attachmentId
	 * @return
	 */
	List<Attachment> searchByAttId(String attachmentId);

	/**
	 * 通过attachment获取第一个附件信息
	 * 
	 * @param attachmentId
	 * @return
	 */
	Attachment searchOneByAttId(String attachmentId);

	/**
	 * 通过entityUid获取附件信息
	 * 
	 * @param entityUid
	 * @return
	 */
	List<Attachment> getByEntityUid(String entityUid);

	/**
	 * 获取过期文件
	 * 
	 * @param now
	 * @return
	 */
	List<Attachment> getAttachmentsByDate(String now);

	/**
	 * 通过附件编号获取附件信息
	 * 
	 * @param attachmentId
	 * @return
	 */
	List<Attachment> getByAttachmentId(String attachmentId);

	/**
	 * 获取未绑定的附件信息
	 * 
	 * @return
	 */
	List<Attachment> getAttachments();

	/**
	 * 通过attId/status获取附件信息
	 * 
	 * @param attId
	 * @param status
	 * @return
	 */
	List<Attachment> getByAttIdByStatus(String attId, String status);

	boolean deleteByAttId(String attId);

	/**
	 * 通过attId获取附件下载URL信息
	 * 
	 * @param attId
	 * @return
	 */
	public String getUrlFromAttId(String attId);

	/**
	 * 通过attIds获取附件下载URL信息
	 * 
	 * @param attId
	 * @return
	 */
	public List<NameValue> listUrlFromAttId(List<String> attIds);

}
