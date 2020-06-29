package org.esy.base.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.esy.base.util.YESUtil;

/**
 * <pre>
 * ClssName: ByteArrayUtil 
 * Description:  byte数组转换工具类
 * </pre>
 *
 * @author cx
 * @since 1.7
 * @version $ Id:BeanshellUtil.java 1.0 2016年10月9日 上午9:47:16  $
 */
public class ByteArrayUtil {

	private static Logger logger = Logger.getLogger(ByteArrayUtil.class);

	/**
	 * 
	 * inputStream 转 byte
	 * @author cx 2016年10月9日 上午10:43:34
	 * @param is
	 * @return
	 */
	public static byte[] InputStreamToByte(InputStream is) {
		byte[] bytes = null;
		try {
			bytes = IOUtils.toByteArray(is);
		} catch (IOException e) {
			logger.error("inputStream to byte error", e);
		}
		return bytes;
	}

	/**
	 * 
	 * byte转InputStream
	 * @author cx 2016年10月9日 上午10:16:55
	 * @param in
	 * @return
	 * @throws Exception
	 */
	public static InputStream byteToInputStream(byte[] in) {
		if (in == null || in.length < 1) {
			return null;
		}
		return new ByteArrayInputStream(in);
	}

	/**
	 * 
	 * 写入OutputStream
	 * @author cx 2016年10月9日 上午10:52:17
	 * @param in
	 * @param outputStream
	 */
	public static void writeOutputStream(byte[] in, OutputStream outputStream) {
		try {
			IOUtils.write(in, outputStream);
		} catch (IOException e) {
			logger.error("written to the outputStream error", e);
		}
	}

	/**
	 * 
	 * 写入文件
	 * @author cx 2016年10月9日 上午10:49:49
	 * @param in
	 * @param fileName 文件名包路径
	 */
	public static void writeFile(byte[] in, String fileName) {
		try {
			FileUtils.writeByteArrayToFile(new File(fileName), in);
		} catch (IOException e) {
			logger.error("written to the file error", e);
		}
	}

	/**
	 * 
	 * 输出到客户端下载默认UTF-8
	 * @author cx 2016年10月9日 上午11:06:14
	 * @param fileName 文件名
	 * @param in
	 * @param response
	 */
	public static void toPageDown(String fileName, byte[] in, HttpServletResponse response) {
		toPageDown(fileName, "UTF-8", in, response);
	}

	/**
	 * 
	 * 输出到客户端下载
	 * @author cx 2016年10月9日 上午11:02:27
	 * @param fileName 文件名
	 * @param characterEncoding 输出格式("UTF-8","GB2312"....)
	 * @param in
	 * @param response
	 */
	public static void toPageDown(String fileName, String characterEncoding, byte[] in, HttpServletResponse response) {
		toOutputStream(fileName, "application/octet-stream;", characterEncoding, in, response);
	}

	/**
	 * 
	 * 输出到客户端打开文件默认UTF-8输出
	 * @author cx 2016年10月9日 上午11:18:31
	 * @param fileName 文件名
	 * @param contentType 文件类型("image/jpeg","application/x-png","application/msword","application/vnd.ms-excel","application/pdf"...)
	 * @param in
	 * @param response
	 */
	public static void toPageOpen(String fileName, String contentType, byte[] in, HttpServletResponse response) {
		toOutputStream(fileName, contentType, "UTF-8", in, response);
	}

	/**
	 * 
	 * 输出到客户端打开文件
	 * @author cx 2016年10月9日 上午11:18:31
	 * @param fileName 文件名
	 * @param contentType 文件类型("image/jpeg","application/x-png","application/msword","application/vnd.ms-excel","application/pdf"...)
	 * @param characterEncoding 输出格式("UTF-8","GB2312"....)
	 * @param in
	 * @param response
	 */
	public static void toPageOpen(String fileName, String contentType, String characterEncoding, byte[] in,
			HttpServletResponse response) {
		toOutputStream(fileName, contentType, characterEncoding, in, response);
	}

	/**
	 * 
	 * 输出到outputStream
	 * @author cx 2016年10月9日 上午11:23:22
	 * @param fileName 文件名
	 * @param contentType 文件类型
	 * @param characterEncoding 格式
	 * @param in
	 * @param response
	 */
	private static void toOutputStream(String fileName, String contentType, String characterEncoding, byte[] in,
			HttpServletResponse response) {
		response.setContentType(contentType);
		response.setCharacterEncoding(characterEncoding);
		response.setHeader("Content-Disposition",
				"attachment;fileName=" + YESUtil.encodeChineseDownloadFileName(fileName));
		try {
			writeOutputStream(in, response.getOutputStream());
		} catch (IOException e) {
			logger.error("download or open file error", e);
		}
	}

}
