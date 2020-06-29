package org.esy.base.dao.impl;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.esy.base.common.BaseUtil;
import org.esy.base.common.RulesType;
import org.esy.base.dao.IRulesDao;
import org.esy.base.dao.core.PageQuery;
import org.esy.base.entity.Rules;
import org.esy.base.entity.pojo.RulesPojo;
import org.esy.base.util.YESUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

/**   
 * 规则库数据层
 *
 * @author cx
 * @date 2016年9月14日 上午10:30:28 
 * @version v1.0   
 */
@Repository
public class RulesDaoImpl implements IRulesDao {

	@PersistenceContext
	private EntityManager em;

	@Resource
	private PageQuery pageQuery;

	/**
	 * 
	 * 创建、更新规则库
	 * @author cx 2016年9月14日 上午10:35:35
	 * @param rules
	 * @return
	 */
	@Override
	public Rules save(Rules rules) {
		if (rules.checkNew()) {
			rules.setUid(YESUtil.getUuid());
			this.em.persist(rules);
		} else {
			rules = this.em.merge(rules);
		}
		return rules;
	}

	/**
	 * 
	 * 删除规则
	 * @author cx 2016年9月14日 上午10:37:00
	 * @param rules
	 */
	@Override
	public void delete(Rules rules) {
		em.remove(rules);
	}

	/**
	 * 
	 * 根据多个uid批量删除规则
	 * @author cx 2016年9月14日 上午10:37:00
	 * @param rules
	 */
	@Override
	public void deleteByUids(List<String> uids) {
		String hql = "delete from Rules where uid in (:uids)";
		int result = em.createQuery(hql).setParameter("uids", uids).executeUpdate();
		System.out.println("删除了规则库" + result + "条");
	}

	/**
	 * 
	 * 根据主键取规则
	 * @author cx 2016年9月14日 上午10:43:29
	 * @param uid
	 * @return
	 */
	@Override
	public Rules getByUid(String uid) {
		return em.find(Rules.class, uid);
	}

	/**
	 * 
	 * 根据编号获取规则
	 * @author cx 2016年9月14日 下午2:50:29
	 * @param no
	 * @return
	 */
	@Override
	public Rules getByNo(String no) {
		String hql = "from Rules where no=:no";
		List<Rules> rules = em.createQuery(hql, Rules.class).setParameter("no", no).getResultList();
		return BaseUtil.isEmpty(rules) ? null : rules.get(0);
	}

	/**
	 * 
	 * 获取所有定时器规则与没停用
	 * @author cx 2016年9月14日 下午3:33:57
	 * @return
	 */
	@Override
	public List<Rules> getAllTimesAndNotDisable() {
		String hql = "from Rules where state=true and rulesType=:rulesType";
		return em.createQuery(hql, Rules.class).setParameter("rulesType", RulesType.TIMES).getResultList();
	}

	/**
	 * 
	 * 分页查询
	 * @author cx 2016年9月14日 下午2:07:00
	 * @param rules
	 * @param pageable
	 * @return
	 */
	@Override
	public Page<RulesPojo> query(Rules rules, Pageable pageable) {
		pageQuery.append("select new org.yessoft.base.entity.pojo.RulesPojo(");
		pageQuery.append("r.uid,r.no,r.name,r.rulesType,r.rulesTime,r.state,");
		pageQuery.append("(select d.text from DicDetail d where d.uid = r.category))");
		pageQuery.append(" from Rules r where");

		//分类筛选
		if (BaseUtil.isNotEmpty(rules.getCategory())) {
			pageQuery.append("r.category=:category").setParam("category", rules.getCategory());
		}
		//名称筛选
		if (BaseUtil.isNotEmpty(rules.getName())) {
			pageQuery.append("and r.name like :name").setParam("name", BaseUtil.toLikeString(rules.getName()));
		}
		//状态筛选
		if (BaseUtil.isNotEmpty(rules.getState())) {
			pageQuery.append("and r.state=:state").setParam("state", rules.getState());
		}
		//类型筛选
		if (BaseUtil.isNotEmpty(rules.getRulesType())) {
			pageQuery.append("and r.rulesType=:rulesType").setParam("rulesType", rules.getRulesType());
		}
		return pageQuery.getPage(pageable, RulesPojo.class);
	}
}
