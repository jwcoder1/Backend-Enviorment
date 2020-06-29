package org.esy.base.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.IEnterpriseDao;
import org.esy.base.dao.IIdentityDao;
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.dao.IOrganizationRelationDao;
import org.esy.base.dao.IPersonDao;
import org.esy.base.dao.IPositionDao;
import org.esy.base.dao.IPostDao;
import org.esy.base.entity.Enterprise;
import org.esy.base.entity.Identity;
import org.esy.base.entity.Organization;
import org.esy.base.entity.Position;
import org.esy.base.entity.Post;
import org.esy.base.entity.dto.IdentOrg;
import org.esy.base.entity.dto.IdentPost;
import org.esy.base.entity.dto.IdentityDto;
import org.esy.base.entity.dto.PersonandIdentityDto;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.service.IIdentityService;
import org.esy.base.service.IPersonService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * Service implement for 身份信息
 *
 */
@Repository
public class IdentityServiceImpl implements IIdentityService {

	@Autowired
	private IIdentityDao identityDao;

	@Autowired
	private IEnterpriseDao enterpriseDao;

	@Autowired
	IPersonDao personDao;

	@Autowired
	private IOrganizationDao organizationDao;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private IPostDao postDao;

	@Autowired
	private IOrganizationRelationDao organizationRelationDao;

	@Override
	public Identity save(Identity o) {
		return identityDao.save(o);
	}

	@Override
	public Identity getByUid(String uid) {
		return identityDao.getByUid(uid);
	}

	@Override
	public List<Identity> getByPid(String pid) {
		List<Identity> idents = identityDao.getByPid(pid);
		if (YESUtil.isNotEmpty(idents)) {
			for (Identity ident : idents) {
				// 企业
				if (YESUtil.isNotEmpty(ident.getEid())) {
					Enterprise en = enterpriseDao.getById(ident.getEid());
					if (YESUtil.isNotEmpty(en))
						ident.setEnterpriseName(en.getCname());
				}
				// 组织
				if (YESUtil.isNotEmpty(ident.getOid())) {
					Organization org = organizationDao.getByOid(ident.getOid());
					if (YESUtil.isNotEmpty(org))
						ident.setoName(org.getName());
				}
				// 职位 positionDao
				if (YESUtil.isNotEmpty(ident.getPositionId())) {
					Position po = positionDao.getByPid(ident.getPositionId(), ident.getEid());
					if (YESUtil.isNotEmpty(po))
						ident.setPositionName(po.getName());
				}
				// 岗位 postDao
				if (YESUtil.isNotEmpty(ident.getPostId())) {
					Post p = postDao.getByPid(ident.getPostId(), ident.getEid());
					if (YESUtil.isNotEmpty(p))
						ident.setPostName(p.getName());
				}
			}
		}
		return idents;
	}

	@Transactional
	@Override
	public boolean delete(Identity o) {
		return identityDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return identityDao.query(parm);
	}

	@Override
	public boolean hadInConditions(Object... obj) {
		return identityDao.hadInConditions(obj);
	}

	@Override
	public MsgResult checkForSave(List<IdentityDto> identitys) {
		MsgResult mr = new MsgResult(false, "");

		// 身份不能为空
		if (BaseUtil.isEmpty(identitys)) {
			mr.setMsg("身份不能为空");
			return mr;
		}

		// 没有主职身份
		int i = 0;
		for (IdentityDto identity : identitys) {
			if (BaseUtil.isNotEmpty(identity.getIsMain()) && identity.getIsMain())
				i++;
			if (YESUtil.isEmpty(identity)) {
				mr.setMsg("身份类型不能为空!");
				return mr;
			}
		}

		if (i == 0) {
			mr.setMsg("主职不能为空");
			return mr;
		}

		if (i > 1) {
			mr.setMsg("不能同时有2个以上主职");
			return mr;
		}

		mr.setSuccess(true);
		return mr;
	}

	@Override
	public boolean deleteByPid(String pid) {
		return identityDao.deleteByPid(pid);
	}

