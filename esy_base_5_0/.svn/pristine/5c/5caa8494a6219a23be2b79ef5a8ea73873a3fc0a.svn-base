package org.esy.base.service.impl;

import java.util.Date;
import java.util.Map;
import java.util.Random;

import org.esy.base.common.BaseUtil;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.IUidDao;
import org.esy.base.entity.Account;
import org.esy.base.entity.Uid;
import org.esy.base.entity.jsonObject.UidResult;
import org.esy.base.service.IDicDetailService;
import org.esy.base.service.IInterfaceAuthorizedService;
import org.esy.base.service.ISerialService;
import org.esy.base.service.IUidService;
import org.esy.base.util.BASEUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UidServiceImpl implements IUidService {

	private static final int SUCESS = 0;

	public static final String TEMP = "TEMP";

	public static final String EFFECTED = "EFFECTED";

	public static final String TEMPNOPERFIX = "TEMPNOPERFIX"; // 数据字典大类-临时职工号前缀

	@Autowired
	private ISerialService serialService;

	@Autowired
	private IDicDetailService dicDetailService;

	@Autowired
	private IInterfaceAuthorizedService interfaceAuthorizedService;

	@Autowired
	private IUidDao uidDao;

	@Override
	public String getNewUid(String type, String perfix) {
		String id = "";
		id = serialService.getSerialString("base", "EFFECTEDUID", "", 6, 999999l);
		Random random1 = new Random(99);
		String tid = String.format("%2d", Math.abs(random1.nextInt(89) + 10));
		id += tid;
		// if (EFFECTED.equals(type)) { // 正式编号规则
		// id = serialService.getSerialString("base", "EFFECTEDUID", "", 6,
		// 999999l);
		// Random random1 = new Random(99);
		// String tid = String.format("%2d", Math.abs(random1.nextInt(89) +
		// 10));
		// id += tid;
		// } else { // 临时编号规则
		// id = serialService.getSerialString("base", "TEMPUID", "", 4, 9999);
		// id = perfix + id;
		// }
		return id;
	}

	@Override
	public Uid getById(String uid) {
		return uidDao.getById(uid);
	}

	@Override
	@Transactional
	public Uid save(Uid uid) {
		// 新增uid
		if (YESUtil.isEmpty(uid.getUid())) {
			String suid = "";
			if (EFFECTED.equals(uid.getStatus())) {
				suid = this.getNewUid(EFFECTED, "");
			} else {
				String eid = BaseUtil.getUser().getEid();
				String perfix = dicDetailService.getValueByDdidandEid(TEMPNOPERFIX, eid);// 根据登录者的uid,找出公司的配置
				suid = this.getNewUid(TEMP, perfix);
			}
			uid.setUid(suid);
		}
		return uidDao.save(uid);
	}

	@Override
	public Uid getByUid(String uid, String staffNo, String tempStaffNo, String birthday, String name, String identifyNo,
			String status) {
		return uidDao.getByUid(uid, staffNo, tempStaffNo, birthday, name, identifyNo, status);
	}

	@Override
	@Transactional
	public Uid newUid(String tempStaffNo, String birthday, String name, String identifyNo) {
		Uid u = new Uid();
		String id = serialService.getSerialString("base", "Uid", "", 8, 99999999l); // 编号规则
		u.setUid(id);
		u.setBirthday(birthday);
		u.setStaffNo("");
		u.setName(name);
		u.setTempStaffNo(tempStaffNo);
		u.setIdentifyNo(BaseUtil.toString(identifyNo));
		String status = this.getStatus(u);
		u.setStatus(status);
		return uidDao.save(u);
	}

	@Override
	public Response checkForRegularuid(Uid o) {
		Response resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null);

		if (BaseUtil.isEmpty(o.getName())) {
			resp.setMessage("必要参数错误:name不可为空");
			return resp;
		}

		if (BaseUtil.isEmpty(o.getStaffNo())) {
			resp.setMessage("必要参数错误:staffNo不可为空");
			return resp;
		}

		if (BaseUtil.isEmpty(o.getBirthday())) {
			resp.setMessage("必要参数错误:birthday不可为空");
			return resp;
		}

		String birthday = o.getBirthday(); // 处理日期
		Date date = (Date) BaseUtil.strToDate(birthday);
		birthday = BaseUtil.dateToStr(date);
		if (BaseUtil.isEmpty(birthday)) {
			resp.setMessage("必要参数错误:birthday格式不符，注意格式为yyyy-MM-dd");
			return resp;
		}
		o.setBirthday(birthday); // 改成符合格式的日期

		// 正式职工号+姓名+生日+EFFECTED 查询
		Uid u = this.getByUid(null, o.getStaffNo(), null, birthday, o.getName(), "", EFFECTED);
		if (BaseUtil.isNotEmpty(u)) {
			resp.setError(SUCESS);
			resp.setMessage("获取uid成功:系统已存在职工号、姓名、生日相同的资料");
			UidResult ur = new UidResult(u.getUid());
			resp.setBody(ur);
			return resp;
		}

		// 创建uid失败:正式职工号已经存在，但姓名生日不匹配
		u = this.getByUid(null, o.getStaffNo(), null, null, null, "", EFFECTED);
		if (BaseUtil.isNotEmpty(u)) {
			resp.setMessage("创建uid失败:正式职工号已经存在，但姓名生日不匹配");
			return resp;
		}

		// 创建uid失败:职工号不存在，但姓名、生日存在，且是临时人员，请联系运维人员!
		u = this.getByUid(null, null, null, birthday, o.getName(), "", TEMP);
		if (BaseUtil.isNotEmpty(u)) {
			resp.setMessage("创建uid失败:正式职工号已经存在，但姓名生日不匹配");
			return resp;
		}
		resp.setError(SUCESS);
		return resp;
	}

	// 正式职工
	@Override
	@Transactional
	public Uid saveForRegularuid(Uid o) {
		Uid u = new Uid();
		String id = this.getNewUid(EFFECTED, null);
		u.setUid(id);
		u.setStatus(EFFECTED);
		u.setBirthday(o.getBirthday());
		u.setStaffNo(o.getStaffNo());
		u.setName(o.getName());
		u.setTempStaffNo("");
		u.setIdentifyNo("");
		return uidDao.save(u);
	}

	@Override
	public Response checkForTempuid(Uid o) {
		Response resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null);

		if (BaseUtil.isEmpty(o.getName())) {
			resp.setMessage("必要参数错误:name不可为空");
			return resp;
		}

		if (BaseUtil.isEmpty(o.getTempStaffNo())) {
			resp.setMessage("必要参数错误:staffNo不可为空");
			return resp;
		}

		if (BaseUtil.isEmpty(o.getBirthday())) {
			resp.setMessage("必要参数错误:birthday不可为空");
			return resp;
		}

		String birthday = o.getBirthday(); // 处理日期
		Date date = (Date) BaseUtil.strToDate(birthday);
		birthday = BaseUtil.dateToStr(date);
		if (BaseUtil.isEmpty(birthday)) {
			resp.setMessage("必要参数错误:birthday格式不符，注意格式为yyyy-MM-dd");
			return resp;
		}
		o.setBirthday(birthday); // 改成符合格式的日期

		// 临时职工号+姓名+生日+EFFECTED 查询
		Uid u = this.getByUid(null, null, o.getTempStaffNo(), birthday, o.getName(), "", TEMP);
		if (BaseUtil.isNotEmpty(u)) {
			resp.setError(SUCESS);
			resp.setMessage("获取uid成功:系统已存在职工号、姓名、生日相同的资料");
			UidResult ur = new UidResult(u.getUid());
			resp.setBody(ur);
			return resp;
		}

		// 创建uid失败:临时职工号已经存在，但姓名生日不匹配
		u = this.getByUid(null, null, o.getTempStaffNo(), null, null, "", TEMP);
		if (BaseUtil.isNotEmpty(u)) {
			resp.setMessage("创建uid失败:临时职工号已经存在，但姓名生日不匹配");
			return resp;
		}
		resp.setError(SUCESS);
		return resp;
	}

	@Override
	@Transactional
	public Uid saveForTempuid(Uid o, String aid) {
		Uid u = new Uid();
		// aid获得eid
		String eid = interfaceAuthorizedService.getEidFromAid(aid);
		// String perfix = dicDetailService.getValueByDdidandEid(TEMPNOPERFIX,
		// eid);
		// String id = this.getNewUid(TEMP, perfix);
		u.setUid(getNewUid(TEMP, eid));
		u.setBirthday(o.getBirthday());
		u.setStaffNo("");
		u.setName(o.getName());
		u.setTempStaffNo(o.getTempStaffNo());
		u.setIdentifyNo("");
		String status = this.getStatus(u);
		u.setStatus(status);
		return uidDao.save(u);
	}

	@Override
	public Response checkForAdd(Uid uid) {
		Response resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null);

		// 检查是否可以保存
		if (YESUtil.isEmpty(uid.getName())) {
			resp.setMessage("姓名不能为空");
			return resp;
		}
		// 生日不可为空
		if (YESUtil.isEmpty(uid.getBirthday())) {
			resp.setMessage("生日不能为空");
			return resp;
		}

		// ---effect情况下--正式职工
		if ("EFFECTED".equals(uid.getStatus())) {
			// 职工号不可为空
			if (YESUtil.isEmpty(uid.getStaffNo())) {
				resp.setMessage("职工号不能为空");
				return resp;
			}

			// 职工号不可重复
			if (uidDao.isByStaffNo(uid.getStaffNo())) {
				resp.setMessage("职工号不能重复");
				return resp;
			}
		}

		// --TEMP情况下 --临时职工
		if ("TEMP".equals(uid.getStatus())) {
			// 临时职工号不可为空
			if (YESUtil.isEmpty(uid.getTempStaffNo())) {
				resp.setMessage("临时职工号不能为空");
				return resp;
			}

			// 临时职工号不可重复
			Uid u2 = this.getById(uid.getTempStaffNo());
			if (!YESUtil.isEmpty(u2)) {
				resp.setMessage("临时职工号不能重复");
				return resp;
			}
		}
		resp.setError(SUCESS);
		return resp;
	}

	@Override
	public boolean isByStaffNo(String staffNo) {
		return uidDao.isByStaffNo(staffNo);
	}

	@Override
	public boolean isByUid(String uid) {
		return uidDao.isByUid(uid);
	}

	@Override
	public Response checkForPut(Uid uid) {
		Response resp = new Response(HttpStatus.INTERNAL_SERVER_ERROR.value(), "", null);

		// uid不可为空
		if (YESUtil.isEmpty(uid.getUid())) {
			resp.setMessage("uid不能为空");
			return resp;
		}

		Uid old = this.getById(uid.getUid());
		if (YESUtil.isEmpty(old)) {
			resp.setMessage("对象不能为空");
			return resp;
		}

		// 检查是否可以保存
		if (YESUtil.isEmpty(uid.getName())) {
			resp.setMessage("姓名不能为空");
			return resp;
		}

		// 生日不可为空
		if (YESUtil.isEmpty(uid.getBirthday())) {
			resp.setMessage("生日不能为空");
			return resp;
		}

		// --effect情况下--正式职工
		if (EFFECTED.equals(uid.getStatus())) {
			// 职工号不可为空
			if (YESUtil.isEmpty(uid.getStaffNo())) {
				resp.setMessage("职工号不能为空");
				return resp;
			}

			// 判断员工号是否等于原员工号// 检查正式职工号是否重复
			if (!old.getStaffNo().equals(uid.getStaffNo())) {
				if (!YESUtil.isEmpty(uidDao.getByStaffNoAndUidForEffected(uid.getStaffNo(), uid.getUid(), EFFECTED))) {
					resp.setMessage("职工号已被占用");
					return resp;
				}
			}
		}
		// -- EFFECTED

		// --TEMP情况下 --临时职工
		if (TEMP.equals(uid.getStatus())) {
			// 临时职工号不可为空
			if (YESUtil.isEmpty(uid.getTempStaffNo())) {
				resp.setMessage("临时职工号不能为空");
				return resp;
			}

			// 判断临时员工号是否等于临时原员工号
			if (!old.getTempStaffNo().equals(uid.getTempStaffNo())) {
				if (!YESUtil
						.isEmpty(uidDao.getBytempStaffNoAndUidForEffected(uid.getTempStaffNo(), uid.getUid(), TEMP))) {
					resp.setMessage("临时职工号已被占用");
					return resp;
				}
			}
		}
		// --TEMP
		resp.setError(SUCESS);
		return resp;
	}

	@Transactional
	@Override
	public boolean delete(Uid uid) {

		return uidDao.delete(uid);

	}

	public QueryResult query(Map<String, Object> parm) {
		return uidDao.query(parm);
	}

	@Override
	public String getTempNo(String eid) {
		if (YESUtil.isEmpty(eid))
			return null;
		String value = dicDetailService.getValueByDid("TEMPNOPERFIX", "TEMPNOPERFIX1", eid);
		if (YESUtil.isEmpty(value))
			return null;
		String id = serialService.getSerialString("base", "TEMPUID", "", 4, 9999);
		return "9" + value.trim() + id.trim();
	}

	@Override
	public String getStatus(Uid uid) {
		if (YESUtil.isNotEmpty(uid.getStaffNo())) {
			if (YESUtil.isEmpty(uid.getBirthday())) {
				return TEMP;
			}
		}

		if (YESUtil.isNotEmpty(uid.getTempStaffNo())) {
			if (YESUtil.isEmpty(uid.getBirthday())) {
				return TEMP;
			}
		}
		return EFFECTED;
	}

	@Override
	public Account getByUid(String uid) {
		return uidDao.getByUid(uid);
	}

}
