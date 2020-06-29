package org.esy.base.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.dao.IRulesVariablesDao;
import org.esy.base.entity.RulesVariables;
import org.esy.base.util.YESUtil;
import org.springframework.stereotype.Repository;

/**
 * 
 * 规则变量数据层
 *
 * @author cx
 * @date 2016年9月14日 下午4:09:07 
 * @version v1.0
 */
@Repository
public class RulesVariablesDaoImpl implements IRulesVariablesDao {
	@PersistenceContext
	private EntityManager em;

	/**
	 * 
	 * 创建、更新规则变量
	 * @author cx 2016年9月14日 上午10:35:35
	 * @param rulesVariables
	 * @return
	 */
	@Override
	public RulesVariables save(RulesVariables rulesVariables) {
		if (rulesVariables.checkNew()) {
			rulesVariables.setUid(YESUtil.getUuid());
			this.em.persist(rulesVariables);
		} else {
			rulesVariables = this.em.merge(rulesVariables);
		}
		return rulesVariables;
	}

	/**
	 * 
	 * 删除规则变量
	 * @author cx 2016年9月14日 上午10:37:00
	 * @param rulesVariables
	 */
	@Override
	public void delete(RulesVariables rulesVariables) {
		em.remove(rulesVariables);
	}

	/**
	 * 
	 * 根据规则uid获取所有规则变量
	 * @author cx 2016年9月14日 下午4:51:23
	 * @param rulesUid
	 * @return
	 */
	@Override
	public List<RulesVariables> getAllByRulesUid(String rulesUid) {
		String hql = "from RulesVariables where rulesUid =:rulesUid";
		return em.createQuery(hql, RulesVariables.class).setParameter("rulesUid", rulesUid).getResultList();
	}

}
