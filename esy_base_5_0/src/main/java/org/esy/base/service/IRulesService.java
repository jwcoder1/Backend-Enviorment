package org.esy.base.service;

import java.util.Map;

/**
 * 规则库服务层接口
 *
 * @author cx
 * @date 2016年9月14日 下午5:19:04
 * @version v1.0
 */
public interface IRulesService {

	/**
	 * 
	 * 根据规则编号执行规则脚本
	 * 
	 * @author cx 2016年9月14日 下午5:10:13
	 * @param no
	 */
	void evalScript(String no);

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
	Object evalScript(String no, Map<String, Object> variables);

	/**
	 * 
	 * 根据规则主键执行脚本
	 * 
	 * @author cx 2016年9月14日 下午5:16:08
	 * @param uid
	 * @param script
	 */
	void evalScriptByUid(String uid, String script);

	/**
	 * Description: 根据规则主键执行脚本
	 * 
	 * @param uid
	 * @param script
	 * @param variables
	 *            参数
	 * @return 脚本值
	 */
	Object evalScriptByUid(String uid, String script, Map<String, Object> variables);

}
