package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.entity.RulesVariables;

/**
 * 
 * 规则变量服务层接口
 *
 * @author cx
 * @date 2016年9月14日 下午4:37:54 
 * @version v1.0
 */
public interface IRulesVariablesService {

	/**
	 * 
	 * 根据规则uid获取所有规则变量
	 * @author cx 2016年9月14日 下午4:51:23
	 * @param rulesUid
	 * @return
	 */
	List<RulesVariables> getAllByRulesUid(String rulesUid);

	/**
	 * 
	 * 根据规则uid获取所有规则变量封装成map(key=name,value=value)
	 * @author cx 2016年9月14日 下午4:58:59
	 * @param rulesUid
	 * @return
	 */
	Map<String, Object> getVariables(String rulesUid);

}