	@Override
	public List<String> listPostNameByPid(String pid) {
		return identityDao.listPostNameByPid(pid);
	}

	@Override
	public List<String> listPositionNameByPid(String pid) {
		return identityDao.listPositionNameByPid(pid);
	}

	@Override
	public List<Map<String, String>> listPositionIdNameByPid(String pid) {
		return identityDao.listPositionIdNameByPid(pid);
	}

	@Override
	public List<String> listOrgNameByPid(String pid) {
		return identityDao.listOrgNameByPid(pid);
	}

	@Override
	public List<Identity> getByPidAndEnable(String pid) {
		return identityDao.getByPidAndEnable(pid);
	}

	@Override
	public List<IdentityDto> listByIdents(List<Identity> identitys) {
		String eid = BaseUtil.getUser().getEid();
		List<IdentityDto> results = new ArrayList<IdentityDto>();
		int i = 0;
		for (Identity ident : identitys) {
			int index = this.existsIdentityDtos(results, ident);
			if (index == -1) {
				IdentityDto dto = new IdentityDto();
				dto.setEid(ident.getEid());
				dto.setEnterpriseName(ident.getEnterpriseName());
				dto.setPid(ident.getPid());
				dto.setOid(ident.getOid());
				dto.setoName(ident.getoName());
				dto.setEnable(ident.getEnable());
				dto.setPositionId(ident.getPositionId());
				dto.setPositionName(ident.getPositionName());
				dto.setEnable(ident.getEnable());
				dto.setType(ident.getType());
				dto.setSeq(++i);
				dto.setStartDate(ident.getStartDate()); // 开始时间
				dto.setToDate(ident.getToDate()); // 结束时间
				dto.setPath(ident.getPath());
				// 处理多岗位
				List<IdentPost> posts = new ArrayList<IdentPost>();
				if (YESUtil.isNotEmpty(ident.getPostId())) {
					IdentPost identPost = new IdentPost();
					identPost.setId(ident.getPostId());
					identPost.setName(ident.getPostName());
					posts.add(identPost);
				}
				dto.setPosts(posts);
				if (!dto.getIsMain())
					dto.setIsMain(ident.getIsMain());
				dto.setIsShow(ident.getEid().indexOf(eid) > -1);
				results.add(dto);
			} else {
				IdentityDto dto = results.get(index);
				if (!dto.getIsMain())
					dto.setIsMain(ident.getIsMain());
				// 处理多岗位
				List<IdentPost> posts = new ArrayList<IdentPost>();
				if ((YESUtil.isNotEmpty(dto.getPosts()) && (dto.getPosts().size() > 0)))
					posts = dto.getPosts();

				if (YESUtil.isNotEmpty(ident.getPostId())) {
					IdentPost identPost = new IdentPost();
					identPost.setId(ident.getPostId());
					identPost.setName(ident.getPostName());
					posts.add(identPost);
				}
				dto.setPosts(posts);
			}
		}

		for (IdentityDto dto : results) {
			if (YESUtil.isNotEmpty(dto.getPosts())) {
				String name = "", buff = "";
				for (IdentPost post : dto.getPosts()) {
					name += buff + post.getName();
					buff = "，";
				}
				dto.setPostName(name);
				dto.setPositionName(dto.getPositionName());
			}
		}
		return results;
	}

