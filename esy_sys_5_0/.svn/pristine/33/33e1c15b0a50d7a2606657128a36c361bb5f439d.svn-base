package org.esy.base.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.core.Message;
import org.esy.base.entity.Attachment;
import org.esy.base.entity.dto.NameValue;
import org.springframework.web.multipart.MultipartFile;

public interface IAttachmentService {
	/**
	 * 上传多个附件
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param files
	 *            上传的数据
	 * @return message中的data是一个List<Attachment>,成功失败个数存放于message中的message
	 */
	Message upload(MultipartFile[] files, Map<String, Object> parm);

	/**
	 * 通过Uid下载附件
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param uid
	 * @param mode
	 *            show:查看,如查看图片等 down:直接下载附件
	 * @param request
	 * @param response
	 */
	void download(String uid, String mode, HttpServletRequest request, HttpServletResponse response) throws IOException;

	/**
	 * 通过attachmentId打包下载附件
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param entityUid
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	String packageDownload(String entityUid, HttpServletRequest request, HttpServletResponse response)
			throws IOException;

	/**
	 * 删除单个或多个附件
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param uids
	 *            要删除的附件的uid 多个使用逗号分割
	 */
	// void delete(String uids);

	/**
	 * 根据 uid 取得实体
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param uid
	 * @return 取得的实体
	 */
	public Attachment getByUid(String uid);

	/**
	 * 清除过期的文件
	 * 
	 * @author huiqiang.yan 2015-08-17
	 * @return
	 */
	Message expiredClean();

	/**
	 * 更新绑定实体
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @param parm
	 * @return
	 */
	boolean updateBinds(Map<String, Object> parm);

	boolean deleteByUids(String uid);

	/**
	 * 清除未绑定的文件
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @return
	 */
	Message unBindsClean();

	/**
	 * 更新过期时间
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @param parm
	 * @return
	 */
	boolean update(Map<String, Object> parm);

	/**
	 * 根据attachmentId取得uid
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @param parm
	 * @return
	 */
	public List<Attachment> searchByAttId(String attachmentId);

	boolean deleteByAttId(String attId);

	boolean getVideoImage(String videoUrl);

	/**
	 * 通过attId/status获取附件信息
	 * 
	 * @author huiqiang.yan 2016-07-27
	 * @param attId
	 *            附件编号
	 * @param status
	 *            状态
	 * @return
	 */
	List<Attachment> getByAttIdByStatus(String attId, String status);

	Message share(String uid);

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

	/**
	 * 上传单个文件包含pc,ipad,iphone端图档
	 * 
	 * @param files
	 * @param parm
	 * @return
	 */
	Message upload(Map<String, MultipartFile> files, Map<String, Object> parm);

}
