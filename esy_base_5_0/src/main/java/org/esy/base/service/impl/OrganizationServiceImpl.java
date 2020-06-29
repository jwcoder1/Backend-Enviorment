package org.esy.base.service.impl;

import java.util.List;
import java.util.Map;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.IWebClientNotifier;
import org.esy.base.core.QueryResult;
import org.esy.base.core.WebClientXmlNotifier;
import org.esy.base.dao.IOrganizationDao;
import org.esy.base.dao.IOrganizationRelationDao;
import org.esy.base.dao.IPositionDao;
import org.esy.base.dao.IPostDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Organization;
import org.esy.base.entity.OrganizationRelation;
import org.esy.base.entity.Person;
import org.esy.base.entity.Position;
import org.esy.base.entity.Post;
import org.esy.base.entity.dto.NameValue;
import org.esy.base.entity.pojo.MsgResult;
import org.esy.base.entity.pojo.OrganizationXmlPojo;
import org.esy.base.service.IOrganizationService;
import org.esy.base.service.ISerialService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.BizUtils;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service implement for 组织信息
 */
@Repository
public class OrganizationServiceImpl implements IOrganizationService {

	@Autowired
	private IOrganizationDao organizationDao;

	@Autowired
	private IPositionDao positionDao;

	@Autowired
	private IPostDao postDao;

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IOrganizationRelationDao organizationRelationDao;

	@Transactional
	@Override
	public Organization save(Organization o) {
		o.setPid(YESUtil.toString(o.getPid()));
		if (o.checkNew()) {
			if (YESUtil.isEmpty(o.getOid())) {
				String no = serialService.getSerialString("base", "Organization", "", 8, 99999999l);
				o.setOid(no);
				o.setShowid(no);
			} else {
				o.setShowid(o.getOid());
			}
			o.setPy(YESUtil.ChineseToPinyinHeader(o.getName(), true)); // 拼音简码
		}

		// 保存父节点
		OrganizationRelation newor = null;
		if (YESUtil.isEmpty(o.getPid())) {
			o.setIsGroup(false); // 父组织不为分组组织
			int seq = organizationRelationDao.getMaxSeqFromPidAndType("", null); // 同一个父节点下的排序号
			OrganizationRelation or = new OrganizationRelation();
			or.setOid(o.getOid());
			or.setPid("");
			or.setPath(o.getOid());
			or.setType(o.getOid());
			or.setSeq(++seq);
			or.setEid(o.getEid());
			or.setLevel(1);
			newor = organizationRelationDao.save(or);
		} else { // 保存非父节点
			String path = o.getPpath(); // 父节点path
			int seq = organizationRelationDao.getMaxSeqFromPidAndType(o.getPid(), o.getType(), path + "."); // 同一个父节点下的排序号
			OrganizationRelation or = new OrganizationRelation();
			or.setOid(o.getOid());
			or.setPid(o.getPid());
			or.setPath(path + "." + o.getOid());
			or.setType(o.getType());
			or.setSeq(++seq);
			or.setEid(o.getEid());
			or.setLevel(this.getLevel(or.getPath()));
			newor = organizationRelationDao.save(or);
		}
		o = organizationDao.save(o);
		o.setPath(newor.getPath());
		o.setType(newor.getType());
		o.setSeq(newor.getSeq());
		o.setBuid(newor.getUid());
		o.setPid(newor.getPid());

		/**
		 * 通过webservice发送pojo
		 */
		OrganizationXmlPojo oxp = new OrganizationXmlPojo();
		oxp.setOid(o.getOid());
		oxp.setName(o.getName());
		oxp.setAbbreviated(o.getAbbreviated());
		oxp.setIsGroup(o.getIsGroup().toString());
		oxp.setEnable(o.getEnable().toString());
		oxp.setMemo(o.getMemo());
		oxp.setPid(o.getPid());
		oxp.setSeq(YESUtil.toString(o.getSeq()));
		oxp.setPath(o.getPath());
		oxp.setLevel(YESUtil.toString(newor.getLevel()));
		try {
			WebClientXmlNotifier wcxn = new WebClientXmlNotifier();
			IWebClientNotifier cn = (IWebClientNotifier) BizUtils.getBean("cxfWebClient");
			wcxn.addListener(cn, oxp, "add");
			wcxn.notifyX();
		} catch (Exception e) {

		}
		return o;
	}

