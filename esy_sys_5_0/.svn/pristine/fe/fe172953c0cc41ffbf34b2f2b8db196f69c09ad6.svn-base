package org.esy.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpPost {

	private static int connectTimeOut = 5000;

	private static int readTimeOut = 10000;

	private static String requestEncoding = "UTF-8";

	public static int getConnectTimeOut() {
		return connectTimeOut;
	}

	public static void setConnectTimeOut(int connectTimeOut) {
		HttpPost.connectTimeOut = connectTimeOut;
	}

	public static int getReadTimeOut() {
		return readTimeOut;
	}

	public static void setReadTimeOut(int readTimeOut) {
		HttpPost.readTimeOut = readTimeOut;
	}

	public static String getRequestEncoding() {
		return requestEncoding;
	}

	public static void setRequestEncoding(String requestEncoding) {
		HttpPost.requestEncoding = requestEncoding;
	}

	public static String doGet(String requrl, Map<?, ?> parameters, String recvEndcoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		String vchartset = recvEndcoding == "" ? HttpPost.requestEncoding : recvEndcoding;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), vchartset));
				params.append("&");
			}
			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}
			URL url = new URL(requrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			System.setProperty("连接超时：", String.valueOf(HttpPost.connectTimeOut));
			System.setProperty("访问超时：", String.valueOf(HttpPost.readTimeOut));
			url_con.setDoOutput(true);//
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			byte[] echo = new byte[10 * 1024];
			int len = in.read(echo);
			responseContent = (new String(echo, 0, len).trim());
			int code = url_con.getResponseCode();
			if (code != 200) {
				responseContent = "ERROR" + code;
			}
		} catch (Exception e) {
			System.out.println("网络故障:" + e.toString());
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;

	}

	public String doGet(String reqUrl, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		String vchartset = recvEncoding == "" ? HttpPost.requestEncoding : recvEncoding;
		try {
			StringBuffer params = new StringBuffer();
			String queryUrl = reqUrl;
			int paramIndex = reqUrl.indexOf("?");

			if (paramIndex > 0) {
				queryUrl = reqUrl.substring(0, paramIndex);
				String parameters = reqUrl.substring(paramIndex + 1, reqUrl.length());
				String[] paramArray = parameters.split("&");
				for (int i = 0; i < paramArray.length; i++) {
					String string = paramArray[i];
					int index = string.indexOf("=");
					if (index > 0) {
						String parameter = string.substring(0, index);
						String value = string.substring(index + 1, string.length());
						params.append(parameter);
						params.append("=");
						params.append(URLEncoder.encode(value, vchartset));
						params.append("&");
					}
				}

				params = params.deleteCharAt(params.length() - 1);
			}
			URL url = new URL(queryUrl);
			url_con = (HttpURLConnection) url.openConnection();
			url_con.setRequestMethod("GET");
			System.setProperty("sun.net.client.defaultConnectTimeout", String.valueOf(HttpPost.connectTimeOut));
			System.setProperty("sun.net.client.defaultReadTimeout", String.valueOf(HttpPost.readTimeOut));
			url_con.setDoOutput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();
			InputStream in = url_con.getInputStream();
			byte[] echo = new byte[10 * 1024];
			int len = in.read(echo);
			responseContent = (new String(echo, 0, len)).trim();
			int code = url_con.getResponseCode();
			if (code != 200) {
				responseContent = "ERROR" + code;
			}
		} catch (Exception e) {
			System.out.println("网络故障:" + e.toString());
		} finally {
			if (url_con != null) {
				url_con.disconnect();
			}
		}
		return responseContent;

	}

	public String doPost(String reqUrl, Map<String, String> parameters, String recvEncoding) {
		HttpURLConnection url_con = null;
		String responseContent = null;
		String vchartset = recvEncoding == "" ? HttpPost.requestEncoding : recvEncoding;
		try {
			StringBuffer params = new StringBuffer();
			for (Iterator<?> iter = parameters.entrySet().iterator(); iter.hasNext();) {
				Entry<?, ?> element = (Entry<?, ?>) iter.next();
				params.append(element.getKey().toString());
				params.append("=");
				params.append(URLEncoder.encode(element.getValue().toString(), vchartset));
				params.append("&");
			}

			if (params.length() > 0) {
				params = params.deleteCharAt(params.length() - 1);
			}

			URL url = new URL(reqUrl);//Post请求的url
			url_con = (HttpURLConnection) url.openConnection();//打开连接
			url_con.setRequestMethod("POST");
			url_con.setConnectTimeout(HttpPost.connectTimeOut);
			url_con.setReadTimeout(HttpPost.readTimeOut);
			//设置是否向connection输出,因为这个是post请求,参数要放在http正文内,因此需要设为true
			url_con.setDoOutput(true);
			//Read from the connection.Default is true.
			//url_con.setDoInput(true);
			byte[] b = params.toString().getBytes();
			url_con.getOutputStream().write(b, 0, b.length);
			url_con.getOutputStream().flush();
			url_con.getOutputStream().close();

			InputStream in = url_con.getInputStream();//发送服务器
			byte[] echo = new byte[10 * 1024];
			int len = in.read(echo);
			responseContent = (new String(echo, 0, len)).trim();
			int code = url_con.getResponseCode();
			if (code != 200) {
				responseContent = "ERROR" + code;
			}
		} catch (IOException e) {
			System.out.println("网络故障:" + e.toString());
		} finally {
			if (url_con != null) {
				url_con.disconnect(); // 断开连接
			}
		}
		return responseContent;
	}

	//
	//
	//	public static void main(String[] args) throws UnsupportedEncodingException {
	//		Map<String, String> map = new HashMap<String, String>();
	//		map.put("name", "nnt510");//此处填写用户账号
	//		map.put("pwd", "5589C0CE32E7E0C73975209212F5");//此处填写用户密码
	//		map.put("mobile","15259212142");//此处填写发送号码
	//		map.put("content","尊敬的XX客户您好，您的XX商品已经由【EMS】快递寄出，单号：888；999，共2件，以面单金额付款。如有疑问请致电您的客服手机13899999,zx货运站.");//此处填写模板短信内容
	//		map.put("sign", "亿实软件");
	//		map.put("type", "pt");
	//		map.put("extno", "123");
	//		//http://web.cr6868.com/asmx/smsservice.aspx?
	//		//	name=nnt510&pwd=5589C0CE32E7E0C73975209212F5&
	//		//	content=厦门旭盈国际货运提醒您A00001已接单&mobile=15005028226&sign=[旭盈物流]&type=pt&extno=123
	//		String temp = HttpPost.doPost("http://web.cr6868.com/asmx/smsservice.aspx",map, "UTF-8");
	//		System.out.println("值:" + temp);//此处为短信发送的返回值
	//	}
	//	
	public String formatIsToString(InputStream is) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = -1;
		try {
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			baos.flush();
			baos.close();
			is.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(baos.toByteArray(), "utf-8");
	}

	public String get(String apiUrl) throws Exception {
		String str = null;
		URL url = new URL(apiUrl);

		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setReadTimeout(5000);//将读超时设置为指定的超时，以毫秒为单位。用一个非零值指定在建立到资源的连接后从 Input 流读入时的超时时间。如果在数据可读取之前超时期满，则会引发一个 java.net.SocketTimeoutException。
		con.setDoInput(true);//指示应用程序要从 URL 连接读取数据。
		con.setRequestMethod("GET");//设置请求方式
		if (con.getResponseCode() == 200) {//当请求成功时，接收数据（状态码“200”为成功连接的意思“ok”）
			InputStream is = con.getInputStream();
			str = formatIsToString(is);
		}
		return str;
	}
}
