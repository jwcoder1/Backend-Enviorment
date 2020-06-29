package org.esy.base.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.esy.base.common.BaseUtil;
import org.esy.base.dao.IRulesDao;
import org.esy.base.entity.Rules;
import org.esy.base.service.IRulesService;
import org.esy.base.service.IRulesVariablesService;
import org.esy.base.util.BeanshellUtil;
import org.esy.base.util.RulesJob;
import org.esy.base.util.SpringContextUtil;
import org.esy.base.util.timers.QuartzManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * 规则服务层
 *
 * @author cx
 * @date 2016年9月14日 上午9:30:59
 * @version v1.0
 */
@Service("rulesService")
public class RulesServiceImpl implements IRulesService {

	private final String F_JOBCLASS = RulesJob.class.getName();

	@Autowired
	private IRulesDao rulesDao;

	@Autowired
	private IRulesVariablesService rulesVarablesService;

	/**
	 * 
	 * spring 启动后调用
	 * 
	 * @author cx 2016年9月14日 上午9:52:32 // @PostConstruct --不要 启动定时器任务
	 */
	private void init() {
		// 添加定时器任务
		new Thread(new Runnable() {
			public void run() {
				addJobTimes();
			}
		}, "定时器线程").start();
	}

	/**
	 * 
	 * 创建、更新规则库
	 * 
	 * @author cx 2016年9月14日 上午10:35:35
	 * @param rules
	 * @return
	 */
	@Transactional
	public Rules save(Rules rules) {
		return rulesDao.save(rules);
	}

	/**
	 * 
	 * 根据主键
	 * 
	 * @author cx 2016年9月18日 上午10:00:10
	 * @param uid
	 * @return
	 */
	public Rules findRulesByUid(String uid) {
		return rulesDao.getByUid(uid);
	}

	/**
	 * 
	 * 根据规则编号执行规则脚本
	 * 
	 * @author cx 2016年9月14日 下午5:10:13
	 * @param no
	 */
	@Override
	public void evalScript(String no) {
		if (BaseUtil.isEmpty(no)) {
			return;
		}
		Rules rules = rulesDao.getByNo(no);
		if (rules == null) {
			return;
		}
		evalScriptByUid(rules.getUid(), rules.getScript());
	}

	/**
	 * 
	 * 根据规则主键执行脚本
	 * 
	 * @author cx 2016年9月14日 下午5:16:08
	 * @param uid
	 * @param script
	 */
	@Override
	public void evalScriptByUid(String uid, String script) {
		if (BaseUtil.isEmpty(uid) || BaseUtil.isEmpty(BaseUtil.toString(script).trim())) {
			return;
		}
		Map<String, Object> variables = getVariables(uid); // 获取变量
		BeanshellUtil.eval(script, variables); // 执行代码
	}

	/**
	 * 
	 * 获取规则变量
	 * 
	 * @author cx 2016年9月14日 下午5:07:58
	 * @param id
	 * @return
	 */
	private Map<String, Object> getVariables(String id) {
		if (rulesVarablesService == null) {
			rulesVarablesService = SpringContextUtil.getBean("rulesVarablesService", IRulesVariablesService.class);
		}
		return rulesVarablesService.getVariables(id);
	}

	/**
	 * 
	 * 定时器任务
	 * 
	 * @author cx 2016年9月14日 下午4:01:00
	 */
	private void addJobTimes() {
		List<Rules> ls = rulesDao.getAllTimesAndNotDisable();
		if (BaseUtil.isEmpty(ls)) {
			return;
		}
		Map<String, Object> params;
		for (Rules rules : ls) {
			String script = BaseUtil.toString(rules.getScript()).trim();
			if (BaseUtil.isEmpty(script)) {
				continue;
			}
			params = new HashMap<String, Object>();
			params.put("script", rules.getScript());
			QuartzManager.addJob(rules.getUid(), F_JOBCLASS, rules.getRulesTime(), params);
		}
	}

	/**
	 * 
	 * 添加定时器任务
	 * 
	 * @author yangkuiping 2016年9月26日 下午4:01:00
	 */
	public void addJobTime(String uid) {
		Rules rules = rulesDao.getByUid(uid);
		String script = BaseUtil.toString(rules.getScript()).trim();
		if (BaseUtil.isEmpty(script)) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("script", rules.getScript());
		QuartzManager.addJob(rules.getUid(), F_JOBCLASS, rules.getRulesTime(), params);
	}

	/**
	 * 
	 * 修改定时器任务
	 * 
	 * @author yangkuiping 2016年9月26日 下午4:01:00
	 */
	public void modifyJobTime(String uid) {
		Rules rules = rulesDao.getByUid(uid);
		String script = BaseUtil.toString(rules.getScript()).trim();
		if (BaseUtil.isEmpty(script)) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("script", rules.getScript());
		QuartzManager.modifyJobTime(rules.getUid(), rules.getRulesTime(), params);
	}

	/**
	 * 
	 * 移除定时器任务
	 * 
	 * @author yangkuiping 2016年9月26日 下午4:01:00
	 */
	public void removeJobTime(String uid) {
		Rules rules = rulesDao.getByUid(uid);
		String script = BaseUtil.toString(rules.getScript()).trim();
		if (BaseUtil.isEmpty(script)) {
			return;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("script", rules.getScript());
		QuartzManager.removeJob(rules.getUid());
	}

	/**
	 * 
	 * 根据规则编号执行规则脚本
	 * 
	 * @author cx 2016年9月14日 下午5:10:13
	 * @param no
	 * @param variables
	 *            参数
	 * @return 脚本值
	 * 
	 */
	@Override
	public Object evalScript(String no, Map<String, Object> variables) {
		if (BaseUtil.isEmpty(no)) {
			return null;
		}
		Rules rules = rulesDao.getByNo(no);
		if (rules == null) {
			return null;
		}
		return evalScriptByUid(rules.getUid(), rules.getScript(), variables);
	}

	/**
	 * Description: 根据规则主键执行脚本
	 * 
	 * @param uid
	 * @param script
	 * @param variables
	 *            参数
	 * @return 脚本值
	 */
	@Override
	public Object evalScriptByUid(String uid, String script, Map<String, Object> variables) {
		if (BaseUtil.isEmpty(uid) || BaseUtil.isEmpty(BaseUtil.toString(script).trim())) {
			return null;
		}
		return BeanshellUtil.eval(script, variables); // 执行代码
	}

}
