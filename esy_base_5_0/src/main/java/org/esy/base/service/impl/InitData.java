package org.esy.base.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.esy.base.service.IImportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Component
public class InitData {

	@Value("${init.data}")
	private boolean init;

	@Value("${init.squence}")
	private String squence;

	@Autowired
	private IImportService iImportService;

	@PostConstruct
	public void init() throws Exception {
		if (init) {
			System.out.println("init.data = " + init);

			ResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();

			Map<String, String> allData = new HashMap<String, String>();

			String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath("org.esy.*.init") + "/" + "*.json";
			Resource[] resources = resourcePatternResolver.getResources(packageSearchPath);
			for (Resource resource : resources) {
				System.out.println("Scanning init data file : " + resource.getFilename());
				BufferedReader br = new BufferedReader(new InputStreamReader(resource.getInputStream(), "utf-8"));
				String trs = "";
				String jsonStr = "";
				while ((trs = br.readLine()) != null) {
					jsonStr += trs;
				}
				allData.put(resource.getFilename(), jsonStr);

				br.close();
			}

			String[] jsons = squence.split(";");
			String jsonData;
			for (String json : jsons) {
				System.out.println("process init data file : = " + json);
				jsonData = allData.get(json);
				if (jsonData != null) {
					processData(jsonData);
				} else {
					System.out.println("init data : [" + json + "] no found.");
				}

			}
		} else {
			System.out.println("init.data = " + init);
		}
	}

	private void processData(String jsonData) throws Exception {

		JSONObject initData = JSONObject.fromObject(jsonData);
		if (initData.has("execute")) {
			JSONArray execute = initData.getJSONArray("execute");
			for (int i = 0; i < execute.size(); i++) {
				JSONObject obj = execute.getJSONObject(i);
				System.out.println("execute hql [" + obj.getString("summary") + "] : " + obj.getString("hql"));
				iImportService.executeHql(obj.getString("hql"));
			}
		}

		if (initData.has("import")) {
			JSONArray importData = initData.getJSONArray("import");
			for (int i = 0; i < importData.size(); i++) {
				JSONObject obj = importData.getJSONObject(i);
				System.out.println("json insert [" + obj.getString("classname") + "] : " + obj.getString("summary"));
				iImportService.saveJson(obj.getString("classname"), obj.getJSONObject("entity").toString());
			}
		}

	}
}