	@Override
	public Organization getByUid(String uid) {
		return organizationDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(Organization o) {
		return organizationDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		if (YESUtil.isEmpty(parm.get("eid"))) {
			Account a = BaseUtil.getUser();
			parm.put("eid", YESUtil.toString(a.getEid()));
		}
		QueryResult qr = organizationDao.query(parm);
		List<Organization> os = (List<Organization>) qr.getItems();
		if (YESUtil.isNotEmpty(os)) {
			for (Organization o : os) {
				if (YESUtil.isEmpty(o.getPid()))
					o.setPid("");
			}
		}
		return qr;
	}

	@Override
	public List<Organization> listByEid(String eid) {
		if (BaseUtil.isEmpty(eid))
			return null;
		return organizationDao.listByEid(eid);
	}

	@Override
	@Transactional
	public boolean deleteTreeByUid(String uid) {
		Organization o = organizationDao.getByUid(uid);
		String path = o.getPath();
		if (BaseUtil.isEmpty(path))
			return false;
		List<String> oids = organizationDao.listOidsByPath(path);
		if (BaseUtil.isEmpty(oids))
			return false;
		// 删除职务
		positionDao.deleteByOids(oids);
		// 删除组织
		organizationDao.deleteByOids(oids);
		return true;
	}

	@Override
	public List<String> listTreeByUid(String uid) {
		return organizationDao.listTreeByUid(uid);
	}

	@Override
	public List<Organization> listByPid(String uid) {
		return organizationDao.listByPid(uid);
	}

	// TODO
	@Override
	public MsgResult checkDeleteByUid(Map<String, Object> parm) {
		MsgResult result = new MsgResult(false, "检查参数错误");

		String eid = YESUtil.toString(parm.get("eid"));
		String path = YESUtil.toString(parm.get("path"));
		String oid = YESUtil.toString(parm.get("oid"));

		// 1.本节点下有子节点
		if (organizationDao.hadChild(path + ".", eid, oid)) {
			result.setMsg("本组织下还有子组织，请先删除子组织!");
			return result;
		}

		List<OrganizationRelation> ors = organizationRelationDao.listByOid(oid, eid);
		if (YESUtil.isNotEmpty(ors)) {
			if (ors.size() > 1) { // 只删除关系
				result.setSuccess(true);
				return result;
			}
		}

		// 2.本节点有职务
		if (organizationDao.hadPosition(oid)) {
			result.setMsg("本组织下有职务，请先删除职务!");
			return result;
		}

		// 有岗位
		if (organizationDao.hadPost(oid)) {
			result.setMsg("本组织下有岗位，请先删除岗位!");
			return result;
		}

		// 有身份
		if (organizationDao.hadIdentity(oid)) {
			result.setMsg("本组织下有人员使用了身份，请先删除人员身份!");
			return result;
		}

		result.setSuccess(true);
		return result;
	}

	@Override
	public String getPathFromOid(String oid) {
		return organizationDao.getPathFromOid(oid);
	}

	@Override
	public List<Organization> getRootByEid(String eid) {
		return organizationDao.getRootByEid(eid);
	}

	public String checkType(Organization o) {
		// if (YESUtil.isEmpty(o.getPid())) {
		// return null;
		// }
		//
		// String type[] = YESUtil.toString(o.getType()).split(",");
		// List<String> types = java.util.Arrays.asList(type);
		// if (YESUtil.isEmpty(types)) {
		// return "请选择组织类型!";
		// }
		//
		// Organization po =
		// organizationDao.getByOid(YESUtil.toString(o.getPid()));
		// if (YESUtil.isEmpty(o.getPid())) {
		// return "上级组织丢失";
		// }
		//
		// if (YESUtil.isEmpty(po.getType())) {
		// return "上级组织丢失";
		// }
		//
		// String ptype[] = YESUtil.toString(po.getType()).split(",");
		// List<String> ptypes = java.util.Arrays.asList(ptype);
		// if (YESUtil.isEmpty(ptypes)) {
		// return "上级组织没有设置类型";
		// }
		//
		// // 当前的节点不在父节点
		// for (String item : types) {
		// if (YESUtil.isNotEmpty(item)) {
		// if (!ptypes.contains(item)) {
		// // 数据字典找出此值
		// String eid = YESUtil.getUser().getEid();
		// String value = dicDetailService.getValueByDid("ORGANIZATIONTYPE",
		// item, eid);
		// return "上级组织没有该类型:" + value;
		// }
		// }
		// }

		return null;
	}

	@Override
	public MsgResult checkSave(Organization o) {
		MsgResult mr = new MsgResult(false, "");
		if (BaseUtil.isEmpty(o)) {
			mr.setMsg("组织为空");
			return mr;
		}

		if (BaseUtil.isEmpty(o.getEid())) {
			mr.setMsg("eid不可为空");
			return mr;
		}

		// if (BaseUtil.isEmpty(o.getShowid())) {
		// mr.setMsg("组织编号不可为空");
		// return mr;
		// }

		if (BaseUtil.isEmpty(o.getName())) {
			mr.setMsg("组织名称不可为空");
			return mr;
		}

		// 同企业下组织编号重复
		// String uid = BaseUtil.toString(o.getUid());
		// if (organizationDao.checkOrgForSave(o.getEid(), o.getShowid(), null,
		// uid)) {
		// mr.setMsg("企业的组织编号重复");
		// return mr;
		// }

		// 根组织不可不选类型
		// if (YESUtil.isEmpty(o.getPid()) && YESUtil.isEmpty(o.getType())) {
		// mr.setMsg("根组织请选择组织类型");
		// return mr;
		// }

		mr.setSuccess(true);
		return mr;
	}

	@Override
	public String getEidFromOid(String oid) {
		return organizationDao.getEidFromOid(oid);
	}

	@Transactional
	@Override
	public MsgResult changeLocation(Map<String, Object> parm, String classname) {
		MsgResult mr = new MsgResult(false, "");
		if (!parm.containsKey("begin")) {
			mr.setMsg("没有起始位置");
			return mr;
		}
		if (!parm.containsKey("end")) {
			mr.setMsg("没有结束位置");
			return mr;
		}

		String bPath = YESUtil.toString(parm.get("begin"));
		String ePath = YESUtil.toString(parm.get("end"));
		String eid = YESUtil.toString(parm.get("eid"));
		String pid = YESUtil.toString(parm.get("pid"));
		String type = YESUtil.toString(parm.get("type"));

		int begin = organizationRelationDao.getSeq(eid, pid, bPath, type);
		int end = organizationRelationDao.getSeq(eid, pid, ePath, type);

		if (begin == end) {
			mr.setMsg("交换位置排序号一致");
			return mr;
		}
		parm.remove("begin");
		parm.remove("end");

		// 获得uid
		String uid = positionDao.getByEidAndSeq(parm, classname, begin);

		// 区间改变
		if (!positionDao.changeLocation(parm, classname, begin, end)) {
			mr.setMsg("交换数据错误！");
			return mr;
		}

		// 改变uid
		if (!positionDao.updateSeq(classname, uid, end)) {
			mr.setMsg("更新本节点数据错误！");
			return mr;
		}

		mr.setSuccess(true);
		return mr;
	}

	@Override
	public List<String> listOidsByPerson(Person p) {
		List<String> ls = organizationDao.listPathByPerson(p);
		if (YESUtil.isEmpty(ls))
			return null;
		ls = organizationDao.listOidsByPaths(ls, p.getEid());
		return ls;
	}

	@Override
	public List<String> listChildNodesOidByPath(String path, String eid) {
		return organizationDao.listChildNodesOidByPath(path, eid);
	}

	@Override
	public List<Position> listPositionsByOid(String oid, String path, Boolean enable, String rootpid, String eid) {
		List<String> oids = this.listChildNodesOidByPath(path, eid);
		return positionDao.listByOids(oids, rootpid, enable);
	}

	@Override
	public List<Post> listPostsByOid(String oid, String path, Boolean enable, String rootpid, String eid) {
		List<String> oids = this.listChildNodesOidByPath(path, eid);
		return postDao.listByOids(oids, rootpid, enable);
	}

	// TODO 此方法无效
	@Override
	public List<String> listOidsByParentOid(String oid) {
		return null;
		// Organization o = organizationDao.getByOid(oid);
		// if (YESUtil.isEmpty(o)) {
		// return null;
		// }
		// return organizationDao.listChildNodesOidByPath(o.getPath());
	}

	@Override
	public List<NameValue> listRootNames(String eid) {
		if (YESUtil.isEmpty(eid))
			eid = BaseUtil.getUser().getEid();
		return organizationDao.listRootNames(eid);
	}

	@Override
	public List<OrganizationRelation> listParentNodes(String eid, String oid) {
		List<OrganizationRelation> ls = organizationDao.listParentNodes(eid, oid);

		// 根节点还要处理
		OrganizationRelation root = organizationDao.getRootName(eid, oid);
		if (YESUtil.isNotEmpty(root)) {
			root.setPname("无");
			ls.add(root);
		}
		return ls;

	}

	@Override
	public MsgResult checkforsaverelation(Organization o) {
		MsgResult mr = new MsgResult();
		mr.setSuccess(false);
		String ppath = o.getPpath();
		String oid = o.getOid();
		// 1.同级组织已存在该组织
		if (organizationRelationDao.isExsitsOid(ppath, o.getPid(), oid)) {
			mr.setMsg("同级组织已存在该组织");
			return mr;
		}

		// 2.所有上级中有该组织
		String[] oids = ppath.split("\\.");
		for (String soid : oids) {
			if (oid.equals(soid)) {
				mr.setMsg("上级组织中已有该组织");
				return mr;
			}
		}

		mr.setSuccess(true);
		return mr;
	}

	@Override
	@Transactional
	public Organization saveForRelation(Organization o) {
		int seq = organizationRelationDao.getMaxSeqFromPidAndType(o.getPid(), o.getType(), o.getPpath() + "."); // 同一个父节点下的排序号
		OrganizationRelation or = new OrganizationRelation();
		String ppath = o.getPpath() + "." + o.getOid();
		or.setPath(ppath);
		or.setOid(o.getOid());
		or.setPid(o.getPid());
		or.setType(o.getType());
		or.setEid(o.getEid());
		or.setSeq(++seq);
		or = organizationRelationDao.save(or);

		o.setPath(or.getPath());
		o.setSeq(or.getSeq());
		return o;
	}

	@Override
	@Transactional
	public Organization update(Organization o) {
		o.setPy(YESUtil.ChineseToPinyinHeader(o.getName(), true)); // 拼音简码
		o = organizationDao.save(o);
		/**
		 * 通过webservice发送pojo
		 */
		OrganizationXmlPojo oxp = new OrganizationXmlPojo();
		oxp.setUuid(o.getUid());
		oxp.setOid(o.getOid());
		oxp.setName(o.getName());
		oxp.setAbbreviated(o.getAbbreviated());
		oxp.setIsGroup(o.getIsGroup().toString());
		oxp.setEnable(o.getEnable().toString());
		oxp.setMemo(o.getMemo());
		oxp.setPid(o.getPid());
		oxp.setSeq(YESUtil.toString(o.getSeq()));
		oxp.setPath(o.getPath());
		try {
			WebClientXmlNotifier wcxn = new WebClientXmlNotifier();
			IWebClientNotifier cn = (IWebClientNotifier) BizUtils.getBean("cxfWebClient");
			wcxn.addListener(cn, oxp, "update");
			wcxn.notifyX();
		} catch (Exception e) {

		}
		return o;
	}

	@Override
	@Transactional
	public MsgResult deletelByRelation(Map<String, Object> parm) {
		MsgResult mr = new MsgResult();
		mr.setMsg("组织已删除");
		mr.setSuccess(true);
		String eid = YESUtil.toString(parm.get("eid"));
		String path = YESUtil.toString(parm.get("path"));
		String oid = YESUtil.toString(parm.get("oid"));
		List<OrganizationRelation> ors = organizationRelationDao.listByOid(oid, eid);
		if (YESUtil.isEmpty(ors))
			return mr;

		if (ors.size() > 1) { // 只删除关系
			organizationRelationDao.deleteByOidPathEid(oid, path, eid);
			mr.setMsg("组织关系已删除，其他组织类型还存在本组织！");
		} else { // 删除关系和本组织
			organizationRelationDao.deleteByOidPathEid(oid, path, eid);
			Organization o = organizationDao.getByOid(oid);
			organizationDao.delete(o);
			mr.setMsg("组织已删除");
			/**
			 * 通过webservice发送pojo
			 */
			OrganizationXmlPojo oxp = new OrganizationXmlPojo();
			oxp.setUuid(o.getUid());
			oxp.setOid(o.getOid());
			oxp.setName(o.getName());
			oxp.setAbbreviated(o.getAbbreviated());
			oxp.setIsGroup(o.getIsGroup().toString());
			oxp.setEnable(o.getEnable().toString());
			oxp.setMemo(o.getMemo());
			oxp.setPid(o.getPid());
			oxp.setSeq(YESUtil.toString(o.getSeq()));
			oxp.setPath(o.getPath());
			oxp.setLevel(YESUtil.toString(ors.get(0).getLevel()));
			try {
				WebClientXmlNotifier wcxn = new WebClientXmlNotifier();
				IWebClientNotifier cn = (IWebClientNotifier) BizUtils.getBean("cxfWebClient");
				wcxn.addListener(cn, oxp, "delete");
				wcxn.notifyX();
			} catch (Exception e) {

			}
		}
		return mr;
	}

	@Override
	public Organization getByOid(String oid) {
		return organizationDao.getByOid(oid);
	}

	public Integer getLevel(String path) {
		String[] arr = path.split("\\.");
		return arr.length;
	}

	@Override
	public String listChildrenOidsByRelationUid(String uid) {
		OrganizationRelation or = organizationRelationDao.getByUid(uid);
		if (YESUtil.isEmpty(or))
			return null;
		String path = or.getPath();
		String eid = or.getEid();
		List<String> oids = this.listChildNodesOidByPath(path, eid);
		if (YESUtil.isEmpty(oids))
			return null;
		String result = "", buff = "";
		for (String oid : oids) {
			result += buff + "'" + oid + "'";
			buff = ",";
		}
		return result;
	}

}
