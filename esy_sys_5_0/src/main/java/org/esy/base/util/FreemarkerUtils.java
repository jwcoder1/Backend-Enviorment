package org.esy.base.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class FreemarkerUtils {

	public static void main(String[] args) {

		String sysPath = System.getProperty("user.dir");
		System.out.println("System path is : " + sysPath);

		String templatePath = sysPath + "\\src\\main\\resources\\CodeGeneration\\template";
		System.out.println("Template path is : " + templatePath);

		String outputPath = sysPath + "\\src\\main\\resources\\CodeGeneration\\output";
		System.out.println("Output path is : " + outputPath);

		String configPath = sysPath + "\\src\\main\\resources\\CodeGeneration\\config";
		System.out.println("Config pth is :" + configPath);

		String configFileName = "CodeGenerationSample.json";
		String configFile = configPath + File.separator + configFileName;
		System.out.println("Config file is " + configFile);

		StringBuffer stringBuffer = new StringBuffer();
		String line = null;
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(configFile)));
			while ((line = bufferedReader.readLine()) != null) {
				stringBuffer.append(line);
			}
			bufferedReader.close();

			JSONObject config = JSONObject.fromObject(stringBuffer.toString());

			Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
			configuration.setDirectoryForTemplateLoading(new File(templatePath));
			configuration.setDefaultEncoding("utf-8");

			String objectName = config.getString("name");

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("config", config); // 完整配置文件

			ArrayList<String> types = new ArrayList<String>(); // 取得所有数据类型
			ArrayList<String> indexes = new ArrayList<String>(); // 取得所有需要索引的字段
			JSONObject entity = config.getJSONObject("entity");
			JSONArray properties = entity.getJSONArray("properties");
			for (int i = 0; i < properties.size(); i++) {
				JSONObject property = properties.getJSONObject(i);
				if (!types.contains(property.getString("type"))) {
					types.add(property.getString("type"));
				}
				if (property.getBoolean("index")) {
					indexes.add(property.getString("field"));
				}
			}
			map.put("types", types);
			map.put("indexes", indexes);

			System.out.println("=================");

			JSONArray templates = config.getJSONArray("templates");
			for (int i = 0; i < templates.size(); i++) {
				String template = templates.getString(i);
				String outputFolder = outputPath + File.separator
						+ (config.getString("package") + "." + template).replace(".", File.separator);

				String outputFileName;
				if ("dao".equals(template)) {
					outputFileName = "I" + objectName + "Dao.java";
				} else if ("dao.impl".equals(template)) {
					outputFileName = objectName + "DaoImpl.java";
				} else if ("service".equals(template)) {
					outputFileName = "I" + objectName + "Service.java";
				} else if ("service.impl".equals(template)) {
					outputFileName = objectName + "ServiceImpl.java";
				} else if ("controller".equals(template)) {
					outputFileName = objectName + "Controller.java";
				} else {
					outputFileName = objectName + ".java";
				}
				String outputFile = outputFolder + File.separator + outputFileName;
				System.out.println("Output file is : " + outputFile);

				File f = new File(outputFile);
				if (!f.getParentFile().exists()) {
					f.getParentFile().mkdirs();
				}
				if (!f.exists()) {
					try {
						f.createNewFile();
						System.out.println("Creating output file : " + outputFile);
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else {
					System.out.println("Replacing output file : " + outputFile);
				}

				Writer writer = new OutputStreamWriter(new FileOutputStream(f), "utf-8");
				Template t = configuration.getTemplate(template + ".ftl");

				// t.process(map, new OutputStreamWriter(System.out));
				t.process(map, writer);
				writer.close();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
