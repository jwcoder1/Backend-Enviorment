package org.esy.base.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.esy.base.common.BaseUtil;
import org.esy.base.entity.ReportEntity;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileOutputStream;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.data.JsonDataSource;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Pdf 处理类
 * 
 * @author duiqing.huang
 *
 */
public class YESPdfUtil {

	private static Map<String, String> _paths = new HashMap<String, String>();

	public static void exportReportPdf(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> para) {

		OutputStream outputStream = null;

		JasperReport jasperReport = null;

		String reportname = YESUtil.toString(para.get("reportname"));
		String dataurl = YESUtil.toString(para.get("dataurl"));
		boolean isDialog = true;
		if (BaseUtil.isNotEmpty(para.get("isDialog"))) {
			isDialog = YESUtil.objToBoolean(para.get("isDialog"));
		}

		if (BaseUtil.isEmpty(reportname + dataurl)) {
		}

		String urlpara = "";
		for (String o : para.keySet()) {
			if (!o.equals("reportname") && !o.equals("dataurl")) {
				urlpara = urlpara + (BaseUtil.isNotEmpty(urlpara) ? "&" : "") + o + "=" + para.get(o);
			}
		}
		dataurl = dataurl + (BaseUtil.isNotEmpty(urlpara) ? "?" : "") + urlpara;

		String _tmplPath = getPath(reportname);

		try {
			outputStream = response.getOutputStream();
			response.reset();

			jasperReport = (JasperReport) (_tmplPath.endsWith(".jasper") ? JRLoader.loadObjectFromFile(_tmplPath)
					: JasperCompileManager.compileReport(_tmplPath));

			JsonDataSource jRDataSource = new JsonDataSource(dataurl, "");
			// Subject currentUser = SecurityUtils.getSubject();
			// String jsonstr = HttpUtil.getByUserAndParm(dataurl, para, "",
			// "");

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, jRDataSource);

			if (isDialog) {
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				outputStream.close();
			} else {
				JasperPrintManager.printReport(jasperPrint, false);
				response.setContentType("application/octet-stream");
				ObjectOutputStream oos = new ObjectOutputStream(outputStream);
				oos.writeObject(jasperPrint);
				oos.flush();
				oos.close();
				outputStream.flush();
				outputStream.close();
			}

		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	public static List<?> exportReportPdf(HttpServletRequest request, HttpServletResponse response,
			ReportEntity reportEntity) {

		boolean isGetData = true;
		if (BaseUtil.isNotEmpty(reportEntity.getParam().get("isGetData"))) {
			isGetData = YESUtil.objToBoolean(reportEntity.getParam().get("isGetData"));
		}
		if (isGetData) {
			return reportEntity.getList();
		}

		OutputStream outputStream = null;

		JasperReport jasperReport = null;

		String reportname = YESUtil.toString(reportEntity.getParam().get("reportname"));
		boolean isDialog = true;
		if (BaseUtil.isNotEmpty(reportEntity.getParam().get("isDialog"))) {
			isDialog = YESUtil.objToBoolean(reportEntity.getParam().get("isDialog"));
		}

		String _tmplPath = request.getSession().getServletContext()
				.getRealPath("/WEB-INF/classes/template/" + reportname + ".jasper");

		try {
			outputStream = response.getOutputStream();
			response.reset();

			jasperReport = (JasperReport) (_tmplPath.endsWith(".jasper") ? JRLoader.loadObjectFromFile(_tmplPath)
					: JasperCompileManager.compileReport(_tmplPath));

			JRBeanCollectionDataSource jRDataSource = new JRBeanCollectionDataSource(reportEntity.getList(), false);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportEntity.getParam(), jRDataSource);

			if (isDialog) {
				response.setContentType("application/pdf");
				JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
				outputStream.close();
			} else {
				JasperPrintManager.printReport(jasperPrint, false);
				response.setContentType("application/octet-stream");
				ObjectOutputStream oos = new ObjectOutputStream(outputStream);
				oos.writeObject(jasperPrint);
				oos.flush();
				oos.close();
				outputStream.flush();
				outputStream.close();
			}

		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static void exportReportPdf(HttpServletResponse response, String reportname, Object reportData,
			Map<String, Object> reportParam) {

		OutputStream outputStream = null;

		JasperReport jasperReport = null;

		String _tmplPath = getPath(reportname);

		try {
			outputStream = response.getOutputStream();
			response.reset();
			response.setContentType("application/pdf");
			jasperReport = (JasperReport) (_tmplPath.endsWith(".jasper") ? JRLoader.loadObjectFromFile(_tmplPath)
					: JasperCompileManager.compileReport(_tmplPath));
			String json = org.esy.base.util.JsonUtil.beanToJson(reportData);
			InputStream stream = new ByteArrayInputStream(json.getBytes("UTF-8"));
			InputStream instream = null;
			JsonDataSource jRDataSource = new JsonDataSource(stream);
			// JRBeanCollectionDataSource jRDataSource = new
			// JRBeanCollectionDataSource(reportData, false);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, reportParam, jRDataSource);

			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			outputStream.close();
		} catch (JRException | IOException e) {
			e.printStackTrace();
		}
	}

	private static List<?> getReportData(String orgPack, String methodname, Map<String, Object> dataParam) {
		// TODO Auto-generated method stub
		Object object = null;

		try {
			// org.esy.vehicle.service.impl.RepVehicleRecordServiceImpl
			ClassLoader loader = Thread.currentThread().getContextClassLoader();
			WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
			Class<?> classType = loader.loadClass("org.esy.vehicle.service.impl.RepVehicleRecordServiceImpl");// 加载指定类，注意一定要带上类的包名
			// RepVehicleRecordServiceImpl service =
			// (RepVehicleRecordServiceImpl);

			Class<?> t_class = wac.getBean("taskService").getClass();
			Method method = classType.getDeclaredMethod("getyearVehicle", Map.class);
			method.invoke(wac.getBean("taskService"), dataParam);

			// ClassLoader loader =
			// Thread.currentThread().getContextClassLoader();
			//
			// Class<?> t_class = loader.loadClass(orgPack);//加载指定类，注意一定要带上类的包名
			//
			// Method method = t_class.getDeclaredMethod(methodname, Map.class);
			// object = method.invoke(t_class.newInstance(), dataParam);// 方法的实现
		} catch (Exception e) {
			e.printStackTrace();
		}
		return (List<?>) object;
	}

	public static void exportReportPdf(OutputStream outputStream, Map<String, Object> parameters, List<?> list,
			String tmplPath) {
		JasperReport jasperReport = null;
		String _tmplPath = getPath(tmplPath);
		try {
			jasperReport = (JasperReport) (_tmplPath.endsWith(".jasper") ? JRLoader.loadObjectFromFile(_tmplPath)
					: JasperCompileManager.compileReport(_tmplPath));
			JRBeanCollectionDataSource jRDataSource = new JRBeanCollectionDataSource(list, false);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, jRDataSource);
			/*
			 * JRPdfExporter exporter = new JRPdfExporter();
			 * //exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING,
			 * "utf-8"); exporter.setParameter(JRExporterParameter.JASPER_PRINT,
			 * jasperPrint);
			 * exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
			 * outputStream); exporter.exportReport();
			 */
			JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
			// outputStream.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出pdf到指定的目录
	 * 路径参考:http://jcifs.samba.org/src/docs/api/jcifs/smb/SmbFile.html
	 * 
	 * @param outPath
	 *            共享目录 如：administrator:123456@172.16.10.136/file
	 * @param fileName
	 *            导出的文件名
	 * @param tmplPath
	 *            Pdf 模板
	 * @param parameters
	 *            模板中的静态参数
	 * @param list
	 *            模板报表数据
	 * @return 状态(success--成功,notdirectory--没找到目录,errorurl--地址格式错,errorsmb--验证出错
	 *         ,unknownhost--未知服务器错)
	 */
	public static String exportReprotPdf(String outPath, String fileName, String tmplPath,
			Map<String, Object> parameters, List<?> list) {
		String status = "success";
		outPath = "smb://" + outPath;
		try {
			SmbFile file = new SmbFile(outPath);
			if (!file.exists()) {
				file.mkdirs();
				// return "notdirectory";
			}
			SmbFileOutputStream smbfileoutput = new SmbFileOutputStream(outPath + "/" + fileName + ".pdf", false);
			exportReportPdf(smbfileoutput, parameters, list, tmplPath);
		} catch (MalformedURLException e) { // 地址格式错
			e.printStackTrace();
			return "errorurl";
		} catch (SmbException e) { // 验证出错
			e.printStackTrace();
			return "errorsmb";
		} catch (UnknownHostException e) { // 未知服务器错
			e.printStackTrace();
			return "unknownhost";
		}
		return status;
	}

	private static String getPath(String filename) {

		String _path = _paths.get(filename);
		if (_path == null) {
			_path = YESUtil.session().getServletContext()
					.getRealPath("/WEB-INF/resources/plugins/pdf/" + filename + ".jasper");
			_path = new File(_path).exists() ? _path
					: YESUtil.session().getServletContext()
							.getRealPath("/WEB-INF/resources/plugins/pdf/" + filename + ".jrxml");
			_paths.put(filename, _path);
		}
		return _path;
	}

}
