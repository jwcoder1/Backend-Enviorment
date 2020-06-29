package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.dao.IRulesVariablesDao;
import org.esy.base.entity.RulesVariables;
import org.esy.base.service.IRulesVariablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 
 * 规则变量服务层
 *
 * @author cx
 * @date 2016年9月14日 下午4:54:36 
 * @version v1.0
 */
@Service
public class RulesVariablesServiceImpl implements IRulesVariablesService {

	@Autowired
	private IRulesVariablesDao rulesVariablesDao;

	/**
	 * 
	 * 根据规则uid获取所有规则变量
	 * @author cx 2016年9月14日 下午4:51:23
	 * @param rulesUid
	 * @return
	 */
	@Override
	public List<RulesVariables> getAllByRulesUid(String rulesUid) {
		List<RulesVariables> ls = rulesVariablesDao.getAllByRulesUid(rulesUid);
		return BaseUtil.isEmpty(ls) ? new ArrayList<RulesVariables>() : ls;
	}

	/**
	 * 
	 * 根据规则uid获取所有规则变量封装成map(key=name,value=value)
	 * @author cx 2016年9月14日 下午4:58:59
	 * @param rulesUid
	 * @return
	 */
	@Override
	public Map<String, Object> getVariables(String rulesUid) {
		List<RulesVariables> ls = getAllByRulesUid(rulesUid);
		Map<String, Object> variables = new HashMap<String, Object>();
		for (RulesVariables rulesVariables : ls) {
			variables.put(rulesVariables.getName(), rulesVariables.getValue());
		}
		return variables;
	}

}
