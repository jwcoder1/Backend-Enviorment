package org.esy.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.Message;
import org.esy.base.core.Response;
import org.esy.base.dao.IAttachmentDao;
import org.esy.base.entity.Attachment;
import org.esy.base.service.IAttachmentService;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.Maps;

/**
 * 附件上传控制器
 * 
 * @author 颜惠强 2015.08.13
 * 
 */
@Controller
@RequestMapping("/api/base/attachment")
public class AttachmentController {

	@Autowired
	private IAttachmentService service;

	@Autowired
	private IAttachmentDao dao;

	@RequestMapping(value = "/getUuid")
	@ResponseBody
	public String getUuid() {
		return UuidUtils.getUUID();
	}

	/**
	 * 附件上传，实现单个或多个同时上传
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param files
	 *            key:notCheckStatus 直接设为状态正式,无需在保存的时候再更新 isImage:生成缩略图
	 *            attachmentId:必须
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/upload")
	@ResponseBody
	public ResponseEntity<Response> upload(@RequestParam(required = true, value = "file") MultipartFile[] files,
			HttpServletRequest request, @RequestParam Map<String, Object> parm) {
		Message msg = service.upload(files, parm);
		Response resp;
		if (!msg.isSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg.getMessage(), null);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attachmentId", msg.getId());
			map.put("data", (ArrayList<Attachment>) msg.getData());
			resp = new Response(0, msg.getMessage(), map);
		}
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 上传base64图档
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param files
	 *            key:notCheckStatus 直接设为状态正式,无需在保存的时候再更新 isImage:生成缩略图
	 *            attachmentId:必须
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/base64upload")
	@ResponseBody
	public ResponseEntity<Response> base64upload(@RequestParam(required = true, value = "base64file") String base64file,
			HttpServletRequest request, @RequestParam Map<String, Object> parm) {

		MultipartFile[] files = new MultipartFile[] { BaseUtil.base64ToMultipart(base64file) };
		Message msg = service.upload(files, parm);
		Response resp;
		if (!msg.isSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg.getMessage(), null);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attachmentId", msg.getId());
			map.put("data", (ArrayList<Attachment>) msg.getData());
			resp = new Response(0, msg.getMessage(), map);
		}
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 上传base64图档含大图中图小图
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param files
	 *            key:notCheckStatus 直接设为状态正式,无需在保存的时候再更新 isImage:生成缩略图
	 *            attachmentId:必须
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/base64uploadall")
	@ResponseBody
	public ResponseEntity<Response> base64uploadall(HttpServletRequest request,
			@RequestParam Map<String, Object> parm) {

		Map<String, MultipartFile> files = Maps.newHashMap();
		if (BaseUtil.isNotEmpty(parm.get("pc"))) {
			files.put("pc", BaseUtil.base64ToMultipart(BaseUtil.toString(parm.get("pc"))));
		}

		if (BaseUtil.isNotEmpty(parm.get("small"))) {
			files.put("small", BaseUtil.base64ToMultipart(BaseUtil.toString(parm.get("small"))));
		}

		if (BaseUtil.isNotEmpty(parm.get("middle"))) {
			files.put("middle", BaseUtil.base64ToMultipart(BaseUtil.toString(parm.get("middle"))));
		}

		Message msg = service.upload(files, parm);
		Response resp;
		if (!msg.isSuccess()) {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), msg.getMessage(), null);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("attachmentId", msg.getId());
			map.put("data", (ArrayList<Attachment>) msg.getData());
			resp = new Response(0, msg.getMessage(), map);
		}
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 附件下载
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/download")
	public void download(@RequestParam String uid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		YESUtil.outPrintln("进入了download uid:" + uid);
		service.download(uid, "down", request, response);
	}

	/**
	 * 附件下载仅下载第一张
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/downloadbyAttId")
	public void downloadbyAttId(@RequestParam String attId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Attachment> attachments = dao.searchByAttId(attId);
		if (BaseUtil.isNotEmpty(attachments)) {
			service.download(attachments.get(0).getUid(), "down", request, response);
		} else {
			response.setStatus(404);
		}

	}

	/**
	 * 附件下载仅下载第一张
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/downloadAllbyAttId")
	public void downloadAllbyAttId(@RequestParam String attId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		List<Attachment> attachments = dao.getByAttachmentId(attId);
		service.download(attachments.get(0).getUid(), "down", request, response);
	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showthumb")
	public void showthumb(@RequestParam String uid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		service.download(uid, "showthumb", request, response);
	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showthumbbyAttId")
	public void showthumbbyAttId(@RequestParam String attId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Attachment> attachments = dao.searchByAttId(attId);
		service.download(attachments.get(0).getUid(), "showthumb", request, response);
		// service.download(uid, "showthumb", request, response);
	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showthumbAllbyAttId")
	public void showthumbAllbyAttId(@RequestParam String attId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Attachment> attachments = dao.getByAttachmentId(attId);
		service.download(attachments.get(0).getUid(), "showthumb", request, response);
		// service.download(uid, "showthumb", request, response);
	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showMiddleThumb")
	public void showMiddleThumb(@RequestParam String uid, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		service.download(uid, "showthumbMiddle", request, response);
	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showMiddleThumbbyAttId")
	public void showMiddleThumbbyAttId(@RequestParam String attId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Attachment> attachments = dao.getByAttachmentId(attId);
		service.download(attachments.get(0).getUid(), "showthumbMiddle", request, response);

	}

	/**
	 * 显示缩略图
	 * 
	 * @author qiongwei.cai 2015-09-16
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/showMiddleThumbAllbyAttId")
	public void showMiddleThumbAllbyAttId(@RequestParam String attId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Attachment> attachments = dao.searchByAttId(attId);
		service.download(attachments.get(0).getUid(), "showthumbMiddle", request, response);

	}

	/**
	 * 下载到项目目录下，分享直接使用
	 * 
	 * @author huiqiang.yan 2016-10-24
	 * @param uid
	 * @return
	 */
	@RequestMapping(value = "/share")
	@ResponseBody
	public ResponseEntity<Response> share(@RequestParam String uid) throws Exception {
		Response resp = new Response(0, "success.", service.share(uid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过attId获取附件信息
	 * 
	 * @author geass.cai 2015-09-14
	 * @param attId
	 * @return
	 */
	@RequestMapping(value = "/getByAttId")
	@ResponseBody
	public ResponseEntity<Response> search(@RequestParam String attId) {
		Response resp = new Response(0, "success.", dao.searchByAttId(attId));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过attId获取附件信息包括未保存
	 * 
	 * @author geass.cai 2015-09-14
	 * @param attId
	 * @return
	 */
	@RequestMapping(value = "/getallByAttId")
	@ResponseBody
	public ResponseEntity<Response> getallByAttId(@RequestParam String attId) {
		Response resp = new Response(0, "success.", dao.getByAttachmentId(attId));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过uid删除附件信息
	 * 
	 * @author geass.cai 2015-09-14
	 * @param entityUid
	 * @return
	 */
	@RequestMapping(value = "/{uid}", method = RequestMethod.DELETE)
	@ResponseBody
	@Transactional
	public ResponseEntity<Response> deleteByUid(@PathVariable String uid) {
		Response resp = new Response(0, "success.", service.deleteByUids(uid));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过attachmentId打包下载
	 * 
	 * @param entityUid
	 *            关联表主键
	 * @author huiqiang.yan 2015-08-17
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/packageDownload")
	public void packageDownload(@RequestParam String entityUid, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		service.packageDownload(entityUid, request, response);
	}

	/**
	 * 通过 uid 更新过期时间
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param uid
	 *            主键编号
	 * @param expired
	 *            过期时间
	 * @return
	 */
	@RequestMapping(value = "/update")
	@ResponseBody
	public ResponseEntity<Response> update(@RequestParam Map<String, Object> parm) {
		Response resp;
		boolean result = service.update(parm);
		if (result) {
			resp = new Response(0, "update success.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Uid or Object does not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 清除过期的文件
	 * 
	 * @author huiqiang.yan 2015-08-17
	 * @return
	 */
	@RequestMapping(value = "/expiredClean")
	@ResponseBody
	public ResponseEntity<Response> expiredClean() {
		Message msg = service.expiredClean();
		Response resp = new Response(0, msg.getMessage(), null);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 更新附件绑定
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @param parm
	 * @return
	 */
	@RequestMapping(value = "/updateBinds")
	@ResponseBody
	public ResponseEntity<Response> updateBinds(@RequestParam Map<String, Object> parm) {
		Response resp;
		boolean result = service.updateBinds(parm);
		if (result) {
			resp = new Response(0, "update success", null);
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		} else {
			resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "attachmentId does not found.", null);
			return new ResponseEntity<Response>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	/**
	 * 清除未绑定的文件
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @return
	 */
	@RequestMapping(value = "/unBindsClean")
	@ResponseBody
	public ResponseEntity<Response> unBindsClean() {
		Message msg = service.unBindsClean();
		Response resp = new Response(0, msg.getMessage(), null);
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 删除附件信息
	 * 
	 * @author huiqiang.yan 2016-06-25
	 * @param attId
	 *            附件编号
	 * @param status
	 *            状态
	 * @return
	 */
	@RequestMapping(value = "/deleteByAttId/{attId}")
	@ResponseBody
	public ResponseEntity<Response> deleteByAttId(@PathVariable String attId) {
		Response resp = new Response(0, "success.", service.deleteByAttId(attId));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	/**
	 * 通过视频路径生成视频截图,如果存在则不重新生成
	 * 
	 * @author huiqiang.yan 2016-07-11
	 * @param videoUrl
	 *            视频地址
	 * @return
	 */
	@RequestMapping(value = "/getVideoImage")
	@ResponseBody
	public ResponseEntity<Response> getVideoImage(@RequestParam String videoUrl) {
		Response resp = new Response(0, "success.", service.getVideoImage(videoUrl));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

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
	@RequestMapping(value = "/getByAttIdByStatus/{attId}/{status}")
	@ResponseBody
	public ResponseEntity<Response> getByAttIdByStatus(@PathVariable String attId, @PathVariable String status) {
		Response resp = new Response(0, "success.", service.getByAttIdByStatus(attId, status));
		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}
}
