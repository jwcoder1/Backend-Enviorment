package org.esy.base.dao;

import java.util.List;

import org.esy.base.entity.Rules;
import org.esy.base.entity.pojo.RulesPojo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * 
 * 规则库数据层接口
 *
 * @author cx
 * @date 2016年9月14日 上午10:29:17 
 * @version v1.0
 */
public interface IRulesDao {

	/**
	 * 
	 * 创建、更新规则库
	 * @author cx 2016年9月14日 上午10:35:35
	 * @param rules
	 * @return
	 */
	Rules save(Rules rules);

	/**
	 * 
	 * 删除规则
	 * @author cx 2016年9月14日 上午10:37:00
	 * @param rules
	 */
	void delete(Rules rules);

	/**
	 * 
	 * 根据多个uid批量删除规则
	 * @author cx 2016年9月14日 上午10:37:00
	 * @param rules
	 */
	void deleteByUids(List<String> uids);

	/**
	 * 
	 * 根据主键取规则
	 * @author cx 2016年9月14日 上午10:43:29
	 * @param uid
	 * @return
	 */
	Rules getByUid(String uid);

	/**
	 * 
	 * 分页查询
	 * @author cx 2016年9月14日 下午2:07:00
	 * @param rules
	 * @param pageable
	 * @return
	 */
	Page<RulesPojo> query(Rules rules, Pageable pageable);

	/**
	 * 
	 * 根据编号获取规则
	 * @author cx 2016年9月14日 下午2:50:29
	 * @param no
	 * @return
	 */
	Rules getByNo(String no);

	/**
	 * 
	 * 获取所有定时器规则与没停用
	 * @author cx 2016年9月14日 下午3:33:57
	 * @return
	 */
	List<Rules> getAllTimesAndNotDisable();

}
