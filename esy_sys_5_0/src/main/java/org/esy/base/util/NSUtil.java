/*
 * Copyright (c) , Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.util;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.FormatSettings;
import org.esy.base.core.Message;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import net.coobird.thumbnailator.Thumbnails;

/**
 * <pre>
 * ClssName: NSUtil 
 * Description: 处理工具类
 * </pre>
 *
 * @author huiqiang.yan
 * @since 1.8
 * @version $ Id:NSUtil.java 1.0 2017年5月11日上午9:48:59 $
 */
public class NSUtil {

	private static final String CONTENT_HEADER_DISPOSITION = "Content-Disposition";

	private static final String CONTENT_HEADER_ATTACHMENT = "attachment; filename=\"%s\";";

	/**
	 * Description:字符串第一个字母转小写
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:50:59
	 * @param str
	 * @return
	 */
	public static String converStrFirstLowerCase(String str) {
		if (BaseUtil.isEmpty(str)) {
			return "";
		}
		byte[] items = str.getBytes();
		char c = (char) items[0];
		if (c > 90 || c < 65) {
			return str;
		}
		items[0] = (byte) (c + 32);
		return new String(items);
	}

	/**
	 * Description:得到格式化后的时间,如果date为空返回空字符串
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:52:17
	 * @param date
	 * @param sim
	 * @return
	 */
	public static String getDateString(Date date, SimpleDateFormat sim) {
		if (date != null)
			return sim.format(date);
		else
			return "";
	}

	/**
	 * Description:导出excel
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:56:35
	 * @param title
	 * @param valls
	 * @param excelName
	 * @param type
	 * @param formatSettings
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String exportExcel(Map<String, Object> title, List valls, String excelName, String type,
			FormatSettings formatSettings, HttpServletResponse response) {
		if (BaseUtil.isEmpty(excelName)) {
			excelName = "excel";
		}
		if (BaseUtil.isEmpty(type) || ((!type.equals("xls")) && (!type.equals("xlsx")))) {
			type = "xlsx";
		}
		NSExcelUtil yesexcel = new NSExcelUtil();
		OutputStream os = null;
		ByteArrayOutputStream bos = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
			response.reset();
			response.setContentType("xls".equals(type) ? "application/vnd.ms-excel"
					: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(CONTENT_HEADER_DISPOSITION, String.format(CONTENT_HEADER_ATTACHMENT,
					NSUtil.encodeChineseDownloadFileName(BaseUtil.toString(excelName) + "." + type)));
			bos = yesexcel.writeExcel(excelName, title, valls, "xlsx".equals(type), formatSettings);
			os.write(bos.toByteArray());
			os.close();
			return null;
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	/**
	 * Description:导出excel
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:56:35
	 * @param title
	 * @param valls
	 * @param excelName
	 * @param type
	 * @param formatSettings
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String exportGExcel(LinkedHashMap<String, Object> title, List valls, String excelName, String type,
			FormatSettings formatSettings, HttpServletResponse response) {
		if (BaseUtil.isEmpty(excelName)) {
			excelName = "excel";
		}
		if (BaseUtil.isEmpty(type) || ((!type.equals("xls")) && (!type.equals("xlsx")))) {
			type = "xls";
		}
		NSExcelUtil yesexcel = new NSExcelUtil();
		OutputStream os = null;
		ByteArrayOutputStream bos = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
			response.reset();
			response.setContentType("xls".equals(type) ? "application/vnd.ms-excel"
					: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(CONTENT_HEADER_DISPOSITION, String.format(CONTENT_HEADER_ATTACHMENT,
					NSUtil.encodeChineseDownloadFileName(BaseUtil.toString(excelName) + "." + type)));
			bos = yesexcel.writeGExcel(excelName, title, valls, "xlsx".equals(type), formatSettings);
			os.write(bos.toByteArray());
			os.close();
			return null;
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	/**
	 * Description:对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:58:48
	 * @param pFileName
	 * @return
	 */
	public static String encodeChineseDownloadFileName(String pFileName) {
		String agent = request().getHeader("USER-AGENT");
		try {
			if (null != agent && (-1 != agent.indexOf("MSIE") || -1 != agent.indexOf("like Gecko"))) {
				pFileName = URLEncoder.encode(pFileName, "utf-8");
			} else {
				pFileName = new String(pFileName.getBytes("utf-8"), "iso8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return pFileName;
	}

	/**
	 * Description:http 请求对象(request)
	 * 
	 * @author huiqiang.yan 2017年5月11日上午9:59:21
	 * @return
	 */
	public static HttpServletRequest request() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * Description:导入excel
	 * 
	 * @author huiqiang.yan 2017年5月11日上午11:52:16
	 * @param excel
	 * @return Message.success:状态,Message.message:错误提示,Message.data:结果集合
	 */
	public static Message importExcel(MultipartFile excel) {
		Message message = new Message();
		String name = excel.getOriginalFilename().toLowerCase();
		if (name.endsWith("xlsx") || name.endsWith("xls")) {
			NSExcelUtil yesexcel = new NSExcelUtil();
			try {
				List<Map<String, Object>> valls = yesexcel.readExcel(excel.getInputStream(),
						excel.getOriginalFilename().toLowerCase().endsWith("xlsx"));
				message.setData(valls);
			} catch (Exception e) {
				e.printStackTrace();
				message.setSuccess(false);
				message.setMessage(e.getMessage());
			}
		} else {
			message.setSuccess(false);
			message.setMessage("请上传正确的excel模版!");
		}
		return message;
	}

	public static String arrayToString(String[] array) {
		String value = "";
		for (String temp : array) {
			value += BaseUtil.isEmpty(value) ? NSUtil.getQuotedstr(temp) : "," + NSUtil.getQuotedstr(temp);
		}
		return value;
	}

	public static String getQuotedstr(String str) {
		return String.format("'%s'", str);
	}

	/**
	 * http 会话对象(session)
	 * 
	 * @return HttpSession
	 */
	public static HttpSession session() {
		try {
			return request().getSession();
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 服务器绝对路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getRealPath(String path) {
		return session().getServletContext().getRealPath(path);
	}

	public static byte[] imgToThumb(InputStream img, int width, int height) throws Exception {
		byte[] bytes = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Thumbnails.of(img).size(width, height).toOutputStream(baos);
		bytes = baos.toByteArray();
		img.close();
		baos.close();
		return bytes;
	}
}
