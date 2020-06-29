package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.Position;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * Dao for 职务信息
 *
 */
public interface IPositionService extends IBaseService<Position> {
	/**
	 * @Description oid 查找对应的组织
	 * @param oid
	 * @return
	 */
	public List<Position> listByOid(String oid);

	/**
	 * @Description 保存前检查是否可保存
	 * @param Position
	 * @return
	 */
	public MsgResult checkForSave(Position p);

	/**
	 * @Description 检查是否可删
	 * @param uid节点
	 * @return
	 */
	public MsgResult checkDeleteByUid(String uid);

	/**
	 * @Description 改变拖动的位置
	 * @param
	 * @return
	 */
	public MsgResult changeLocation(Map<String, Object> parm, String classname);

	/**
	 * 
	 * @param oids
	 * @param rootpid
	 * @param enable
	 * @return
	 */
	public List<Position> listByOids(List<String> oids, String rootpid, Boolean enable);

}