	private int existsIdentityDtos(List<IdentityDto> dtos, Identity ident) {
		int result = -1;
		// 企业，组织，开始日期，结束日期，enbale ,type, path
		if (!YESUtil.isEmpty(dtos)) {
			for (int i = 0; i < dtos.size(); i++) {
				IdentityDto dto = dtos.get(i);
				if (dto.getOid().equals(ident.getOid()) && dto.getPath().equals(ident.getPath())
						&& dto.getType().equals(ident.getType()) && dto.getEid().equals(ident.getEid())
						&& dto.getEnable().equals(ident.getEnable())) {
					// && dto.getStartDate().equals(ident.getStartDate()) &&
					// dto.getToDate().equals(ident.getToDate())
					// boolean flag = false;
					// if (dto.getStartDate() == null && ident.getStartDate() ==
					// null) {
					// flag = true;
					// } else if (dto.getStartDate() != null) {
					// flag = dto.getStartDate().equals(ident.getStartDate());
					// } else if (ident.getStartDate() != null) {
					// flag = ident.getStartDate().equals(dto.getStartDate());
					// }
					// boolean flag2 = false;
					// if (dto.getToDate() == null && ident.getToDate() == null)
					// {
					// flag = true;
					// } else if (dto.getToDate() != null) {
					// flag = dto.getToDate().equals(ident.getToDate());
					// } else if (ident.getToDate() != null) {
					// flag = ident.getToDate().equals(dto.getToDate());
					// }
					result = i;
					break;

				}
			}
		}
		return result;
	}

	@Override
	public List<Identity> listByIdentdtos(List<IdentityDto> dtos) {
		int seq = 1;
		List<Identity> results = new ArrayList<Identity>();
		for (IdentityDto dto : dtos) {
			// 1.组织1个+职位1个
			if (YESUtil.isNotEmpty(dto.getPositionId())) {
				Identity ident = new Identity();
				ident = this.loadData(ident, dto);
				ident.setPositionId(dto.getPositionId());
				if (dto.getIsMain()) {
					ident.setIsMain(true);
					ident.setSeq(1);
				} else {
					ident.setSeq(++seq);
				}
				results.add(ident);
			}

			// 2.岗位多个
			if (YESUtil.isNotEmpty(dto.getPosts()) && dto.getPosts().size() > 0) {
				for (IdentPost dpost : dto.getPosts()) {
					Identity ident = new Identity();
					ident = this.loadData(ident, dto);
					ident.setPostId(dpost.getId());
					ident.setPositionId(dto.getPositionId());
					ident.setSeq(++seq);
					results.add(ident);
				}
			}
		}
		return results;
	}

	public Identity loadData(Identity ident, IdentityDto dto) {
		Identity result = ident;
		ident.setEid(dto.getEid());
		ident.setPid(dto.getPid());
		ident.setOid(dto.getOid());
		ident.setEnable(dto.getEnable());
		ident.setType(dto.getType());
		ident.setStartDate(dto.getStartDate());
		if (YESUtil.isNotEmpty(dto.getToDate())) {
			ident.setToDate(dto.getToDate());
		}
		ident.setPath(dto.getPath());
		return result;
	}

	@Override
	public List<String> listOidByPid(String pid) {
		return identityDao.listOidByPid(pid);
	}

	@Override
	public List<IdentOrg> listIdentOrgByPid(String pid) {
		List<String> oids = this.listOidByPid(pid);
		if (YESUtil.isEmpty(oids))
			return null;

		List<IdentOrg> orgs = organizationRelationDao.listOrgsByOids(oids);
		// path 改为全路径显示
		for (IdentOrg org : orgs) {
			String[] ids = org.getPath().split("\\.");
			String name = "", buff = "";
			for (String id : ids) {
				Organization o = organizationDao.getByOid(id);
				if (YESUtil.isNotEmpty(o)) {
					name += buff + o.getName();
					buff = "/";
				}
			}
			org.setoName(name);
		}

		return orgs;
	}

	@Override
	public List<Identity> listtest() {
		return identityDao.listtest();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.esy.base.service.IIdentityService#getidentitybylogin()
	 */
	@Override
	public PersonandIdentityDto getidentitybylogin(String personId) {
		// TODO Auto-generated method stub
		List<Identity> os = null;
		List<IdentityDto> dtos = null;
		// if ("person".equals(YESUtil.getUser().getType())) {

		PersonandIdentityDto personIdentity = new PersonandIdentityDto();
		personIdentity.setPerson(personDao.getByPid(personId));
		os = this.getByPid(personId);
		dtos = this.listByIdents(os);
		personIdentity.setIdentitys(dtos);
		return personIdentity;
	}

}
