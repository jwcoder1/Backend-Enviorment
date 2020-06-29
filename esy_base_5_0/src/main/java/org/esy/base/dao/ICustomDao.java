package org.esy.base.dao;

public interface ICustomDao {

	/**
	 * 根据id找出对应的名称(通用方法)
	 * 
	 * @param EntityName
	 * @param id
	 * @return
	 */
	public String getNameById(String entityName, String name, String id, String idvalue);

	/**
	 * 同步应用分组和群组的企业分类显示名称
	 * 
	 * @param eid
	 * @param id
	 * @param value
	 * @return
	 */
	public boolean updateGroup(String eid, String id, String value);

	/**
	 * 表中是否有数据
	 * 
	 * @return
	 */
	public boolean exsitsInTable(String className, String field, String value);

}
