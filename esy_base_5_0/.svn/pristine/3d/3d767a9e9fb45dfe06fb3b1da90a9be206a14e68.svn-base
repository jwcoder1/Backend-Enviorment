package org.esy.base.dao;

import java.util.Date;
import java.util.List;

import org.esy.base.core.IBaseDao;
import org.esy.base.entity.MQueue;

public interface IMQueueDao extends IBaseDao<MQueue> {

	public List<MQueue> getMessages(String qid);

	public List<MQueue> getMessages(String qid, int count, int skip);

	public int remove(String qid, Date beginDate);

	public int remove(String qid, long seq);

}
