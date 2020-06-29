package org.esy.base.service.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jcifs.smb.SmbException;

import org.apache.commons.io.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.Message;
import org.esy.base.dao.IAttachmentDao;
import org.esy.base.entity.Attachment;
import org.esy.base.entity.dto.NameValue;
import org.esy.base.service.IAttachmentService;
import org.esy.base.util.ImageUtils;
import org.esy.base.util.UuidUtils;
import org.esy.base.util.YESConfig;
import org.esy.base.util.YESUtil;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Component
public class AttachmentServiceImpl implements IAttachmentService {
	private static final String ATTATCH_ROOT_PATH_FORMAT = "attachments/%s/";

	private static final String THUMB_SMALL = "_150X150";

	private static final String THUMB_MIDDLE = "_600X600";

	@Autowired
	private IAttachmentDao attachmentDao;

	@Transactional
	public Message upload(MultipartFile[] files, Map<String, Object> parm) {
		Message msg = new Message();
		List<Attachment> ls = new ArrayList<Attachment>();
		Integer ok = 0, error = 0, i = 1;
		String lastAttId = BaseUtil.toString(parm.get("attachmentId"));
		String attachmentId = BaseUtil.isEmpty(lastAttId) ? UuidUtils.getUUID() : lastAttId;
		String temp = YESUtil.toString(parm.get("expired"));
		String remark = YESUtil.toString(parm.get("remark"));
		Boolean isImage = BaseUtil.objToBoolean(parm.get("isImage"));
		String pc = YESUtil.toString(parm.get("pc"));
		String small = YESUtil.toString(parm.get("small"));
		String middle = YESUtil.toString(parm.get("middle"));
		Date expired = getExpired(temp);
		boolean notCheckStatus = YESUtil.objToBoolean(parm.get("notCheckStatus"));
		for (MultipartFile file : files) {
			if (file.isEmpty()) {
				continue;
			}
			Attachment entity = upload(file, i, attachmentId, expired, isImage, notCheckStatus, remark, pc, small,
					middle);
			pc = "";
			small = "";
			middle = "";
			i = i + 1;
			if (entity == null) {
				error++;
				continue;
			} else {
				ls.add(entity);
				ok++;
			}
		}
		msg.setData(ls);
		boolean success;
		String msgstr = "";
		if (ok == 0) {
			msgstr = "上传失败!";
			success = false;
		} else {
			if (error == 0) {
				msgstr = ok + "个文件上传成功";
			} else {
				msgstr = ok + "个文件上传成功," + error + "个文件上传失败";
			}
			success = true;
		}
		msg.setId(attachmentId);
		msg.setMessage(msgstr);
		msg.setSuccess(success);
		return msg;
	}

	@Override
	@Transactional
	public Message upload(Map<String, MultipartFile> files, Map<String, Object> parm) {
		Message msg = new Message();
		List<Attachment> ls = new ArrayList<Attachment>();
		Integer ok = 0, error = 0, i = 1;
		String lastAttId = BaseUtil.toString(parm.get("attachmentId"));
		String attachmentId = BaseUtil.isEmpty(lastAttId) ? UuidUtils.getUUID() : lastAttId;
		String temp = YESUtil.toString(parm.get("expired"));
		String remark = YESUtil.toString(parm.get("remark"));
		Boolean isImage = BaseUtil.objToBoolean(parm.get("isImage"));
		Date expired = getExpired(temp);
		boolean notCheckStatus = YESUtil.objToBoolean(parm.get("notCheckStatus"));

		Attachment entity = upload(files, i, attachmentId, expired, isImage, notCheckStatus, remark);
		if (entity == null) {
			error++;
		} else {
			ls.add(entity);
			ok++;
		}

		msg.setData(ls);
		boolean success;
		String msgstr = "";
		if (ok == 0) {
			msgstr = "上传失败!";
			success = false;
		} else {
			if (error == 0) {
				msgstr = ok + "个文件上传成功";
			} else {
				msgstr = ok + "个文件上传成功," + error + "个文件上传失败";
			}
			success = true;
		}
		msg.setId(attachmentId);
		msg.setMessage(msgstr);
		msg.setSuccess(success);
		return msg;
	}

