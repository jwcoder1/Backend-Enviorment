package org.esy.base.util;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.TreeMap;
import java.util.UUID;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanMap;
import org.apache.commons.io.FileUtils;
import org.esy.base.common.BaseUtil;
import org.esy.base.core.FormatSettings;
import org.esy.base.core.Message;
import org.joda.time.DateTime;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.util.ClassUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

import jcifs.smb.SmbException;
import jcifs.smb.SmbFile;
import jcifs.smb.SmbFileInputStream;
import jcifs.smb.SmbFileOutputStream;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class YESUtil {

	public static final String NOTALLOWYBLANK = "_NOTALLOWYBLANK";

	/*
	 * private static char[] hexChar = { '0', '1', '2', '3', '4', '5', '6', '7',
	 * '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
	 */
	private static byte[] cryptKey = { '4', '@', '(', '3', '0', '9', '?', 'Z', '-', '#', 'O', '{', '}', '"', '$', '|',
			'Q', '5', ',', '=' };

	private static final String CONTENT_HEADER_DISPOSITION = "Content-Disposition";

	private static final String CONTENT_HEADER_ATTACHMENT = "attachment; filename=\"%s\";";

	public static Map<Class, TreeMap<String, Object>> allquerys = null;

	static {
		ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
		Map<Class, String> result = new HashMap<Class, String>();
		try {
			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath("org.esy.*.entity.query") + "/" + "*.json";
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (int i = 0; i < resources.length; i++) {
				Resource resource = resources[i];
				Class entity = Class.forName(
						resource.getURL().getPath().split("!/")[1].replace("query/" + resource.getFilename(), "")
								.replace("/", ".") + resource.getFilename().replace(".json", ""));
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream()));
				String trs = "", rs = "";
				while ((trs = br.readLine()) != null) {
					rs += trs;
				}
				result.put(entity, rs);
				br.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		allquerys = new HashMap<Class, TreeMap<String, Object>>();
		for (Class key : result.keySet()) { // 所有类
			TreeMap<String, Object> querys = new TreeMap<String, Object>();
			String jsonval = result.get(key);
			JSONArray jr = JSONArray.fromObject(jsonval);
			for (int i = 0, size = jr.size(); i < size; i++) { // 所有字段
				JSONObject jo = jr.getJSONObject(i);
				for (Object obj : jo.keySet()) {// 单个字段
					String colName = obj.toString();
					if ("dtoMap".equals(colName)) {
						querys.put("dtoMap", jo.getJSONObject(colName));
					} else {
						Object javatype = null;
						try {
							Class tclass = key.getDeclaredField(colName).getType();
							switch (tclass.getName().replace("java.lang.", "").toLowerCase()) {
							case "int":
								javatype = 0;
								break;
							case "integer":
								javatype = 0;
								break;
							case "double":
								javatype = 0.0;
								break;
							case "float":
								javatype = Float.parseFloat("0");
								break;
							default:
								javatype = tclass.newInstance();
								break;
							}
						} catch (NoSuchFieldException e) {
						} catch (InstantiationException e) {
						} catch (IllegalAccessException e) {
						} catch (SecurityException e) {
						}
						if (javatype == null) {
							System.out.println("通用查询配置(" + key.getName().replace("entity.", "entity.query")
									+ ")json配置格式错误[字段名{" + colName + "}不存在]");
							continue;
						}
						Object query = jo.get(colName);
						if (query instanceof String) {// 只有一个条件
							querys.put(colName + "$" + query, javatype);
						} else if (query instanceof JSONArray) {// 条件数组
							JSONArray qrys = (JSONArray) query;
							for (int j = 0, qsize = qrys.size(); j < qsize; j++) {// 所有条件
								querys.put(colName + "$" + qrys.getString(j), javatype);
							}
						} else {// 非法
							System.out.println("通用查询配置(" + key.getName().replace("entity.", "entity.query")
									+ ")json配置格式错误[{" + colName + "}的查询条件配置错误]");
						}
					}
				}
			}
			allquerys.put(key, querys);
		}
		System.out.println("初始化查询条件完成");
	}

	public static String jiexi(String key, String sVal, Object type, Map<String, Object> hqlmap) {
		String cz = "eq", likestr = "";
		String[] keys = key.split("\\$");
		String colName = keys[0];
		if (keys.length > 1) {
			cz = keys[1];
		}
		switch (cz) {
		case "eq":
			cz = "=";
			break;
		case "gt":
			cz = ">";
			break;
		case "gte":
			cz = ">=";
			break;
		case "lt":
			cz = "<";
			break;
		case "lte":
			cz = "<=";
			break;
		case "neq":
			cz = "!=";
			break;
		case "sw":
			cz = " like ";
			likestr = "val%";
			break;
		case "ew":
			cz = " like ";
			likestr = "%val";
			break;
		case "match":
			cz = " like ";
			likestr = "%val%";
			break;
		}
		Object val = changeType(type, sVal);
		if (!likestr.equals("")) {// 是like
			val = likestr.replace("val", val.toString());
		}
		String rs = colName + cz + ":" + key;
		hqlmap.put(key, val);
		return rs;
	}

	public static Object changeType(Object type, String sVal) {
		Object rs = null;
		if (type instanceof String) {
			rs = sVal;
		} else if (type instanceof Boolean) {
			rs = YESUtil.objToBoolean(sVal);
		} else if (type instanceof Double) {
			rs = YESUtil.objToDouble(sVal);
		} else if (type instanceof Integer) {
			rs = YESUtil.objtoint(sVal);
		} else if (type instanceof Date) {
			rs = getDateTimeByString(sVal);
		} else if (type instanceof DateTime) {
			rs = new DateTime(getDateTimeByString(sVal).getTime());
		}
		return rs;
	}

	/**
	 * 同步xml
	 * 
	 * @param content
	 *            xml的文件内容
	 * @return
	 */
	public static boolean createSyncXml(String content) {
		try {
			String filename = getDateString(new Date(), new SimpleDateFormat("yyyy.MM.dd HH.mm.ssSSS")) + ".xml";
			return createTxt(new PropertiesUtils().getProperty("xmlSyncFileUrl") + "/" + filename, content);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 同步地址
	 * 
	 * @param url
	 *            post地址
	 * @param sendvalue
	 *            发送的值
	 * @param canshuName
	 *            发送的值的参数名
	 * @return
	 */
	public static boolean createSyncTxt(String url, String sendvalue, String canshuName) {
		String txtsyncurl = "";
		try {
			PropertiesUtils p = new PropertiesUtils();
			txtsyncurl = p.getProperty("txtsyncurl");
			return createSyncTxt(url, sendvalue, canshuName, txtsyncurl);
		} catch (IOException e) {
			return false;
		}
	}

	/**
	 * 同步地址
	 * 
	 * @param url
	 *            post地址
	 * @param sendvalue
	 *            发送的值
	 * @param canshuName
	 *            发送的值的参数名
	 * @param filepath
	 *            同步文件夹地址
	 * @return
	 */
	public static boolean createSyncTxt(String url, String sendvalue, String canshuName, String filepath) {
		Map<String, String> rootmap = new HashMap<String, String>();
		rootmap.put("url", url);
		rootmap.put("sendvalue", sendvalue);
		rootmap.put("canshuName", canshuName);
		String root = JSONObject.fromObject(rootmap).toString();
		String filename = getDateString(new Date(), new SimpleDateFormat("yyyy.MM.dd HH.mm.ssSSS")) + ".txt";
		return createTxt(filepath + "/" + filename, root);
	}

	/**
	 * valmap.put("tlinkman",
	 * "联系人");比对newobj和oldobj的tlinkman,如果不相等得到字符串[联系人]由*更改为*2\n 可比对类型:int double
	 * string date
	 * 
	 * @author 蔡琼伟 2014.12.18
	 * @param valmap
	 * @param newobj
	 *            新对象
	 * @param oldobj
	 *            旧对象
	 * @param sim
	 *            默认 yyyy-MM-dd HH:mm:ss 比对date类型时显示的格式
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String bidui(Map<String, String> valmap, Object newobj, Object oldobj, SimpleDateFormat sim) {
		if (sim == null) {
			sim = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
		Map<Object, Object> no = null;
		Map<Object, Object> oo = null;
		if (newobj instanceof Map) {
			no = (Map<Object, Object>) newobj;
		} else {
			no = new BeanMap(newobj);
		}
		if (oldobj instanceof Map) {
			oo = (Map<Object, Object>) oldobj;
		} else {
			oo = new BeanMap(oldobj);
		}
		String rsstr = "";
		Set<Entry<String, String>> vals = valmap.entrySet();
		for (Entry<String, String> entry : vals) {
			String ziduan = entry.getKey();
			String value = entry.getValue();
			Object obj = no.get(ziduan);
			if (obj instanceof String) {
				String nval = YESUtil.toString(obj);
				String oval = YESUtil.toString(oo.get(ziduan));
				if (!oval.equals(nval)) {
					rsstr += "【" + value + "】：[ " + oval + " ] —> [ " + nval + " ]<br/>";
				}
			} else if (obj instanceof Integer) {
				Integer nval = YESUtil.objtoint(obj);
				Integer oval = YESUtil.objtoint(oo.get(ziduan));
				if (oval != nval) {
					rsstr += "【" + value + "】：[ " + oval + " ] —> [ " + nval + " ]<br/>";
				}
			} else if (obj instanceof Double) {
				Double nval = YESUtil.objToDouble(obj);
				Double oval = YESUtil.objToDouble(oo.get(ziduan));
				if (!oval.equals(nval)) {
					rsstr += "【" + value + "】：[ " + oval + " ] —> [ " + nval + " ]<br/>";
				}
			} else if (obj instanceof Date) {
				Date nval = (Date) obj;
				Date oval = (Date) oo.get(ziduan);
				if (nval != null && oval != null) {// 都不为空执行判断
					if (oval.getTime() != nval.getTime()) {
						rsstr += "【" + value + "】：[ " + YESUtil.getDateString(oval, sim) + " ] —> [ "
								+ YESUtil.getDateString(nval, sim) + " ]<br/>";
					}
				} else if (nval != null || oval != null) {// 其中一个为空,一个不为空
					rsstr += "【" + value + "】：[ " + YESUtil.getDateString(oval, sim) + " ] —> [ "
							+ YESUtil.getDateString(nval, sim) + " ]<br/>";
				}
			}
		}
		return rsstr;
	}

	/**
	 * 比对两个对象的值是否完全一致,暂不支持数组,复杂对象
	 * 
	 * @param newobj
	 *            对象
	 * @param oldobj
	 *            对象
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean equalObject(Object obj1, Object obj2) {
		Map<Object, Object> o1 = null;
		new BeanMap(obj1);
		Map<Object, Object> o2 = null;
		new BeanMap(obj2);

		if (obj1 instanceof Map) {
			o1 = (Map<Object, Object>) obj1;
		} else {
			o1 = new BeanMap(obj1);
		}
		if (obj2 instanceof Map) {
			o2 = (Map<Object, Object>) obj2;
		} else {
			o2 = new BeanMap(obj2);
		}

		Set<Object> keys = o1.keySet();
		if (keys.size() != o2.keySet().size()) {
			return false;
		}
		for (Object key : keys) {
			if (!o1.get(key).equals(o2.get(key))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 导出excel,成功返回null,失败返回错误信息
	 * 
	 * @author 蔡琼伟 2016.03.17
	 * @param title
	 *            存放标题的map{key对应valls的key,value为excel上显示的值}
	 * @param valls
	 *            excel数据 List
	 * @param excelName
	 *            excel名和sheet名
	 * @param type
	 *            xls或xlsx 默认xls
	 * @param formatSetting
	 *            formatSettings对象,用于设置excel的特殊输出格式
	 * @param response
	 * @return 成功返回null,失败返回错误信息
	 */
	public static String exportExcel(LinkedHashMap<String, Object> title, List valls, String excelName, String type,
			FormatSettings formatSettings, HttpServletResponse response) {
		if (isEmpty(excelName)) {
			excelName = "excel";
		}
		if (isEmpty(type) || ((!type.equals("xls")) && (!type.equals("xlsx")))) {
			type = "xls";
		}
		YESExcelUtil yesexcel = new YESExcelUtil();
		OutputStream os = null;
		ByteArrayOutputStream bos = null;
		try {
			os = new BufferedOutputStream(response.getOutputStream());
			response.reset();
			response.setContentType("xls".equals(type) ? "application/vnd.ms-excel"
					: "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			// response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader(CONTENT_HEADER_DISPOSITION, String.format(CONTENT_HEADER_ATTACHMENT,
					YESUtil.encodeChineseDownloadFileName(YESUtil.toString(excelName) + "." + type)));
			bos = yesexcel.writeExcel(excelName, title, valls, "xlsx".equals(type), formatSettings);
			os.write(bos.toByteArray());
			os.close();
			return null;
		} catch (IOException e) {
			return e.getMessage();
		}
	}

	/**
	 * 导出excel,成功返回null,失败返回错误信息
	 * 
	 * @author 蔡琼伟 2014.12.12
	 * @param title
	 *            存放标题的map{key对应valls的key,value为excel上显示的值}
	 * @param valls
	 *            excel数据 List
	 * @param excelName
	 *            excel名和sheet名
	 * @param type
	 *            xls或xlsx 默认xls
	 * @param response
	 * @return 成功返回null,失败返回错误信息
	 */
	public static String exportExcel(LinkedHashMap<String, Object> title, List valls, String excelName, String type,
			HttpServletResponse response) {
		return exportExcel(title, valls, excelName, type, null, response);
	}

	/**
	 * 创建txt内容为context,编码为utf-8
	 * 
	 * @param filepath
	 *            文件地址
	 * @param context
	 *            内容
	 */
	public static boolean createTxt(String filepath, String context) {
		OutputStreamWriter out = null;
		try {
			out = new OutputStreamWriter(new FileOutputStream(filepath), "UTF-8");
			out.write(context);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 写入字符串到文件中 isappend=true则在txt后面添加而不删除原文件
	 * 
	 * @param filepath
	 *            文件地址
	 * @param context
	 *            内容
	 * @param isappend
	 *            是否是append
	 * @return boolean 成功或失败
	 */
	public static boolean writetxt(String filepath, String context, boolean isappend) {
		FileWriter fileWritter = null;
		try {
			fileWritter = new FileWriter(filepath, isappend);
			fileWritter.write(context);
			fileWritter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				if (fileWritter != null) {
					fileWritter.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 在filepath追加内容
	 * 
	 * @param filepath
	 *            文件地址
	 * @param context
	 *            内容
	 * @return 成功或失败
	 */
	public static boolean appendtxt(String filepath, String context) {
		return writetxt(filepath, context, true);
	}

	/**
	 * 得到格式化后的时间
	 * 
	 * @param date
	 * @param sim
	 *            格式化
	 * @return 得到sim格式的date 如果date为空返回空字符串
	 */
	public static String getDateString(Date date, SimpleDateFormat sim) {
		if (date != null)
			return sim.format(date);
		else
			return "";
	}

	/**
	 * 得到(yyyy-MM-dd HH:mm:ss)格式的date字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss格式的时间,如果为空返回空字符串
	 */
	public static String getDatetimeString(Date date) {
		return getDateString(date, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	/**
	 * 得到(yyyy-MM-dd)格式的date字符串
	 * 
	 * @param date
	 * @return yyyy-MM-dd格式的时间,如果为空返回空字符串
	 */
	public static String getDateString(Date date) {
		return getDateString(date, new SimpleDateFormat("yyyy-MM-dd"));
	}

	/**
	 * 得到sim格式化后的当前时间
	 * 
	 * @param date
	 * @return yyyy-MM-dd格式的时间,如果为空返回空字符串
	 */
	public static String getDateString(SimpleDateFormat sim) {
		return getDateString(new Date(), sim);
	}

	/**
	 * 得到format格式化后的当前时间
	 * 
	 * @param format
	 * @return yyyy-MM-dd格式的时间,如果为空返回空字符串
	 */
	public static String getDateString(String format) {
		return getDateString(new Date(), new SimpleDateFormat(format));
	}

	/**
	 * 
	 * @return 得到yyyy-MM-dd格式的当前时间
	 */
	public static String getDateString() {
		return getDateString(new Date());
	}

	public static Date getDateByString(String datestr, SimpleDateFormat sim) {
		try {
			return sim.parse(datestr);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getDateByString(String datestr) {
		return getDateByString(datestr, new SimpleDateFormat("yyyy-MM-dd"));
	}

	public static Date getDateTimeByString(String datestr) {
		return getDateByString(datestr, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}

	public static Date getTimeByString(String datestr) {
		return getDateByString(datestr, new SimpleDateFormat("HH:mm:ss"));
	}

	public static DateTime getDatetimeByDate(Date date) {
		if (date != null)
			return new DateTime(date.getTime());
		else
			return null;
	}

	/**
	 * 对文件流输出下载的中文文件名进行编码 屏蔽各种浏览器版本的差异性
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

	public static Resource getResource(String path) {
		return ((ResourcePatternResolver) new PathMatchingResourcePatternResolver()).getResource(path);
	}

	/**
	 * 字符串返回
	 * 
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		if (obj == null || obj.equals("null") || obj.toString().isEmpty())
			return "";
		else
			return obj.toString().trim();
	}

	/**
	 * excel转换为string型,如有.0去除后面的.0
	 * 
	 * @param obj
	 * @return
	 */
	public static String exceltoString(Object obj) {
		if (obj == null || obj.equals("null") || obj.toString().isEmpty())
			return "";
		else {
			String str = obj.toString().trim();
			if (str.indexOf(".0") != -1)
				str = str.substring(0, str.length() - 2);
			return str;
		}
	}

	/**
	 * 字符串返回str
	 * 
	 * @param obj
	 * @param str
	 *            默认值
	 * @return
	 */
	public static String toString(Object obj, String str) {
		if (obj == null || obj.equals("null") || obj.toString().isEmpty())
			return str;
		else
			return obj.toString().trim();
	}

	/**
	 * 判断对象是否Empty(null或元素为0)<br>
	 * 实用于对如下对象做判断:String Collection及其子类 Map及其子类
	 * 
	 * @param pObj
	 *            待检查对象
	 * @return boolean 返回的布尔值
	 */
	public static boolean isEmpty(Object pObj) {
		if (pObj == null)
			return true;
		if (pObj.equals(""))
			return true;
		if (pObj == "")
			return true;
		if (pObj instanceof String) {
			return ((String) pObj).length() < 1;
		} else if (pObj instanceof Collection) {
			return ((Collection) pObj).size() < 1;
		} else if (pObj instanceof Map) {
			return ((Map) pObj).size() < 1;
		} else if (pObj.getClass().isArray()) {
			return Array.getLength(pObj) < 1;
		}
		return false;
	}

	public static boolean isNotEmpty(Object pObj) {
		return !isEmpty(pObj);
	}

	public static boolean isNumeric(String str) {
		if (str == null)
			return true;
		return str.matches("^[0-9]*$");
	}

	public static boolean isDouble(String str) {
		if (str == null)
			return true;
		return str.matches("^[-+]?(\\d+(\\.\\d*)?|\\.\\d+)$");
	}

	public static boolean isDouble(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof Double) {
			return true;
		}
		return isDouble(obj.toString());
	}

	public static boolean isNumeric(Object obj) {
		if (obj == null)
			return true;
		return isNumeric(obj.toString());
	}

	public static Integer objtoint(Object obj) {
		return objtoint(obj, 0);
	}

	public static Integer objtoint(Object obj, Integer iter) {
		if (isNumeric(obj)) {
			return isEmpty(obj) ? iter : Integer.parseInt(obj.toString());
		} else {
			return iter;
		}
	}

	public static Long objtolong(Object obj) {
		return objtolong(obj, 0l);
	}

	public static Long objtolong(Object obj, Long iter) {
		if (isNumeric(obj)) {
			return isEmpty(obj) ? iter : Long.parseLong(obj.toString());
		} else {
			return iter;
		}
	}

	public static Double objToDouble(Object obj) {
		return objToDouble(obj, (double) 0l);
	}

	public static Double objToDoubleHasNull(Object obj) {
		if (isDouble(obj)) {
			return isEmpty(obj) ? null : Double.parseDouble(obj.toString());
		} else {
			return null;
		}
	}

	public static Double objToDouble(Object obj, Double num) {
		if (isDouble(obj)) {
			return isEmpty(obj) ? num : Double.parseDouble(obj.toString());
		} else {
			return num;
		}
	}

	public static Boolean objToBoolean(Object obj) {
		return objToBoolean(obj, false);
	}

	public static Boolean objToBoolean(Object obj, Boolean bool) {
		String value = toString(obj).trim();
		if (value.isEmpty()) {
			return isEmpty(bool) ? false : bool;
		}
		if ("是".equals(value) || "真".equals(value) || "1".equals(value) || "true".equalsIgnoreCase(value)) {
			return true;
		}
		if ("否".equals(value) || "假".equals(value) || "0".equals(value) || "false".equalsIgnoreCase(value)) {
			return false;
		}

		return Boolean.parseBoolean(value);
	}

	public static Long[] objTolongArray(Object[] obj) {
		int len = obj.length;
		Long[] lg = new Long[len];
		for (int i = 0; i < len; i++) {
			lg[i] = objtolong(obj[i].toString().trim());
		}
		return lg;
	}

	/**
	 * 判断字符串是否是整数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isInteger(String str) {
		if (str == null)
			return true;
		return str.matches("^[-\\+]?\\d+$");
	}

	/**
	 * 判断字符串是否是浮点数
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isFloat(String str) {
		if (str == null)
			return true;
		return str.matches("^-?([1-9]\\d*\\.\\d*|0\\.\\d*[1-9]\\d*|0?\\.0+|0)$") || isInteger(str);
	}

	/**
	 * 去右边一个字符
	 * 
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String rtrim(String str, char ch) {
		return org.springframework.util.StringUtils.trimTrailingCharacter(str, ch);
	}

	/**
	 * 去左边一个字符
	 * 
	 * @param str
	 * @param ch
	 * @return
	 */
	public static String ltrim(String str, char ch) {
		return org.springframework.util.StringUtils.trimLeadingCharacter(str, ch);
	}

	/**
	 * http 请求对象(request)
	 * 
	 * @return HttpServletRequest
	 */
	public static HttpServletRequest request() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}

	/**
	 * 获得所有cookies
	 * 
	 * @return
	 */
	public static Cookie[] getCookies() {
		return request().getCookies();
	}

	/**
	 * 设置cookie 值
	 * 
	 * @param key
	 * @param value
	 * @param _time
	 *            周期 可填（默认1小时）单位秒
	 */
	public static void setCookiesValue(HttpServletResponse response, String key, String value, Integer... _time) {
		Cookie cookie = new Cookie(key, value);
		cookie.setMaxAge(_time.length > 0 ? _time[0] : 3600);
		response.addCookie(cookie);
	}

	/**
	 * 删除指定cookie
	 * 
	 * @param key
	 */
	public static void removeCookies(HttpServletResponse response, String key) {
		setCookiesValue(response, key, null, 0);
	}

	/**
	 * 取指定cookie值
	 * 
	 * @param key
	 * @return
	 */
	public static String getCookiesValue(String key) {
		Cookie[] cookies = request().getCookies();
		if (isEmpty(cookies) || isEmpty(key)) {
			return "";
		}
		for (Cookie cookie : cookies) {
			if (key.equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return "";
	}

	/**
	 * 设置加密的cookie
	 * 
	 * @param key
	 * @param value
	 * @return
	 */
	public static void setCookiesCryptValue(HttpServletResponse response, String key, String value,
			Integer... integers) {
		setCookiesValue(response, key, encrypt(value), integers);
	}

	/**
	 * 根据key 取解密后的cookie值
	 * 
	 * @param key
	 * @return
	 */
	public static String getCookiesCryptValue(String key) {
		String _str = getCookiesValue(key);
		return _str == "" ? _str : decrypt(_str);
	}

	/**
	 * 取cookie 中的用户信息
	 * 
	 * @return
	 */
	public static String[] getCookiesUser() {
		return YESUtil.getCookiesCryptValue("yesuserinfo").split("\\{yes\\|\\|yes\\}");
	}

	/**
	 * des 加密
	 * 
	 * @param str
	 *            需要加密的字符串
	 * @param rawKeyData
	 *            密匙(长度到少是16)
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String encrypt(String str, byte[]... _rawKeyData) {
		// DES算法要求有一个可信任的随机数源 cryptKey
		SecureRandom sr = new SecureRandom();
		String rstr = "";
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks;
		try {
			byte[] rawKeyData = _rawKeyData.length > 0 ? _rawKeyData[0] : cryptKey;
			dks = new DESKeySpec(rawKeyData);
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.ENCRYPT_MODE, key, sr);
			// 现在，获取数据并加密
			byte data[] = str.getBytes();
			// 正式执行加密操作
			byte[] encryptedData = cipher.doFinal(data);
			rstr = new String(Base64.encode(encryptedData), "UTF-8");
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			return rstr;
		}
	}

	/**
	 * 解密
	 * 
	 * @param encryptedData
	 *            需要解密的字符串
	 * @param rawKeyData
	 *            密匙(长度到少是16)
	 * @return
	 */
	@SuppressWarnings("finally")
	public static String decrypt(String encryptedData, byte[]... _rawKeyData) {
		// DES算法要求有一个可信任的随机数源
		SecureRandom sr = new SecureRandom();
		byte[] rawKeyData = _rawKeyData.length > 0 ? _rawKeyData[0] : cryptKey;
		String rstr = "";
		// 从原始密匙数据创建一个DESKeySpec对象
		DESKeySpec dks;
		try {
			dks = new DESKeySpec(rawKeyData);
			// 创建一个密匙工厂，然后用它把DESKeySpec对象转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey key = keyFactory.generateSecret(dks);
			// Cipher对象实际完成解密操作
			Cipher cipher = Cipher.getInstance("DES");
			// 用密匙初始化Cipher对象
			cipher.init(Cipher.DECRYPT_MODE, key, sr);
			// 正式执行解密操作
			rstr = new String(cipher.doFinal(Base64.decode(encryptedData.getBytes("UTF-8"))));
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} finally {
			return rstr;
		}
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
	 * 根据属性名称取会话中的对象
	 * 
	 * @param name
	 *            属性名称
	 * @return 对象
	 */
	public static Object getSession(String name) {
		try {
			return session().getAttribute(name);
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 写入会话属性
	 * 
	 * @param name
	 *            属性名称
	 * @param obj
	 *            对象
	 */
	public static void putSession(String name, Object obj) {
		session().setAttribute(name, obj);
	}

	/**
	 * 获取文件版本号
	 * 
	 * @return 文件版本号
	 */
	public static String getVersion(String file2) {
		File file = new File(file2);
		RandomAccessFile raf = null;
		byte[] buffer;
		String str;
		try {
			raf = new RandomAccessFile(file, "r");
			buffer = new byte[64];
			raf.read(buffer);
			str = "" + (char) buffer[0] + (char) buffer[1];
			if (!"MZ".equals(str)) {
				return "";
			}

			int peOffset = unpack(new byte[] { buffer[60], buffer[61], buffer[62], buffer[63] });
			if (peOffset < 64) {
				return "";
			}

			raf.seek(peOffset);
			buffer = new byte[24];
			raf.read(buffer);
			str = "" + (char) buffer[0] + (char) buffer[1];
			if (!"PE".equals(str)) {
				return "";
			}
			int machine = unpack(new byte[] { buffer[4], buffer[5] });
			if (machine != 332) {
				return "";
			}

			int noSections = unpack(new byte[] { buffer[6], buffer[7] });
			int optHdrSize = unpack(new byte[] { buffer[20], buffer[21] });
			raf.seek(raf.getFilePointer() + optHdrSize);
			boolean resFound = false;
			for (int i = 0; i < noSections; i++) {
				buffer = new byte[40];
				raf.read(buffer);
				str = "" + (char) buffer[0] + (char) buffer[1] + (char) buffer[2] + (char) buffer[3] + (char) buffer[4];
				if (".rsrc".equals(str)) {
					resFound = true;
					break;
				}
			}
			if (!resFound) {
				return "";
			}

			int infoVirt = unpack(new byte[] { buffer[12], buffer[13], buffer[14], buffer[15] });
			int infoSize = unpack(new byte[] { buffer[16], buffer[17], buffer[18], buffer[19] });
			int infoOff = unpack(new byte[] { buffer[20], buffer[21], buffer[22], buffer[23] });
			raf.seek(infoOff);
			buffer = new byte[infoSize];
			raf.read(buffer);
			int numDirs = unpack(new byte[] { buffer[14], buffer[15] });
			boolean infoFound = false;
			int subOff = 0;
			for (int i = 0; i < numDirs; i++) {
				int type = unpack(
						new byte[] { buffer[i * 8 + 16], buffer[i * 8 + 17], buffer[i * 8 + 18], buffer[i * 8 + 19] });
				if (type == 16) { // FILEINFO resource
					infoFound = true;
					subOff = unpack(new byte[] { buffer[i * 8 + 20], buffer[i * 8 + 21], buffer[i * 8 + 22],
							buffer[i * 8 + 23] });
					break;
				}
			}
			if (!infoFound) {
				return "";
			}

			subOff = subOff & 0x7fffffff;
			infoOff = unpack(
					new byte[] { buffer[subOff + 20], buffer[subOff + 21], buffer[subOff + 22], buffer[subOff + 23] }); // offset
																														// of
																														// first
																														// FILEINFO
			infoOff = infoOff & 0x7fffffff;
			infoOff = unpack(new byte[] { buffer[infoOff + 20], buffer[infoOff + 21], buffer[infoOff + 22],
					buffer[infoOff + 23] }); // offset
												// to
												// data
			int dataOff = unpack(
					new byte[] { buffer[infoOff], buffer[infoOff + 1], buffer[infoOff + 2], buffer[infoOff + 3] });
			dataOff = dataOff - infoVirt;

			int version1 = unpack(new byte[] { buffer[dataOff + 48], buffer[dataOff + 48 + 1] });
			int version2 = unpack(new byte[] { buffer[dataOff + 48 + 2], buffer[dataOff + 48 + 3] });
			int version3 = unpack(new byte[] { buffer[dataOff + 48 + 4], buffer[dataOff + 48 + 5] });
			int version4 = unpack(new byte[] { buffer[dataOff + 48 + 6], buffer[dataOff + 48 + 7] });
			String version = version2 + "." + version1 + "." + version4 + "." + version3;
			return version;

		} catch (FileNotFoundException e) {
			return "error FileNotFoundException";
		} catch (IOException e) {
			return "error IOException";
		} finally {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static int unpack(byte[] b) {
		int num = 0;
		for (int i = 0; i < b.length; i++) {
			num = 256 * num + (b[b.length - 1 - i] & 0xff);
		}
		return num;
	}

	/**
	 * 获取文件MD5加密
	 * 
	 * @return md5字符串
	 */
	public static String getMd5ByFile(File file) {
		FileInputStream in = null;
		String value = null;
		try {
			in = new FileInputStream(file);
			MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file.length());
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(byteBuffer);
			BigInteger bi = new BigInteger(1, md5.digest());

			value = bi.toString(16);
			StringBuffer sb = new StringBuffer(value);
			if (sb.length() < 32) {
				value = patchMd5Str(value);
			}
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (null != in) {
				try {
					in.close();
				} catch (IOException e) {

					e.printStackTrace();
				}
			}
		}
		return value;
	}

	public static String getMd5ByStream(InputStream stream) {
		String value = null;
		try {
			byte[] buffer = new byte[stream.available()];
			stream.read(buffer);
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(buffer);
			BigInteger bi = new BigInteger(1, md5.digest());
			value = bi.toString(16);
			StringBuffer sb = new StringBuffer(value);
			if (sb.length() < 32) {
				value = patchMd5Str(value);
			}
			buffer = new byte[0];
		} catch (IOException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return value;
	}

	private static String patchMd5Str(String value) {
		StringBuffer sb = new StringBuffer(value);
		while (sb.length() < 32) {
			sb = new StringBuffer("0" + sb.toString());
		}
		return sb.toString();
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param length
	 *            获取的字符串长度
	 * @return
	 */
	public static String getRandmoString(Integer length) {
		StringBuffer str = new StringBuffer("abcdefghijklnmopqrstuvwxyzABCDEFGHIJKLNMOPQRSTUVWXYZ");
		Random rand = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			sb.append(str.charAt(rand.nextInt(str.length())));
		}
		return sb.toString();
	}

	/**
	 * 获得当前日期时间字符串
	 * 
	 * @return yyyyMMddHHmmss格式时间
	 */
	public static String getDate() {
		Date dt = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		return sdf.format(dt);
	}

	/**
	 * 比较两个日期的大小
	 * 
	 * @param dateA
	 * @param dateB
	 * @param format
	 *            根据
	 * @return dateA > dateB --true
	 * @throws ParseException
	 *             转换Date类型错误
	 */
	public static Boolean compareDate(DateTime dateA, DateTime dateB, String format) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		Integer dA = objtoint(sdf.format(dateA.toDate()));
		Integer dB = objtoint(sdf.format(dateB.toDate()));
		if (dA > dB) {
			return true;
		} else if (dA < dB) {
			return false;
		}

		if (!"MM".equals(format) && !"dd".equals(format))
			return false;
		sdf = new SimpleDateFormat("MM");
		dA = objtoint(sdf.format(dateA.toDate()));
		dB = objtoint(sdf.format(dateB.toDate()));
		if (dA > dB) {
			return true;
		} else if (dA < dB) {
			return false;
		}

		if (!"dd".equals(format))
			return false;
		sdf = new SimpleDateFormat("dd");
		dA = objtoint(sdf.format(dateA.toDate()));
		dB = objtoint(sdf.format(dateB.toDate()));
		if (dA > dB) {
			return true;
		} else if (dA < dB) {
			return false;
		}
		return false;
	}

	/**
	 * 计算时间
	 * 
	 * @param startTime
	 *            ： 开始时间
	 * @param endTime
	 *            ： 结束时间
	 * @return
	 */
	public static int caculateTotalDays(String startTime, String endTime) {
		if (BaseUtil.isEmpty(startTime) || BaseUtil.isEmpty(endTime)) {
			return 0;
		}
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		Date date1 = null;
		Date date = null;
		Long l = 0L;
		try {
			date = formatter.parse(startTime);
			long ts = date.getTime();
			date1 = formatter.parse(endTime);
			long ts1 = date1.getTime();

			l = (ts - ts1) / (1000 * 60 * 60 * 24);

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return l.intValue();
	}

	/**
	 * 比较两个Double是否相等
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean compareDouble(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) == Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个Double是否相等
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean doubleiseq(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) == Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个doublea是否大于等doubleb
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean doubleisgte(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) >= Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个doublea是否小于等doubleb
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean doubleislte(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) <= Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个doublea是否大于doubleb
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean doubleisgt(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) > Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 比较两个doublea是否小于doubleb
	 * 
	 * @param doublea
	 * @param doubleb
	 * @param format
	 *            根据
	 * @return doublea > doubleb --true
	 */
	public static Boolean doubleislt(Double doublea, Double doubleb) {
		doublea = BaseUtil.isEmpty(doublea) ? 0.0 : doublea;
		doubleb = BaseUtil.isEmpty(doubleb) ? 0.0 : doubleb;
		if (Double.doubleToLongBits(doublea) < Double.doubleToLongBits(doubleb)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 整型格式化 示例:(formatInteger(1,0,5)--输出:00001)
	 * 
	 * @param number
	 *            格式化整型
	 * @param complement
	 *            标识
	 * @param length
	 *            最小长度
	 * @return
	 */
	public static String formatInteger(Integer number, Integer complement, Integer length) {
		String s = "%1$" + complement + length + "d";
		return String.format(s, number);
	}

	/**
	 * 使用指定的格式化字符串和参数返回一个格式化字符串
	 * 
	 * @param arg0
	 *            指定的格式化字符串
	 * @param arg1
	 *            参数 格式化说明：(index-参数索引,identifies-标识,length 长度,minLength最小长度)
	 *            n%[index]/:自定义,必须 index (参数:自定义字符) r%[length]/:随机字符,可选
	 *            length,默认长度1 d%[index]/:日期,可选 index,默认格式yyyyMMddHHmmss
	 *            (参数:日期格式) i%[index][identifies][minLength]/:整型,必须 index 可选
	 *            identifies minLength,默认identifies=0 minLength=4 (参数:需格式化的整型)
	 *            例:getFormatId("r%2/n%1/d%2/i%304/","test","yyyyMMdd",8)--输出：
	 *            rdtest201310290008,依次对应 rd-test-20131029-0008
	 * @return String
	 */
	public static String getFormatId(String arg0, Object... arg1) {
		String str[] = arg0.split("/");
		String result = "";
		Integer index = 0;
		for (int i = 0; i < str.length; i++) {
			String keys[] = str[i].split("%");
			Integer length = keys.length;
			if (length < 1)
				return arg0 + ":" + str[i] + " 错误-格式不正确!";
			;
			if ("n".equals(keys[0])) {// 自定义
				if (length < 2)
					return arg0 + ":" + str[i] + " 错误-格式不正确!";
				index = objtoint(keys[1]);
				if (arg1.length < index)
					return arg0 + ":" + str[i] + " 错误-找不到参数!";
				result += arg1[index - 1].toString();
			} else if ("r".equals(keys[0])) {// 随机
				length = length > 1 ? objtoint(keys[1]) : 1;
				result += getRandmoString(length);
			} else if ("d".equals(keys[0])) {// 日期
				String format = "yyyyMMddHHmmss";
				if (length > 1) {
					index = objtoint(keys[1]);
					format = arg1.length >= index ? arg1[index - 1].toString() : format;
				}
				;
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				result += sdf.format(new Date());
			} else if ("i".equals(keys[0])) {// 整型
				if (length < 2)
					return arg0 + ":" + str[i] + " 错误-格式不正确!";
				char parm[] = keys[1].toCharArray();
				if (parm.length < 1)
					return arg0 + ":" + str[i] + " 错误-格式或者参数不正确!";
				index = objtoint(parm[0]);
				if (arg1.length < index)
					return arg0 + ":" + str[i] + " 错误-找不到参数!";
				Long number = objtolong(arg1[index - 1].toString());
				Integer fillNum = parm.length > 1 ? objtoint(parm[1]) : 0;
				Integer min_length = parm.length > 2 ? objtoint(keys[1].substring(2)) : 4;
				String s = "%1$" + fillNum + min_length + "d";
				result += String.format(s, number);
			}
		}
		return result;
	}

	/**
	 * 格式化编号为sql语句应用的in后面字符串(id,id,...)
	 * 
	 * @param uids
	 * @return
	 */
	public static String getFormatSqlIn(String uids) {
		String[] strs = uids.split(",");
		uids = "(";
		for (Integer i = 0; i < strs.length; i++) {
			uids += "'" + strs[i] + "'";
			if ((i + 1) < strs.length) {
				uids += ",";
			}
		}
		uids += ")";
		return uids;
	}

	/**
	 * 获取随机生成uuid
	 */
	public static String getUuid() {
		return UUID.randomUUID().toString().replace("-", "");
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

	/**
	 * 服务器上传路径
	 * 
	 * @param path
	 * @return
	 */
	public static String getuploadPath(String path) {

		String updloadpath = "";
		Resource resource = new ClassPathResource("config.properties");
		Properties p = null;
		try {
			p = PropertiesLoaderUtils.loadProperties(resource);
			updloadpath = p.getProperty("upload.path");

		} catch (IOException e) {
			System.out.println("读取配置文件出错");
			return session().getServletContext().getRealPath(path);
		}

		if (isEmpty(updloadpath)) {
			updloadpath = getRealPath(path);
		}

		return updloadpath;

	}

	/**
	 * 获取序列化根路径
	 */
	public static String getSlcPath() {
		return getRealPath("WEB-INF/yes/base/serializableCache");
	}

	public static String stream2Str(InputStream stream) {
		try {
			byte[] buf = new byte[stream.available()];
			stream.read(buf);
			return new String(buf);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 字符串第一个字母转小写
	 * 
	 * @param str
	 * @return
	 */
	public static String converStrFirstLowerCase(String str) {
		if (isEmpty(str)) {
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
	 * 字符串第一个字母转大写
	 * 
	 * @param str
	 * @return
	 */
	public static String converStrFirstUpperCase(String str) {
		if (isEmpty(str)) {
			return "";
		}
		byte[] items = str.getBytes();
		char c = (char) items[0];
		if (c > 122 || c < 97) {
			return str;
		}
		items[0] = (byte) (c - 32);
		;
		return new String(items);
	}

	/**
	 * 把内容输出到控制台
	 * 
	 * @param text
	 */
	public static void outPrintln(String text) {
		System.out.println(text + new SimpleDateFormat(" [yyyy-MM-dd HH:mm:ss]").format(new Date()));
	}

	/**
	 * double 加法 v1+v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleAdd(Double v1, Double v2) {
		v1 = isEmpty(v1) ? 0 : v1;
		v2 = isEmpty(v2) ? 0 : v2;
		BigDecimal b1 = new BigDecimal(toString(v1));
		BigDecimal b2 = new BigDecimal(toString(v2));
		return b1.add(b2).doubleValue();
	}

	/**
	 * double 减法 v1-v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleSub(Double v1, Double v2) {
		v1 = isEmpty(v1) ? 0 : v1;
		v2 = isEmpty(v2) ? 0 : v2;
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.subtract(b2).doubleValue();
	}

	/**
	 * double 乘法 v1*v2
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static Double doubleMul(Double v1, Double v2) {
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.multiply(b2).doubleValue();
	}

	/**
	 * double 除法 v1/v2 默认保留小数点后六位
	 * 
	 * @param v1
	 * @param v2
	 * @return if v2==0D return 0D
	 */
	public static Double doubleDivde(Double v1, Double v2) {
		return doubleDivde(v1, v2, 6);
	}

	/**
	 * double 除法 v1/v2
	 * 
	 * @param v1
	 * @param v2
	 * @param scale
	 *            除不尽时候精确的小数点位数
	 * @return
	 */
	public static Double doubleDivde(Double v1, Double v2, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		if (v2.compareTo(0D) == 0) {
			return 0D;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal(v2.toString());
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * double 小数位精确
	 * 
	 * @param v1
	 *            需要四舍五入的double数字
	 * @param scale
	 *            保留小数点后几位
	 * @return
	 */
	public static Double doubleRound(Double v1, int scale) {
		if (scale < 0) {
			scale = 0;
		}
		BigDecimal b1 = new BigDecimal(v1.toString());
		BigDecimal b2 = new BigDecimal("1");
		return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();

	}

	/**
	 * jsonString 转Map
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> getMap(String jsonStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		JSONObject jObj = JSONObject.fromObject(jsonStr);
		result = (Map<String, Object>) JSONObject.toBean(jObj, Map.class);
		return result;
	}

	/**
	 * jsonString 转Map
	 * 
	 * @param jsonStr
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, String> getStrMap(String jsonStr) {
		Map<String, Object> result = new HashMap<String, Object>();
		Map<String, String> strresult = new HashMap<String, String>();
		JSONObject jObj = JSONObject.fromObject(jsonStr);
		result = (Map<String, Object>) JSONObject.toBean(jObj, Map.class);
		Set<?> set = result.entrySet();
		for (Iterator<?> iterator = set.iterator(); iterator.hasNext();) {
			Map.Entry<String, Object> string = (Entry<String, Object>) iterator.next();
			String key = string.getKey();
			String value = toString(string.getValue());

			strresult.put(key, value);
		}
		return strresult;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static List<Map<String, Object>> getList(String jsonStr) {
		List<Map<String, Object>> result;
		JSONArray jObjs = JSONArray.fromObject(jsonStr);
		result = (List<Map<String, Object>>) JSONArray.toList(jObjs, Map.class);
		return result;
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public List<Map<String, Object>> getJavaList(String jsonStr) {
		List<Map<String, Object>> result;
		JSONArray jObjs = JSONArray.fromObject(jsonStr);
		result = (List<Map<String, Object>>) JSONArray.toList(jObjs, Map.class);
		return result;
	}

	public static String getJson(Object obj) {
		JsonConfig jsonConfig = new JsonConfig();
		JSONObject jObj = JSONObject.fromObject(obj, jsonConfig);
		return jObj.toString();
	}

	public static String getJsonArray(List<Object> list) {
		JSONArray jObjs = JSONArray.fromObject(list);
		return jObjs.toString();
	}

	/**
	 * 删除远程共享目录or文件
	 * 
	 * @param remotePath
	 *            路径
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static void deleteRemote(String remotePath) throws MalformedURLException, SmbException {
		SmbFile smbfile = new SmbFile("smb://" + remotePath);
		if (smbfile.exists()) {
			smbfile.delete();
		}
	}

	/**
	 * 文件写到远程共享目录
	 * 路径参考:http://jcifs.samba.org/src/docs/api/jcifs/smb/SmbFile.html
	 * 
	 * @param file
	 *            文件
	 * @param outPath
	 *            共享目录 如：administrator:123456@172.16.10.136/file
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws SmbException
	 */
	public static String saveRemote(File file, String outPath) throws SmbException, UnknownHostException, IOException {
		if (file.isFile()) {
			SmbFile smbfile = new SmbFile("smb://" + outPath);
			createRemoteDir(smbfile.getParent());
			FileUtils.copyFile(file, new SmbFileOutputStream(smbfile));
			return "success";
		} else {
			return "nofile";
		}
	}

	/**
	 * 读取远程共享文件
	 * 
	 * @param remotePath
	 *            共享文件路径 如：administrator:123456@172.16.10.136/file/xx
	 * @param targetFile
	 *            放在本地文件路径
	 * @return
	 * @throws IOException
	 */
	public static File readRemote(String remotePath, String targetFile) throws IOException {
		File file = new File(targetFile);
		if (!file.exists()) {
			file.createNewFile();
		}
		SmbFile smbfile = new SmbFile("smb://" + remotePath);
		if (smbfile.isFile()) {
			FileUtils.copyInputStreamToFile(new SmbFileInputStream(smbfile), file);
			return file;
		}
		return null;
	}

	/**
	 * 创建共享目录
	 * 
	 * @param remotePath
	 *            共享文件路径 如：administrator:123456@172.16.10.136/file/xx
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static void createRemoteDir(String remotePath) throws MalformedURLException, SmbException {
		remotePath = remotePath.startsWith("smb://") ? remotePath : "smb://" + remotePath;
		SmbFile smbfile = new SmbFile(remotePath);
		if (!smbfile.exists()) {
			smbfile.mkdirs();
		}
	}

	/**
	 * 判断远程目录或文件是否存在
	 * 
	 * @param remotePath
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static Boolean remoteExists(String remotePath) throws SmbException, MalformedURLException {
		return new SmbFile("smb://" + remotePath).exists();
	}

	/**
	 * 取得字串中字串的个数
	 * 
	 * @param remotePath
	 * @throws MalformedURLException
	 * @throws SmbException
	 */
	public static int getOccur(String src, String find) {
		int o = 0;
		int index = -1;
		while ((index = src.indexOf(find, index)) > -1) {
			++index;
			++o;
		}
		return o;
	}

	/**
	 * 获取Role集合
	 * 
	 * @return List<?>类型的Role集合
	 */
	/*
	 * public static List<?> getRoles() { return (List<?>) getSession("roles");
	 * }
	 */

	/**
	 * 判断登录用户是否属于传入的角色集合
	 * 
	 */
	/*
	 * @SuppressWarnings("unchecked") public static boolean
	 * hasRoles(List<String> roles) { if (isEmpty(roles)) return false;
	 * List<RoleEntity> loginRoles = (List<RoleEntity>) getRoles(); for (String
	 * s : roles) { for (RoleEntity r : loginRoles) { if
	 * (r.getRle_name().equals(s)) { return true; } } } return false; }
	 */
	/**
	 * 字符串加密
	 * 
	 * @param str
	 *            要加密的字符串
	 * @param hashType
	 *            加密类型如：（"MD5"，"SHA1"，"SHA-256"，"SHA-384"，"SHA-512"）
	 * @return 加密后的字符串
	 * @throws Exception
	 */

	/*
	 * public static String getEncry(String str, YESHashType hashType) {
	 * MessageDigest md5; try { md5 =
	 * MessageDigest.getInstance(hashType.toString()); return
	 * toHexString(md5.digest(str.getBytes())); } catch
	 * (NoSuchAlgorithmException e) { System.out.println("加密失败！！"); } return "";
	 * }
	 * 
	 * private static String toHexString(byte[] b) { StringBuilder sb = new
	 * StringBuilder(b.length * 2); for (int i = 0; i < b.length; i++) {
	 * sb.append(hexChar[(b[i] & 0xf0) >>> 4]); sb.append(hexChar[b[i] & 0x0f]);
	 * } return sb.toString(); }
	 */
	/**
	 * 导入excel,返回Message.success:状态,Message.message:错误提示,Message.data:结果集合
	 * 
	 * @author 颜惠强 2015.08.10
	 * @param excel
	 *            excel文件的MultipartFile
	 * @return Message.success:状态,Message.message:错误提示,Message.data:结果集合
	 */
	public static Message importExcel(MultipartFile excel) {
		Message message = new Message();
		String name = excel.getOriginalFilename().toLowerCase();
		if (name.endsWith("xlsx") || name.endsWith("xls")) {
			YESExcelUtil yesexcel = new YESExcelUtil();
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
			message.setMessage("请上传正确的excel模版");
		}
		return message;
	}

	// String转为List cx
	public static List<String> StrtoList(String str) {
		if (isEmpty(str)) {
			return new ArrayList<String>();
		}
		String[] arrayStr = new String[] {};
		arrayStr = str.split(",");
		return java.util.Arrays.asList(arrayStr);
	}

	/**
	 * @Description 获取客户端IP地址
	 * @Date wwc2015-12-24
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
			if (ip.equals("127.0.0.1")) {
				// 根据网卡取本机配置的IP
				InetAddress inet = null;
				try {
					inet = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				ip = inet.getHostAddress();
			}
		}
		// 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
		if (ip != null && ip.length() > 15) {
			if (ip.indexOf(",") > 0) {
				ip = ip.substring(0, ip.indexOf(","));
			}
		}
		return ip;
	}

	/**
	 * @Description 获取随机英文
	 * @Date wwc2015-12-25
	 * @param 返回数字
	 * @return
	 */
	public static String getRandomStr(int num) {
		String str = "";
		for (int i = 0; i < num; i++) {
			str = str + (char) (Math.random() * 26 + 'A');
		}
		return str;
	}

	/**
	 * @Description 汉字转为拼音
	 * @Date wwc2016-01-04
	 * @param 汉字
	 * @return 返回拼音字符
	 */
	public static String ChineseToPinyin(String character) {
		return PinyinUtils.getPingYin(character);
	}

	/**
	 * @Description 汉字转为拼音首字母
	 * @Date wwc2016-01-04
	 * @param 汉字
	 * @param 是否大写
	 *            ：true大写
	 * @return 返回首字母拼音字符串
	 */
	public static String ChineseToPinyinHeader(String character, boolean isCapital) {
		return PinyinUtils.getPinYinHeadChar(character, isCapital);
	}

	/**
	 * 
	 * @Description 一天开始日期 wwc
	 * @param
	 * @return
	 */
	public static Date getBeginDate(Date from) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = String.format("%s %s", sdf.format(from), "00:00:00");
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Date date = from;
		try {
			date = sdf.parse(sd);
		} catch (ParseException e) {
			date = from;
		}
		return date;
	}

	/**
	 * @Description 一天最晚日期 wwc
	 * @param
	 * @return
	 */
	public static Date getEndDate(Date to) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sd = String.format("%s %s", sdf.format(to), "23:59:59");
		Date date = to;
		sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			date = sdf.parse(sd);
		} catch (ParseException e) {
			date = to;
		}
		return date;
	}

	/**
	 * @Description 获取右边%的like 查询字符串 wwc
	 * @param
	 * @return
	 */
	public static String getRightLikeStr(String str) {
		return "'" + str + "%'";
	}

	/**
	 * @Description 获取左右%的like 查询字符串 wwc
	 * @param
	 * @return
	 */
	public static String getLikeStr(String str) {
		return "'%" + str + "%'";
	}

	/**
	 * @Description 字符川加'' wwc
	 * @param
	 * @return
	 */
	public static String getQuotedstr(String str) {
		return String.format("'%s'", str);
	}

	/**
	 * 格式化数字
	 * 
	 * @param Pattern
	 * @param num
	 * @return
	 */
	public static String formatNumber(String Pattern, Double num) {
		DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
		df.applyPattern("##.####");
		return df.format(num);
	}

	/**
	 * 字符串转为日期 2016-10-19 王为潮
	 * 
	 * @param str
	 *            分辨通用时间格式
	 * @return
	 */
	public static Date StrToDate(String str) {
		Date date = null;
		if (YESUtil.isEmpty(str))
			return null;
		try {
			date = DateUtil.parseDate(str);
		} catch (ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 数组转换成string（["1","2"]=>'1','2'）
	 * 
	 * @author huiqiang.yan 2016-11-7
	 * @param array
	 * @return
	 */
	public static String arrayToString(String[] array) {
		String value = "";
		for (String temp : array) {
			value += YESUtil.isEmpty(value) ? YESUtil.getQuotedstr(temp) : "," + YESUtil.getQuotedstr(temp);
		}
		return value;
	}

	/**
	 * List转为 sql查询in用的str
	 * 
	 * @author wwc
	 * 
	 * @param
	 * @return
	 */
	public static String lisToSqlStr(Collection<String> list) {
		String str = "", buff = "";
		for (String item : list) {
			str += buff + "'" + item + "'";
			buff = ",";
		}
		return str;
	}

	/**
	 * 日期天数的加减 wwc
	 * 
	 * @param date->要操作的日期
	 * @param addDay->增加/减少的天数
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date addDate(Date date, int addDay) {
		Calendar ca = Calendar.getInstance();
		ca.setTime(date);
		ca.add(ca.DATE, addDay);
		Date dt = ca.getTime();
		return dt;
	}

	/**
	 * 获取当月第一天
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getfirstday() {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = null;
		cale = Calendar.getInstance();
		// cale.setTime(date);
		cale.add(Calendar.MONTH, 0);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		cale.set(Calendar.HOUR_OF_DAY, 00);
		cale.set(Calendar.MINUTE, 0);
		cale.set(Calendar.SECOND, 0);
		cale.set(Calendar.MILLISECOND, 00);
		return cale.getTime();
	}

	/**
	 * 获取当月最后一天
	 * 
	 * @return
	 */
	@SuppressWarnings("static-access")
	public static Date getlastday() {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cale = null;
		cale = Calendar.getInstance();
		// cale.setTime(date);
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		cale.set(Calendar.HOUR_OF_DAY, 23);
		cale.set(Calendar.MINUTE, 59);
		cale.set(Calendar.SECOND, 59);
		cale.set(Calendar.MILLISECOND, 59);
		cale.set(Calendar.PM, 1);
		return cale.getTime();
	}

	/**
	 * 获取当年的第一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearFirst() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearFirst(currentYear);
	}

	/**
	 * 获取当年的最后一天
	 * 
	 * @param year
	 * @return
	 */
	public static Date getCurrYearLast() {
		Calendar currCal = Calendar.getInstance();
		int currentYear = currCal.get(Calendar.YEAR);
		return getYearLast(currentYear);
	}

	/**
	 * 获取某年第一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearFirst(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		Date currYearFirst = calendar.getTime();
		return currYearFirst;
	}

	/**
	 * 获取某年最后一天日期
	 * 
	 * @param year
	 *            年份
	 * @return Date
	 */
	public static Date getYearLast(int year) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.YEAR, year);
		calendar.roll(Calendar.DAY_OF_YEAR, -1);
		Date currYearLast = calendar.getTime();

		return currYearLast;
	}

	// 金额转换成大写
	public static String tocapitals(String s) {
		String[] numArr = new String[] { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
		String[] unitArr = new String[] { "圆", "拾", "佰", "仟", "萬", "拾", "佰", "仟", "億", "拾", "佰", "仟" };
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			int index = Integer.parseInt(String.valueOf(c));
			sb.append(numArr[index]);
			sb.append(unitArr[s.length() - 1 - i]);
		}
		String result = sb.toString();
		result = result.replaceAll("零[拾佰仟]", "零");
		result = result.replaceAll("零{2,}", "零");
		result = result.replaceAll("零萬", "萬");
		result = result.replaceAll("零億", "億");
		return result;
	}

	public static String randomNum(int i) {
		String number = "";
		Random random = new Random();
		if (i <= 0) {
			number = "妈的智障";
		} else {
			for (int a = 0; a < i; a++) {
				number += YESUtil.toString(random.nextInt(9));
			}
		}
		return number;
	}

}
