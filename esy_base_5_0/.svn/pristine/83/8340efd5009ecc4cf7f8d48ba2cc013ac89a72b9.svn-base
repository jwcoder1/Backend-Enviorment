package org.esy.base.util;

import org.esy.base.service.IRulesService;
import org.esy.base.util.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;

/**   
 * 
 * 规则定时器任务
 * @author cx
 * @date 2016年9月14日 下午5:06:17 
 * @version v1.0
 */
public class RulesJob implements Job {

	private static IRulesService rulesService;

	/**
	 * 规则定时器执行任务
	 */
	@Override
	public void execute(JobExecutionContext context) {
		String uid = context.getJobDetail().getName();
		String script = context.getJobDetail().getJobDataMap().getString("script");
		getIRulesService().evalScriptByUid(uid, script);
	}

	/**
	 * 
	 * 获取规则库接口
	 * @author cx 2016年9月14日 下午5:07:58
	 * @return
	 */
	private IRulesService getIRulesService() {
		if (rulesService == null) {
			rulesService = SpringContextUtil.getBean("rulesService", IRulesService.class);
		}
		return rulesService;
	}

}
