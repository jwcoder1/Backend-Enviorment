package org.esy.base.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.esy.base.controller.AccountController;
import org.esy.base.entity.Account;
import org.esy.base.entity.dto.MenuDto;
import org.esy.base.security.service.ShiroRealm.ShiroUser;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 *
 */
@SuppressWarnings("rawtypes")
public class BASEUtil {

	private static String ACCESSKEYID;

	private static String ACCESSKEYSECRET;

	private static String SIGNNAME;

	static {
		Resource resource = new ClassPathResource("config.properties");
		Properties p = null;
		try {
			p = PropertiesLoaderUtils.loadProperties(resource);
			ACCESSKEYID = p.getProperty("dysms.id");
			ACCESSKEYSECRET = p.getProperty("dysms.secret");
			SIGNNAME = p.getProperty("dysms.signname");
		} catch (IOException e) {
			System.out.println("读取配置文件出错");
		}
	}

	/**
	 * 发送邮件功能,需在config.properties设置smtpHostName(smtp地址) & username(邮箱) &
	 * password(密码)
	 * 
	 * @param recipient
	 *            接收者
	 * @param title
	 *            标题
	 * @param content
	 *            内容
	 */
	public static boolean sendEmail(String recipient, String title, String content) {
		String smtpHostName = "", username = "", password = "";
		Resource resource = new ClassPathResource("config.properties");
		Properties p = null;
		try {
			p = PropertiesLoaderUtils.loadProperties(resource);
			smtpHostName = p.getProperty("smtpHostName");
			username = p.getProperty("username");
			password = p.getProperty("password");
		} catch (IOException e) {
			System.out.println("读取配置文件出错");
			return false;
		}
		SimpleMailSender sim = new SimpleMailSender(smtpHostName, username, password);
		try {
			String title_before = "", content_before = "";
			if (YESUtil.isNotEmpty(AccountController.projectName)) {
				title_before = "“" + AccountController.projectName + "”";
				content_before = AccountController.projectName + "：";
			}

			sim.send(recipient, title_before + title, content_before + content);
			return true;
		} catch (Exception e) {
			System.out.println("发送邮件失败,请检查config.properties的邮件配置!");
			return false;
		}
	}

	public static String randomNum(int i) {
		String code = "";
		for (int j = 0; j < i; j++) {
			code += (int) (Math.random() * 10);
		}
		return code;
	}

	/**
	 * 
	 * @param phone
	 *            手机号码
	 * @param templateCode
	 *            短信模板代号
	 * @param templateParam
	 *            短信模板参数
	 * @return
	 * @throws ClientException
	 */
	public static String sendMsg(String phone, String templateCode, String templateParam) throws ClientException {
		// 设置超时时间-可自行调整
		System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
		System.setProperty("sun.net.client.defaultReadTimeout", "10000");
		// 初始化ascClient需要的几个参数
		final String product = "Dysmsapi";// 短信API产品名称（短信产品名固定，无需修改）
		final String domain = "dysmsapi.aliyuncs.com";// 短信API产品域名（接口地址固定，无需修改）
		// 初始化ascClient,暂时不支持多region
		IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", ACCESSKEYID, ACCESSKEYSECRET);
		DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", product, domain);
		IAcsClient acsClient = new DefaultAcsClient(profile);
		// 组装请求对象
		SendSmsRequest request = new SendSmsRequest();
		// 使用post提交
		request.setMethod(MethodType.POST);
		// 必填:待发送手机号。支持以逗号分隔的形式进行批量调用，批量上限为1000个手机号码,批量调用相对于单条调用及时性稍有延迟,验证码类型的短信推荐使用单条调用的方式
		request.setPhoneNumbers(phone);
		// 必填:短信签名-可在短信控制台中找到
		request.setSignName(SIGNNAME);
		// 必填:短信模板-可在短信控制台中找到
		request.setTemplateCode(templateCode);
		// 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
		// 友情提示:如果JSON中需要带换行符,请参照标准的JSON协议对换行符的要求,比如短信内容中包含\r\n的情况在JSON中需要表示成\\r\\n,否则会导致JSON在服务端解析失败
		request.setTemplateParam(templateParam);
		// 上行短信扩展码
		String uid = UuidUtils.getUUID();

		request.setSmsUpExtendCode("123456");
		// 请求失败这里会抛ClientException异常
		SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);
		if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals("OK")) { // 请求成功
			return "";
		} else {
			return sendSmsResponse.getMessage();
		}
	}

}
