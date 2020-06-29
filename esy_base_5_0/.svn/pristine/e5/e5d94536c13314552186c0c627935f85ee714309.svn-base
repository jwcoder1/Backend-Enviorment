package org.esy.base.service;

import java.util.List;
import java.util.Map;

import org.esy.base.core.IBaseService;
import org.esy.base.entity.Identity;
import org.esy.base.entity.dto.IdentOrg;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.dto.PersonandIdentityDto;
import org.esy.base.entity.pojo.MsgResult;

/**
 * 
 * Dao for 身份信息
 *
 */
public interface IIdentityService extends IBaseService<Identity> {

	/**
	 * @Description 是否存在相关联的资料
	 * @param oid
	 *            组织id
	 * @param positionId
	 *            职务id
	 * @param postId
	 *            岗位id
	 * @return
	 */
	public boolean hadInConditions(Object... obj);

	/**
	 * 根据pid取到改用户所有的身份
	 * 
	 * @param pid
	 * @return
	 */
	public List<Identity> getByPid(String pid);

	/**
	 * 
	 * @Description 检查identitys是否可以保存
	 * @param identitys
	 * @return
	 */
	public MsgResult checkForSave(List<IdentityDto> identitys);

	/**
	 * @Description 根据人员编号删除
	 * @param pid
	 * @return
	 */
	public boolean deleteByPid(String pid);

	/**
	 * @Description 用用户pid查找身份的岗位名称
	 * @param
	 * @return
	 */
	public List<String> listPostNameByPid(String pid);

	/**
	 * @Description 用用户pid查找身份的职位名称
	 * @param
	 * @return
	 */
	public List<String> listPositionNameByPid(String pid);

	/**
	 * @Description 用用户pid查找身份的组织名称
	 * @param
	 * @return
	 */
	public List<String> listOrgNameByPid(String pid);

	/**
	 * 根据pid取到改用户所有的身份，且enable=true
	 * 
	 * @param pid
	 * @return
	 */
	public List<Identity> getByPidAndEnable(String pid);

	/**
	 * 对查询结果进行整理
	 * 
	 * @param identitys
	 * @return
	 */
	public List<IdentityDto> listByIdents(List<Identity> identitys);

	/**
	 * dto转为identitys
	 * 
	 * @param identitys
	 * @return
	 */
	public List<Identity> listByIdentdtos(List<IdentityDto> dtos);

	/**
	 * 用户的所有组织
	 * 
	 * @param pid
	 * @return
	 */
	public List<String> listOidByPid(String pid);

	/**
	 * 人员所属的所有组织
	 * 
	 * @param pid
	 * @return
	 */
	public List<IdentOrg> listIdentOrgByPid(String pid);

	public List<Identity> listtest();

	public PersonandIdentityDto getidentitybylogin(String personId);

	public List<Map<String, String>> listPositionIdNameByPid(String pid);
}
