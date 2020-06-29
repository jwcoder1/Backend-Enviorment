package org.esy.base.core;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.esy.base.common.BaseUtil;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

public class GapResourceBundleMessageSource extends ResourceBundleMessageSource {

	@Override
	public void setBasenames(final String... basenames) {
		List<String> list = new LinkedList<String>();
		if (ArrayUtils.isNotEmpty(basenames)) {
			for (String baseName : basenames) {
				try {
					Resource[] resources = BaseUtil.getResources(ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
							+ baseName.replace('.', '/') + "/*.properties");
					for (Resource resource : resources) {
						String fileName = baseName
								+ "."
								+ StringUtils.substringBeforeLast(
										StringUtils.substringBeforeLast(resource.getFilename(), "_"), "_");
						if (list.indexOf(fileName) > -1) {
							continue;
						}
						list.add(fileName);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String[] bases = new String[list.size()];
		list.toArray(bases);
		list.clear();
		list = null;
		for (String string : bases) {
			System.out.println("language package import : " + string);
		}
		super.setBasenames(bases);
	}

}
