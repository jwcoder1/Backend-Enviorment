package org.esy.base.service;

import java.util.List;

import org.esy.base.entity.Sysvar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author <a href="mailto:ardui@163.com">ardui</a>
 * @date Thu Nov 30 22:38:54 CST 2017
 *
 * @version v2.0
 */
public interface ISysvarService {

	Sysvar save(Sysvar o);

	Sysvar getByUid(String uid);

	boolean delete(Sysvar o);

	boolean savelist(List<Sysvar> o);

	Page<Sysvar> query(Sysvar sysvar, Pageable pageable);

	void deletes(String uids);

	Object getgroptype();

	String getsysvar(String var_type, String var_name);

}
