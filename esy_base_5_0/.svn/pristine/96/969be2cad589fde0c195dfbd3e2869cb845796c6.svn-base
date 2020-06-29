package org.esy.base.dao;

import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Post;

/**
 * 
 * Dao for 岗位信息
 *
 */
public interface IPostDao extends IBaseDao<Post> {

	/**
	 * @Description 计算Post数，根据条件，用于判断新增修改是否name重复
	 * @param oid
	 * @param name
	 * @param uid
	 * @return
	 */
	public long countByNameAndOid(String oid, String name, String pid, String uid, String eid);

	/**
	 * @Description 取得最大的seq值
	 * @param
	 * @param
	 * @return
	 */
	public Integer getMaxSeq(String eid, String oid);

	/**
	 * 组织ids 找到对应的可用岗位
	 * 
	 * @param oids
	 * @return
	 */
	public List<Post> listByOids(List<String> oids, String rootpid, Boolean enable);

	/**
	 * 
	 * @param pid
	 * @return
	 */
	public Post getByPid(String pid, String eid);

}
