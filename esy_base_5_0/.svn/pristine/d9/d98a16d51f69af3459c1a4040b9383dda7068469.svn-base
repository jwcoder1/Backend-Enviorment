package org.esy.base.service;

import org.esy.base.core.MQueueBody;
import org.esy.base.core.Response;

public interface IMQueueService {

	public Response save(String qid, MQueueBody mqbody, String sourceIp);

	/**
	 * 返回指定  Message Queue 的数据
	 * @param qid
	 * 			队列编号
	 * @param count
	 * 			读取条数（负数为取最近读取过的数据）
	 * @param skip
	 * 			起始条数
	 * @return
	 */
	public Response get(String qid, int count, int skip);

	public long getNewSeq();

}
