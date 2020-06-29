package org.esy.base.dao;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.Position;

/**
 * 
 * Dao for 职务信息
 *
 */
public interface IPositionDao extends IBaseDao<Position> {

	/**
	 * @Description 根据组织编号删除
	 * @param oids
	 * @return
	 */
	public boolean deleteByOids(List<String> oids);

	/**
	 * @Description oid 查找对应的组织
	 * @param oid
	 * @return
	 */
	public List<Position> listByOid(String oid);

	/**
	 * @Description oids 查找对应的组织
	 * @param oid
	 * @return
	 */
	public List<Position> listByOids(List<String> oid, String rootpid, Boolean enable);

	/**
	 * @Description 计算Position个数，根据条件，用于判断新增修改是否name重复
	 * @param oid
	 * @param name
	 * @param uid
	 * @return
	 */
	public long countByNameAndOid(String oid, String name, String pid, String uid, String eid);

	/**
	 * @Description 拖动，移动有排序的表的位置
	 * @param classname
	 * @param eid
	 * @param begin
	 * @param end
	 * @returns
	 */
	public boolean changeLocation(Map<String, Object> parm, String classname, int begin, int end);

	/**
	 * @Description 查找uid
	 * @param classname
	 * @param seq
	 * @returns
	 */
	public String getByEidAndSeq(Map<String, Object> parm, String classname, int seq);

	/**
	 * @Description 改变该uid的seq值
	 * @param
	 * @param
	 * @return
	 */
	public boolean updateSeq(String classname, String uid, int seq);

	/**
	 * @Description 取得最大的seq值
	 * @param
	 * @param
	 * @return
	 */
	public Integer getMaxSeq(String eid, String oid);

	/**
	 * @Description 获得uid的seq
	 * @param className
	 * @param uid
	 * @return
	 */
	public Integer getSeqFromObject(String className, String uid);

	/**
	 * 
	 * @param pid
	 * @return
	 */
	public Position getByPid(String pid, String eid);

}
