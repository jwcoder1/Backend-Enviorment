package org.esy.base.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSourceStatLogger;
import com.alibaba.druid.pool.DruidDataSourceStatLoggerImpl;
import com.alibaba.druid.pool.DruidDataSourceStatValue;
import com.alibaba.druid.stat.DruidStatService;

/**
 * 
 * @Title: 日志监听记录
 * @Description: 
 * @author: duiqing.huang
 * @date: 2014年11月13日 下午5:50:06
 * @version: V1.0
 * 
 */
public class StatLogger extends DruidDataSourceStatLoggerImpl implements DruidDataSourceStatLogger {

	private static final Logger log = LoggerFactory.getLogger(StatLogger.class);

	private final DruidStatService statService = DruidStatService.getInstance();

	private final String[] url = { "/datasource.json", "/sql.json", "/wall.json", "/weburi.json", "/webapp.json",
			"/spring.json", "/websession.json" };

	private final String[] names = { "数据源", "SQL监控", "SQL防火墙", "URI监控", "Web应用", "spring监控", "Session监控" };

	private int len = 0;

	public StatLogger() {
		len = url.length;
	}

	@Override
	public void log(DruidDataSourceStatValue statValue) {
		for (int i = 0; i < len; i++) {
			//log.info("==========================[" + names[i] + "]=============================");
			//log.info(statService.service(url[i]));
		}
	}
}
