package org.esy.base.service.impl;

import java.util.Map;

import org.springframework.stereotype.Repository;
import org.esy.base.core.QueryResult;
import org.esy.base.dao.ICodeGenerationSampleDao;
import org.esy.base.entity.CodeGenerationSample;
import org.esy.base.service.ICodeGenerationSampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;


/**
 * 
 * Service implement for 自动代码生成范例
 *
 */
@Repository
public class CodeGenerationSampleServiceImpl implements ICodeGenerationSampleService {

	@Autowired
	ICodeGenerationSampleDao codeGenerationSampleDao;

	@Transactional
	@Override
	public CodeGenerationSample getEntityById(String id) { 
		return null;
			}
	
	
	@Override
	public int getEntityByNumber(int parm1, int parm2) { 
		return 0;
			}
	
	
	@Transactional
	@Override
	public CodeGenerationSample save(CodeGenerationSample o) {
		return codeGenerationSampleDao.save(o);
	}

	@Override
	public CodeGenerationSample getByUid(String uid) {
		return codeGenerationSampleDao.getByUid(uid);
	}

	@Transactional
	@Override
	public boolean delete(CodeGenerationSample o) {
		return codeGenerationSampleDao.delete(o);
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return codeGenerationSampleDao.query(parm);
	}
}
