package org.esy.base.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 
 * 配置文件值读取工具
 * @author Victor 2014/11/12
 *
 */
public class PropertiesUtils {

	private Properties properties = new Properties();

	private String fileName = "";

	/**
	 * 创建配置文件读取工具
	 * @param fileName 配置文件名，放置与 classpath 根目录
	 * @throws IOException
	 */
	public PropertiesUtils(String fileName) throws IOException {
		this.fileName = fileName;
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(this.fileName);
		properties.load(inputStream);
	}

	/**
	 * 创建配置文件读取工具，读取默认配置文件 config.properties
	 * @throws IOException
	 */
	public PropertiesUtils() throws IOException {
		this.fileName = "config.properties";
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileName);
		properties.load(inputStream);
	}

	/**
	 * 
	 * @param propertyName
	 * @return 返回配置值
	 */
	public String getProperty(String propertyName) {
		return properties.getProperty(propertyName);
	}

}
