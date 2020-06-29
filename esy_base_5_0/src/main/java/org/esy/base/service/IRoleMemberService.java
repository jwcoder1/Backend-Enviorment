package org.esy.base.service;

import org.esy.base.dao.core.PageResult;
import org.esy.base.entity.RoleMember;
import org.esy.base.entity.view.RoleMemberv;
import org.springframework.data.domain.Pageable;

public interface IRoleMemberService {

	boolean save(RoleMember rm);

	PageResult<RoleMemberv> query(RoleMemberv roleMemberv, Pageable pageable);

}
