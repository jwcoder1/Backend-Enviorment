package org.esy.base.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import net.sf.json.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/***
 * Http 数据处理
 * 
 * @author Victor 2013.10.26
 * 
 */
public class HttpUtil {
	/**
	 * 取得 GET 数据
	 * 
	 * @param urlstring
	 *            URL
	 * @return
	 */
	public static String get(String urlstring) {

		String body = "";
		URL url;
		HttpURLConnection conn;
		try {
			url = new URL(urlstring);
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setUseCaches(false);

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				body = body + inputLine;
			}
			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			body = "ERROR GET: (msg=" + e.getMessage() + "; url=" + urlstring + ")";
			System.out.println(body);
		}

		return body;
	}

	private static final Log log = LogFactory.getLog(HttpUtil.class);

	private static final String ENCODING = "UTF-8";

	private static final String JSON_CONTENT_TYPE = "application/json;charset=UTF-8";

	private static final String ERROR_JSON;

	static {
		final Map<String, String> result = new HashMap<String, String>(3);
		result.put("errCode", "8");
		result.put("errMsg", "post连接失败");
		result.put("transactionId", "");
		ERROR_JSON = JSONObject.fromObject(result).toString();
	}

	public static void main(String[] args) throws Exception {
		final Map<String, String> result = new HashMap<String, String>(3);
		result.put("errCode", "8");
		result.put("errMsg", "post连接失败");
		result.put("transactionId", "");
		System.out
				.println(postUsingJson("http://172.27.35.28:8080/test/111", JSONObject.fromObject(result).toString()));
	}

	public static String postUsingJson(String url, String json) throws Exception {
		try {
			final HttpClient client = HttpClientBuilder.create().build();
			final HttpPost post = new HttpPost(url);
			post.setHeader("Accept-Charset", ENCODING);
			post.setHeader("Accept", "application/json");
			post.setHeader("Content-Type", JSON_CONTENT_TYPE);
			post.setEntity(new StringEntity(json == null ? "" : json, ENCODING));
			// [START]设置超时[/START]
			RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();// 设置请求和传输超时时间
			post.setConfig(config);
			// [CLOSE]设置超时[/CLOSE]
			final HttpResponse response = client != null ? client.execute(post) : null;
			final StatusLine statusLine = response != null ? response.getStatusLine() : null;
			final int statusCode = statusLine != null ? statusLine.getStatusCode() : -1;
			log.debug("本次请求的响应代码为：" + statusCode);
			if (HttpStatus.SC_OK == statusCode) { // 判断响应代码是否为200
				return IOUtils.toString(response.getEntity().getContent(), ENCODING);
			}
		} catch (Exception e) {
			// log.error(e.getMessage(), e);
			// throw e;
		}
		return ERROR_JSON;
	}

	/**
	 * 取得 URL Post 数据
	 * 
	 * @param urlstring
	 *            URL
	 * @param parm
	 *            POST 参数
	 * @return
	 */
	public static String post(String urlstring, String parm) {

		String body = "";
		try {
			URL url = new URL(urlstring);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoOutput(true);// 是否输入参数
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式
			StringBuffer params = new StringBuffer();
			params.append(parm);// 表单参数与get形式一样
			byte[] bytes = params.toString().getBytes("UTF-8");
			conn.getOutputStream().write(bytes);// 输入参数

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				body = body + inputLine;
			}

			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			body = "{\"errCode\":8,\"errMsg\":\"post连接失败\",\"transactionId\":\"\"}";
			System.out.println(body);

		}
		return body;

	}

	/**
	 * 取得 URL Post 数据
	 * 
	 * @param urlstring
	 *            URL
	 * @param parm
	 *            POST 参数
	 * @return
	 */
	public static String postbyuser(String urlstring, String parm, String userName, String passWord) {

		String body = "";
		try {
			URL url = new URL(urlstring);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoOutput(true);// 是否输入参数
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式
			String userpass = userName + ":" + passWord;
			String encoding = new sun.misc.BASE64Encoder().encode(userpass.getBytes());
			conn.setRequestProperty("Authorization", "Basic " + encoding);

			StringBuffer params = new StringBuffer();
			params.append(parm);// 表单参数与get形式一样
			byte[] bytes = params.toString().getBytes("UTF-8");
			conn.getOutputStream().write(bytes);// 输入参数

			InputStream ggg = conn.getInputStream();

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(ggg, "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				body = body + inputLine;
			}

			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			body = "{\"errCode\":8,\"errMsg\":\"post连接失败\",\"transactionId\":\"\"}";
			System.out.println(body);

		}
		return body;

	}

	/**
	 * 取得 URL Post 数据
	 * 
	 * @param urlstring
	 *            URL
	 * @param parm
	 *            POST 参数
	 * @return
	 */
	public static String postbyuserAndJson(String urlstring, String json, String userName, String passWord) {

		String body = "";
		try {
			URL url = new URL(urlstring);
			HttpURLConnection conn;
			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setDoOutput(true);// 是否输入参数
			conn.setRequestProperty("Accept-Charset", "UTF-8");
			conn.setRequestProperty("Accept", "application/json"); // 设置接收数据的格式
			conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8"); // 设置发送数据的格式
			String userpass = userName + ":" + passWord;
			String encoding = new sun.misc.BASE64Encoder().encode(userpass.getBytes());
			conn.setRequestProperty("Authorization", "Basic " + encoding);

			// 往服务器里面发送数据
			byte[] writebytes = json.getBytes();
			// 设置文件长度
			conn.setRequestProperty("Content-Length", String.valueOf(writebytes.length));
			OutputStream outwritestream = conn.getOutputStream();
			outwritestream.write(json.getBytes());
			outwritestream.flush();
			outwritestream.close();

			InputStream ggg = conn.getInputStream();

			BufferedReader in;
			in = new BufferedReader(new InputStreamReader(ggg, "UTF-8"));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				body = body + inputLine;
			}

			conn.getInputStream().close();
		} catch (Exception e) {
			e.printStackTrace();
			body = "{\"errCode\":8,\"errMsg\":\"post连接失败\",\"transactionId\":\"\"}";
			System.out.println(body);

		}
		return body;

	}

	public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

	public static String okhttppost(String url, String json) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		OkHttpClient client = new OkHttpClient();// 创建OkHttpClient对象。
		Request request = new Request.Builder().url(url).post(body).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	public static String okhttppost(String url, String json, String token) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		OkHttpClient client = new OkHttpClient();// 创建OkHttpClient对象。
		Request request = new Request.Builder().url(url).post(body).addHeader("token", token).build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	public static String okhttppost(String url, String json, String userName, String passWord) throws IOException {
		RequestBody body = RequestBody.create(JSON, json);
		OkHttpClient client = new OkHttpClient();// 创建OkHttpClient对象。
		String userpass = userName + ":" + passWord;
		String encoding = new sun.misc.BASE64Encoder().encode(userpass.getBytes());
		Request request = new Request.Builder().url(url).post(body).addHeader("Authorization", "Basic " + encoding)
				.build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

	public static String okhttget(String url, String userName, String passWord) throws IOException {
		OkHttpClient client = new OkHttpClient();// 创建OkHttpClient对象。
		String userpass = userName + ":" + passWord;
		String encoding = new sun.misc.BASE64Encoder().encode(userpass.getBytes());
		Request request = new Request.Builder().url(url).addHeader("Authorization", "Basic " + encoding).get().build();
		Response response = client.newCall(request).execute();
		if (response.isSuccessful()) {
			return response.body().string();
		} else {
			throw new IOException("Unexpected code " + response);
		}
	}

}
