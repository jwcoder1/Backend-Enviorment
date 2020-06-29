package org.esy.base.service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.entity.Dictionary;
import org.springframework.http.ResponseEntity;

public interface IDictionaryService {

	public Dictionary save(Dictionary o, HttpServletRequest request);

	public boolean delete(Dictionary o, HttpServletRequest request);

	Dictionary getByUid(String uid);

	public Dictionary getByID(String id);

	public QueryResult query(Map<String, Object> parm);

	/**
	 * 通过主表的name,查询子表list
	 * 
	 * @param name
	 * @return
	 */
	public List<Object> getDicDetailByName(String name);

	/**
	 * @Description 检查是否有查询权限
	 * @param
	 * @return
	 */
	public ResponseEntity<Response> checkSearch(Map<String, Object> parm);

}
