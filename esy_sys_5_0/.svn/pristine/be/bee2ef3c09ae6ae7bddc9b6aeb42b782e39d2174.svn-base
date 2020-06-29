package org.esy.base.core;

import java.util.List;
import java.util.Map;

/**
 * 
 * 修改List<Object> to List<?>
 *
 * @author cx
 * @date 2015年9月25日 下午2:38:54
 * @version v1.0
 */
public class QueryResult {

	private Object headitem;

	private List<?> items;

	private Map<String, String> headers;

	private long count;

	private Object other;

	/**
	 * @param 查询结果
	 * @param 标题信息
	 * @param 总记录数
	 */
	public QueryResult(List<?> items, Map<String, String> headers, long count) {
		super();
		this.items = items;
		this.headers = headers;
		this.count = count;
	}

	/**
	 * 
	 */
	public QueryResult() {
		super();
	}

	/**
	 * @return the other
	 */
	public Object getOther() {
		return other;
	}

	/**
	 * @param other
	 *            the other to set
	 */
	public void setOther(Object other) {
		this.other = other;
	}

	/**
	 * @return 查询结果
	 */
	public List<?> getItems() {
		return items;
	}

	/**
	 * @param 查询结果
	 */
	public void setItems(List<?> items) {
		this.items = items;
	}

	/**
	 * @return 标题信息
	 */
	public Map<String, String> getHeaders() {
		return headers;
	}

	/**
	 * @param 标题信息
	 */
	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	/**
	 * @return 总记录数
	 */
	public long getCount() {
		return count;
	}

	/**
	 * @param 总记录数
	 */
	public void setCount(long count) {
		this.count = count;
	}

	public Object getHeaditem() {
		return headitem;
	}

	public void setHeaditem(Object headitem) {
		this.headitem = headitem;
	}

}
