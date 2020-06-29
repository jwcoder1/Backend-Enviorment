package org.esy.base.dao.core;

import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

/**
 * 
 * 查询内容传递体，用于查询 Json 数据数据
 *
 * @author 冰刀
 * @date 2016年5月13日 上午9:54:37 
 * @version v1.0
 */
public class PageResult<T> extends PageImpl<T> {

	private static final long serialVersionUID = 867755909294344406L;

	public PageResult(List<T> content, Pageable pageable, long total) {
		super(content, pageable, total);
	}

	public PageResult(List<T> content) {
		this(content, null, null == content ? 0 : content.size());
	}

	private Object other;

	/**
	 * 
	 * 总页数
	 * @author cx 2016年5月13日 上午9:54:14
	 * @return
	 */
	public int getTotal() {
		return getTotalPages();
	}

	/**
	 * 
	 * 总条数
	 * @author cx 2016年5月13日 上午9:53:44
	 * @return
	 */
	public long getRecords() {
		return getTotalElements();
	}

	/**
	 * 
	 * 当前页码从0开始
	 * @author cx 2016年5月13日 上午9:53:15
	 * @return
	 */
	public long getPage() {
		return getNumber();
	}

	/**
	 * @return the other
	 */
	public Object getOther() {
		return other;
	}

	/**
	 * @param other the other to set
	 */
	public void setOther(Object other) {
		this.other = other;
	}

}