	private Attachment upload(Map<String, MultipartFile> files, Integer i, String attachmentId, Date expired,
			Boolean isImage, boolean notCheckStatus, String remark) {
		try {
			MultipartFile file = files.get("pc");
			if (!file.isEmpty()) {// 非空判断

				InputStream stream = file.getInputStream();
				byte[] buffer = new byte[stream.available()];
				stream.read(buffer);
				stream.close();
				DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
				String relatePath = String.format(ATTATCH_ROOT_PATH_FORMAT, format.print(System.currentTimeMillis()));
				long millisecond = System.currentTimeMillis();
				String fileUrl = prepareOutFilePath(relatePath).getPath() + File.separator + millisecond;
				File newFile = new File(fileUrl);
				if (!newFile.exists())
					newFile.createNewFile();
				FileOutputStream fileStream = new FileOutputStream(newFile);
				fileStream.write(buffer);
				fileStream.flush();
				fileStream.close();
				String fileVideoUrl = "";
				if (file.getOriginalFilename().indexOf(".jpg") >= 0 || file.getOriginalFilename().indexOf(".png") >= 0
						|| file.getOriginalFilename().indexOf(".gif") >= 0
						|| file.getOriginalFilename().indexOf(".bmp") >= 0
						|| file.getOriginalFilename().indexOf(".jepg") >= 0
						|| file.getContentType().indexOf("image") >= 0) {
					isImage = true;
				} else {
					isImage = false;
				}
				Attachment entity = new Attachment();
				if (YESUtil.isNotEmpty(isImage) && !isImage) {// 如果是视频,在服务端生成备用视频,便于取视频截图
					fileVideoUrl = prepareOutFilePath(relatePath).getPath() + File.separator
							+ file.getOriginalFilename();
					File newVideoFile = new File(fileVideoUrl);
					if (!newFile.exists())
						newFile.createNewFile();
					FileOutputStream fileVideoStream = new FileOutputStream(newVideoFile);
					fileVideoStream.write(buffer);
					fileVideoStream.flush();
					fileVideoStream.close();
					String saveFilePath = prepareOutFilePath(relatePath).getPath() + File.separator + millisecond
							+ THUMB_SMALL;
					boolean result = processImg(fileVideoUrl, YESUtil.getuploadPath("/") + "ffmpeg.exe", saveFilePath);
					if (result) {
						// 图片截取成功删除备用视频
						Thread.sleep(1000l);
						File tempFile = new File(fileUrl);
						if (tempFile.exists())
							tempFile.delete();
					}
				}
				if (isImage) {
					byte[] thumb = ImageUtils.imgToThumb(files.get("small").getInputStream(), 150, 150);
					FileOutputStream thumbStream = new FileOutputStream(new File(fileUrl + THUMB_SMALL));
					thumbStream.write(thumb);
					thumbStream.flush();
					thumbStream.close();
					thumb = ImageUtils.imgToThumb(files.get("middle").getInputStream(), 600, 600);
					FileOutputStream thumbStreamMiddle = new FileOutputStream(new File(fileUrl + THUMB_MIDDLE));
					thumbStreamMiddle.write(thumb);
					thumbStreamMiddle.flush();
					thumbStreamMiddle.close();
				}
				entity.setIsImage(isImage);
				entity.setAttachmentId(attachmentId);
				entity.setFileName(file.getOriginalFilename());
				entity.setFilePath(relatePath);
				entity.setFileSize(newFile.length());
				entity.setSequence(i);
				entity.setType(file.getContentType());
				entity.setRemark(remark);
				if (notCheckStatus) {
					entity.setStatus("2");
				} else {
					entity.setStatus("1");
				}
				entity.setExpired(expired);
				entity.setPhysicsName(millisecond + "");
				entity = attachmentDao.save(entity);
				return entity;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();// 缩略图出错
		}
		return null;
	}

	@Transactional
	public Attachment upload(MultipartFile file, Integer i, String attachmentId, Date expired, Boolean isImage,
			Boolean notCheckStatus, String remark, String pc, String small, String middle) {
		try {
			if (!file.isEmpty()) {// 非空判断
				InputStream stream = file.getInputStream();
				byte[] buffer = new byte[stream.available()];
				stream.read(buffer);
				stream.close();
				DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMdd");
				String relatePath = String.format(ATTATCH_ROOT_PATH_FORMAT, format.print(System.currentTimeMillis()));
				long millisecond = System.currentTimeMillis();
				String fileUrl = prepareOutFilePath(relatePath).getPath() + File.separator + millisecond;
				File newFile = new File(fileUrl);
				if (!newFile.exists())
					newFile.createNewFile();
				if (BaseUtil.isNotEmpty(pc)) {
					MultipartFile filepc = BaseUtil.base64ToMultipart(pc);
					stream = filepc.getInputStream();
					buffer = new byte[stream.available()];
					stream.read(buffer);
					stream.close();

				}
				FileOutputStream fileStream = new FileOutputStream(newFile);
				fileStream.write(buffer);
				fileStream.flush();
				fileStream.close();
				String fileVideoUrl = "";
				if (file.getOriginalFilename().indexOf(".jpg") >= 0 || file.getOriginalFilename().indexOf(".png") >= 0
						|| file.getOriginalFilename().indexOf(".gif") >= 0
						|| file.getOriginalFilename().indexOf(".bmp") >= 0
						|| file.getOriginalFilename().indexOf(".jepg") >= 0
						|| file.getContentType().indexOf("image") >= 0) {
					isImage = true;
				} else {
					isImage = false;
				}
				Attachment entity = new Attachment();
				if (YESUtil.isNotEmpty(isImage) && !isImage) {// 如果是视频,在服务端生成备用视频,便于取视频截图
					fileVideoUrl = prepareOutFilePath(relatePath).getPath() + File.separator
							+ file.getOriginalFilename();
					File newVideoFile = new File(fileVideoUrl);
					if (!newFile.exists())
						newFile.createNewFile();
					FileOutputStream fileVideoStream = new FileOutputStream(newVideoFile);
					fileVideoStream.write(buffer);
					fileVideoStream.flush();
					fileVideoStream.close();
					String saveFilePath = prepareOutFilePath(relatePath).getPath() + File.separator + millisecond
							+ THUMB_SMALL;
					boolean result = processImg(fileVideoUrl, YESUtil.getuploadPath("/") + "ffmpeg.exe", saveFilePath);
					if (result) {
						// 图片截取成功删除备用视频
						Thread.sleep(1000l);
						File tempFile = new File(fileUrl);
						if (tempFile.exists())
							tempFile.delete();
					}
				}
				if (isImage) {
					MultipartFile clipfile = file;
					if (BaseUtil.isNotEmpty(small)) {
						clipfile = BaseUtil.base64ToMultipart(small);
					}
					byte[] thumb = ImageUtils.imgToThumb(clipfile.getInputStream(), 150, 150);
					FileOutputStream thumbStream = new FileOutputStream(new File(fileUrl + THUMB_SMALL));
					thumbStream.write(thumb);
					thumbStream.flush();
					thumbStream.close();
					clipfile = file;
					if (BaseUtil.isNotEmpty(middle)) {
						clipfile = BaseUtil.base64ToMultipart(middle);
					}
					thumb = ImageUtils.imgToThumb(clipfile.getInputStream(), 600, 600);
					FileOutputStream thumbStreamMiddle = new FileOutputStream(new File(fileUrl + THUMB_MIDDLE));
					thumbStreamMiddle.write(thumb);
					thumbStreamMiddle.flush();
					thumbStreamMiddle.close();
				}
				entity.setIsImage(isImage);
				entity.setAttachmentId(attachmentId);
				entity.setFileName(file.getOriginalFilename());
				entity.setFilePath(relatePath);
				entity.setFileSize(newFile.length());
				entity.setSequence(i);
				entity.setType(file.getContentType());
				entity.setRemark(remark);
				if (notCheckStatus) {
					entity.setStatus("2");
				} else {
					entity.setStatus("1");
				}
				entity.setExpired(expired);
				entity.setPhysicsName(millisecond + "");
				entity = attachmentDao.save(entity);
				return entity;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();// 缩略图出错
		}
		return null;
	}

	/**
	 * 视频截图(生成视频文件的首帧为图片)
	 * 
	 * @author huiqiang.yan 2016-07-06 上午9:51
	 * @param veido_path
	 *            视频路径
	 * @param ffmpeg_path
	 *            转换工具路径
	 * @return
	 */
	private boolean processImg(String veido_path, String ffmpeg_path, String saveFilePath) {
		File file = new File(veido_path);
		if (!file.exists()) {
			System.err.println("路径[" + veido_path + "]对应的视频文件不存在!");
			return false;
		}
		List<String> commands = new java.util.ArrayList<String>();
		commands.add(ffmpeg_path);
		commands.add("-i");
		commands.add(veido_path);
		commands.add("-y");
		commands.add("-f");
		commands.add("image2");
		commands.add("-ss");
		commands.add("0");// 这个参数是设置截取视频多少秒时的画面
		// commands.add("-t");
		// commands.add("0.001");
		commands.add("-s");
		commands.add("150x150");
		commands.add(saveFilePath);
		try {
			ProcessBuilder builder = new ProcessBuilder();
			builder.command(commands);
			builder.start();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void download(String uid, String mode, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		Attachment entity = attachmentDao.getByUid(uid);
		if (entity == null) {
			response.setStatus(404);
		} else {
			InputStream instream = null;
			if ("showthumb".equalsIgnoreCase(mode)) {
				instream = loadAttatchStream(entity.getFilePath() + entity.getPhysicsName() + THUMB_SMALL);
			} else if ("showthumbMiddle".equalsIgnoreCase(mode)) {
				instream = loadAttatchStream(entity.getFilePath() + entity.getPhysicsName() + THUMB_MIDDLE);
			} else {
				instream = loadAttatchStream(entity.getFilePath() + entity.getPhysicsName());
			}
			if (instream == null) {
				response.setStatus(404);
				return;
			}
			String agent = request.getHeader("USER-AGENT");
			response.setContentType("multipart/form-data");
			if ("down".equalsIgnoreCase(mode)) {
				response.setContentType("application/x-msdownload;");
			} else if ("show".equalsIgnoreCase(mode)) {
				response.setContentType(entity.getType());
			}
			response.setCharacterEncoding("UTF-8");
			String responseFile = entity.getFileName();
			if (responseFile.indexOf(".") == -1) {
				if (entity.getIsImage()) {
					String type = "jpg";
					if (entity.getType().indexOf("jpeg") == -1) {
						type = entity.getType().split("/")[1];
					}
					responseFile = responseFile + "." + type;
				}
			}
			try {
				if (org.apache.commons.lang3.StringUtils.contains(agent, "MSIE")) {
					responseFile = URLEncoder.encode(responseFile, "UTF-8");
				} else if (org.apache.commons.lang3.StringUtils.contains(agent, "Mozilla")) {
					responseFile = new String(responseFile.getBytes(), "ISO8859-1");
				} else {
					responseFile = URLEncoder.encode(responseFile, "UTF-8");
				}
			} catch (UnsupportedEncodingException e) {

			}
			response.setHeader("Content-Disposition", "attachment;fileName=\"" + responseFile + "\"");
			ServletOutputStream servletStream = response.getOutputStream();
			byte[] buf = new byte[instream.available()];
			instream.read(buf);
			servletStream.write(buf);
			servletStream.flush();
			servletStream.close();
		}

	}

	@Override
	public Message share(String uid) {
		Message msg = new Message();
		Attachment entity = attachmentDao.getByUid(uid);
		if (entity == null) {
			msg.setSuccess(false);
		} else {
			InputStream inputStream = loadAttatchStream(entity.getFilePath() + entity.getPhysicsName() + THUMB_MIDDLE);
			if (inputStream == null) {
				msg.setSuccess(false);
				return msg;
			}
			OutputStream outputStream = null;
			try {
				String format = new SimpleDateFormat("yyyyMMdd").format(new Date());
				String relatePath = String.format(ATTATCH_ROOT_PATH_FORMAT, "download/" + format);
				String type = ".jpg";
				if (entity.getFileName().indexOf(".") == -1) {
					if (entity.getType().indexOf("jpeg") == -1) {
						type = "." + entity.getType().split("/")[1];
					}
				}
				msg.setData(relatePath + "/" + entity.getFileName() + type);
				String fileUrl = prepareOutFilePath(relatePath).getPath() + "/" + entity.getFileName() + type;
				outputStream = new FileOutputStream(fileUrl);
				int len = inputStream.available();
				if (len <= 1024 * 1024) {
					byte[] bytes = new byte[len];
					inputStream.read(bytes);
					outputStream.write(bytes);
				} else {
					int byteCount = 0;
					byte[] bytes = new byte[1024 * 1024];
					while ((byteCount = inputStream.read(bytes)) != -1) {
						outputStream.write(bytes, 0, byteCount);
					}
				}
				outputStream.flush();
				inputStream.close();
				outputStream.close();
				return msg;
			} catch (Exception e) {
				e.printStackTrace();
				return msg;
			}
		}
		return null;
	}

	public String packageDownload(String entityUid, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		String outzipfile = "";
		String path = "";
		String remoteUrl = new YESConfig().getRemoteUrl();
		String temppath = getTempPath();
		String remotetempl = temppath + File.separator + "templ"; // 临时目录
		boolean flag = YESUtil.isEmpty(remoteUrl);
		File file = new File(temppath);
		if (!file.exists()) {
			file.mkdirs();
		}
		if (!flag) {
			file = new File(remotetempl);
			file.mkdirs();
		}
		List<Attachment> list = this.attachmentDao.getByEntityUid(entityUid);
		if (YESUtil.isNotEmpty(list)) {
			path = YESUtil.isEmpty(outzipfile) || outzipfile.startsWith("smb://")
					? temppath + File.separator + entityUid + ".zip" : outzipfile;
			File _file = new File(path);
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(_file));
			for (Attachment attachment : list) {
				try {
					InputStream fis = loadAttatchStream(attachment.getFilePath() + attachment.getPhysicsName());
					byte[] buffer = new byte[fis.available()];
					out.putNextEntry(new ZipEntry(attachment.getFileName()));
					int len = 0;
					while ((len = fis.read(buffer)) > 0) {
						out.write(buffer, 0, len);
					}
					out.closeEntry();
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			out.close();
			this.downFile(response, path, entityUid);
			try {
				FileUtils.deleteDirectory(file);
			} catch (IOException e) {
			}
			if (outzipfile != null && outzipfile.startsWith("smb://")) {
				try {
					YESUtil.saveRemote(_file, outzipfile);
					return outzipfile;
				} catch (SmbException e) {
					e.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				return "";
			}
		}
		return null;
	}

	private void downFile(HttpServletResponse response, String path, String entityUid) {
		try {
			File file = new File(path);
			if (file.exists()) {
				InputStream ins = new FileInputStream(path);
				BufferedInputStream bins = new BufferedInputStream(ins);
				OutputStream outs = response.getOutputStream();
				BufferedOutputStream bouts = new BufferedOutputStream(outs);
				response.setContentType("application/x-download");
				response.setHeader("Content-disposition",
						"attachment;filename=" + URLEncoder.encode(entityUid + ".zip", "UTF-8"));
				int bytesRead = 0;
				byte[] buffer = new byte[8192];
				while ((bytesRead = bins.read(buffer, 0, 8192)) != -1) {
					bouts.write(buffer, 0, bytesRead);
				}
				bouts.flush();
				ins.close();
				bins.close();
				outs.close();
				bouts.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * @Transactional public void delete(String uids) { String[] uidls =
	 * uids.split(","); Boolean pd = null; Attachment entity = null; for (String
	 * uid : uidls) { entity = attachmentDao.getByUid(uid); if (entity != null)
	 * { pd = attachmentDao.delete(entity); if (pd) {// 删除成功} } } } }
	 */

	@Transactional
	@Override
	public boolean deleteByUids(String uids) {
		String[] uidls = uids.split(",");
		Attachment entity = null;
		for (String uid : uidls) {
			entity = attachmentDao.getByUid(uid);
			if (entity != null) {
				String fileurl = YESUtil.getuploadPath("/") + entity.getFilePath() + entity.getPhysicsName();
				File file = new File(fileurl);
				if (file.exists())
					file.delete();
				if (YESUtil.objToBoolean(entity.getIsImage())) {
					File thumb = new File(fileurl + THUMB_SMALL);
					if (thumb.exists())
						thumb.delete();
					thumb = new File(fileurl + THUMB_MIDDLE);
					if (thumb.exists())
						thumb.delete();
				}
				attachmentDao.delete(entity);
			}
		}
		return true;
	}

	public Attachment getByUid(String uid) {
		return attachmentDao.getByUid(uid);
	}

	/**
	 * 得到根目录+参数relatePath的目录,并检查目录是否存在,如不存在则创建
	 * 
	 * @author 颜惠强 2015-08-14
	 * @param relatePath
	 *            根目录后的路径
	 * @return
	 */
	private File prepareOutFilePath(String relatePath) {
		String realPath = YESUtil.getuploadPath("/");
		File file = new File(realPath + relatePath);
		if (!file.exists()) {
			file.mkdirs();
		}
		return file;
	}

	/**
	 * 获取上传文件后缀名
	 * 
	 * @author huiqiang.yan 2015-08-14
	 * @param filename
	 *            文件名
	 * @return
	 */
	public static String getExtensionName(String filename) {
		if ((filename != null) && (filename.length() > 0)) {
			int dot = filename.lastIndexOf('.');
			if ((dot > -1) && (dot < (filename.length() - 1))) {
				return filename.substring(dot + 1);
			}
		}
		return filename;
	}

	/**
	 * 获得文件的InputStram
	 * 
	 * @author 颜惠强 2015.08.14
	 * @param filename
	 *            工程地址下的 目录/文件名
	 * @return
	 */
	private InputStream loadAttatchStream(String filename) {
		File file = new File(YESUtil.getuploadPath("/") + filename);
		try {
			FileInputStream inStream = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inStream.read(buffer);
			inStream.close();
			InputStream stream = new ByteArrayInputStream(buffer);
			return stream;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getTempPath() {
		return YESUtil.getuploadPath("WEB-INF/base/tempattach/" + YESUtil.session().getId() + "/");
	}

	@Transactional
	public Message expiredClean() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String now = sdf.format(new Date());
		List<Attachment> list = attachmentDao.getAttachmentsByDate(now);
		int success = 0, error = 0;
		if (YESUtil.isNotEmpty(list)) {
			for (Attachment attachment : list) {
				boolean result = attachmentDao.delete(attachment);
				if (result) {
					success++;
					String path = prepareOutFilePath(attachment.getFilePath()).getPath();
					FileUtils.deleteQuietly(new File(path + File.separator + attachment.getPhysicsName()));
				} else {
					error++;
				}
			}
		}
		String message = "过期文件共" + list.size() + "个，成功清除" + success + "个，失败" + error + "个。";
		Message msg = new Message(message);
		return msg;
	}

	private boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}

	/**
	 * 计算得到过期时间
	 * 
	 * @author huiqiang.yan 2015-08-19
	 * @param expired
	 * @return
	 */
	public Date getExpired(String expired) {
		try {
			if (BaseUtil.isEmpty(expired)) {
				expired = "1440";
			}
			if (isNum(expired)) {
				Long temp = YESUtil.objtolong(expired);
				long currentTime = System.currentTimeMillis();
				currentTime += temp * 60 * 1000;
				Date date = new Date(currentTime);
				return date;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}

	@Transactional
	public boolean updateBinds(Map<String, Object> parm) {
		String attachmentId = YESUtil.toString(parm.get("attachmentId"));
		System.out.println("attachmentId===" + attachmentId);
		if (YESUtil.isEmpty(attachmentId))
			return false;
		String[] atts = attachmentId.split(",");
		for (String attid : atts) {
			List<Attachment> list = attachmentDao.getByAttachmentId(attid);
			if (YESUtil.isNotEmpty(list)) {
				String entityName = YESUtil.toString(parm.get("entityName"));
				String entityUid = YESUtil.toString(parm.get("entityUid"));
				String fieldName = YESUtil.toString(parm.get("fieldName"));
				for (Attachment attachment : list) {
					attachment.setEntityName(entityName);
					attachment.setEntityUid(entityUid);
					attachment.setFieldName(fieldName);
					attachment.setStatus("2");
					attachmentDao.save(attachment);
				}
			}
		}
		return true;
	}

	@Transactional
	public Message unBindsClean() {
		List<Attachment> list = attachmentDao.getAttachments();
		int success = 0, error = 0;
		if (YESUtil.isNotEmpty(list)) {
			for (Attachment attachment : list) {
				boolean result = attachmentDao.delete(attachment);
				if (result) {
					success++;
					String path = prepareOutFilePath(attachment.getFilePath()).getPath();
					FileUtils.deleteQuietly(new File(path + File.separator + attachment.getPhysicsName()));
				} else {
					error++;
				}
			}
		}
		String message = "未绑定文件共" + list.size() + "个，成功清除" + success + "个，失败" + error + "个。";
		Message msg = new Message(message);
		return msg;
	}

	@Transactional
	public boolean update(Map<String, Object> parm) {
		String uid = YESUtil.toString(parm.get("uid"));
		String temp = YESUtil.toString(parm.get("expired"));
		String updated = YESUtil.toString(parm.get("updated"));
		if (YESUtil.isEmpty(uid))
			return false;
		Attachment attachment = attachmentDao.getByUid(uid);
		if (attachment != null) {
			Date expired = getExpired(temp);
			Date date = null;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if (YESUtil.isNotEmpty(expired) && YESUtil.isEmpty(updated)) {
				try {
					String time = formatter.format(expired);
					date = formatter.parse(time);
				} catch (Exception e) {
				}
			} else if (YESUtil.isEmpty(expired) && YESUtil.isNotEmpty(updated)) {
				try {
					date = formatter.parse(updated);
				} catch (Exception e) {
				}
			} else if (YESUtil.isNotEmpty(expired) && YESUtil.isNotEmpty(updated)) {
				try {
					String time = formatter.format(expired);
					date = formatter.parse(time);
				} catch (Exception e) {
				}
			} else {
				date = attachment.getExpired();
			}
			if (attachment.getUid().equals(uid)) {
				attachment.setExpired(date);
				attachmentDao.save(attachment);
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@Override
	public List<Attachment> searchByAttId(String attachmentId) {
		return attachmentDao.searchByAttId(attachmentId);
	}

	@Override
	@Transactional
	public boolean deleteByAttId(String attId) {
		return attachmentDao.deleteByAttId(attId);
	}

	@Override
	public boolean getVideoImage(String videoUrl) {
		String savePath = videoUrl.substring(0, videoUrl.indexOf("."));
		return processImg(YESUtil.getuploadPath("/") + videoUrl, YESUtil.getuploadPath("/") + "ffmpeg.exe",
				YESUtil.getuploadPath("/") + savePath + ".jpg");
	}

	@Override
	public List<Attachment> getByAttIdByStatus(String attId, String status) {
		return attachmentDao.getByAttIdByStatus(attId, status);
	}

	@Override
	public String getUrlFromAttId(String attId) {
		if (YESUtil.isEmpty(attId))
			return null;
		String url = attachmentDao.getUrlFromAttId(attId);
		if (YESUtil.isEmpty(url))
			return url;
		return url;
	}

	@Override
	public List<NameValue> listUrlFromAttId(List<String> attIds) {
		if (YESUtil.isEmpty(attIds))
			return null;
		return attachmentDao.listUrlFromAttId(attIds);
	}

}
