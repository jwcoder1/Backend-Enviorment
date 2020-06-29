package org.esy.base.service.impl;

import java.util.List;

import org.esy.base.common.BaseUtil;
import org.esy.base.dao.ISysvarDao;
import org.esy.base.dao.YSDao;
import org.esy.base.entity.Sysvar;
import org.esy.base.service.ISysvarService;
import org.esy.base.util.YESUtil;
import org.esy.base.util.YesException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SysvarServiceImpl implements ISysvarService {

	@Autowired
	private YSDao dao;

	@Autowired
	private ISysvarDao sysvarDao;

	/**
	 * 保存实体
	 * @param Sysvar
	 * @return Sysvar o
	 * @version v2.0
	 */
	@Override
	@Transactional
	public Sysvar save(Sysvar o) throws YesException {
		if (BaseUtil.isNotEmpty(o.getUid())) {

			Sysvar old = dao.getByUid(Sysvar.class, o.getUid());
			if (BaseUtil.isEmpty(old)) {
				throw new YesException(HttpStatus.NOT_FOUND, "记录不存在，不可更新!!!");
			}
			if (!YESUtil.getDatetimeString(old.getUpdstamp()).equals(YESUtil.getDatetimeString(o.getUpdstamp()))) {
				throw new YesException(HttpStatus.INTERNAL_SERVER_ERROR, "该记录已被其他用户修改，不可更新!!！");
			}
		}
		return dao.save(o);
	}

	/* (non-Javadoc)
	 * @see org.esy.base.service.ISysvarService#savelist(java.util.List)
	 */
	@Override
	@Transactional
	public boolean savelist(List<Sysvar> o) {
		for (Sysvar sysvar : o) {
			dao.save(sysvar);
		}
		return false;
	}

	/**
	 * 通过uid查找实体
	 * @param uid
	 * @return Sysvar o
	 * @ version v2.0
	 * 
	 */
	@Override
	public Sysvar getByUid(String uid) {
		return dao.getByUid(Sysvar.class, uid);
	}

	/**
	 * 删除实体
	 * @param Sysvar o
	 * @return boolean 
	 * @ version v2.0 
	 */
	@Override
	@Transactional
	public boolean delete(Sysvar o) throws YesException {

		Sysvar old = dao.getByUid(Sysvar.class, o.getUid());
		if (BaseUtil.isNotEmpty(old)) {
			throw new YesException(HttpStatus.INTERNAL_SERVER_ERROR, "记录不存，不可删除!!!");
		}
		if (!YESUtil.getDatetimeString(old.getUpdstamp()).equals(YESUtil.getDatetimeString(o.getUpdstamp()))) {
			throw new YesException(HttpStatus.INTERNAL_SERVER_ERROR, "该记录已被其他用户修改，不可删除!!！");
		}
		return dao.delete(o);
	}

	/**
	* 查询实体
	* @param Map<String, Object> parm
	* @return QueryResult 
	* @ version v2.0 
	*/
	@Override
	public Page<Sysvar> query(Sysvar sysvar, Pageable pageable) {
		return dao.query(Sysvar.class, sysvar, pageable);
	}

	@Override
	@Transactional
	public void deletes(String uids) throws YesException {
		// TODO Auto-generated method stub

		String[] uidsArr = uids.split(",");
		for (String uid : uidsArr) {

			Sysvar old = dao.getByUid(Sysvar.class, uid);
			if (BaseUtil.isEmpty(old)) {
				throw new YesException(HttpStatus.INTERNAL_SERVER_ERROR, "记录不存，不可删除!!!");
			}

			dao.delete(old);
		}

	}

	/* (non-Javadoc)
	 * @see org.esy.base.service.ISysvarService#getgroptype()
	 */
	@Override
	public Object getgroptype() {
		// TODO Auto-generated method stub
		return sysvarDao.getgroptype();
	}

	@Override
	public String getsysvar(String var_type, String var_name) {
		Sysvar sysvar = dao.getEntity(Sysvar.class, new Sysvar(var_type, var_name));
		return BaseUtil.isNotEmpty(sysvar) ? sysvar.getVar_val() : "";//传主索引条件
	}

}
