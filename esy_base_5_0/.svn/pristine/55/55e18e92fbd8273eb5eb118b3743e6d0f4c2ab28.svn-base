package org.esy.base.service.impl;

import java.util.Date;
import java.util.List;

import org.esy.base.core.MQueueBody;
import org.esy.base.core.Response;
import org.esy.base.dao.IMQueueCfgDao;
import org.esy.base.dao.IMQueueDao;
import org.esy.base.entity.MQueue;
import org.esy.base.entity.MQueueCfg;
import org.esy.base.service.IMQueueService;
import org.esy.base.service.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Service
public class MQueueServiceImpl implements IMQueueService {

	@Autowired
	private IMQueueDao mQueueDao;

	@Autowired
	private IMQueueCfgDao mQueueCfgDao;

	@Autowired
	private ISerialService serialSerivce;

	@Override
	public Response save(String qid, MQueueBody mqbody, String sourceIp) {

		MQueueCfg mqcfg = mQueueCfgDao.getById(qid);
		if (mqcfg == null) {
			return new Response(404, "Queue Configuration not found.", null);
		}

		long seq = getNewSeq();
		String data = mqbody.getJsonData().toString();
		String state = "10";
		String attachments = mqbody.getAttachmentId();
		String ipAddress = sourceIp;
		Date lastScheduler = null;
		String lastMessage = "";
		int errorCount = 0;

		MQueue mQueue = new MQueue(qid, seq, data, state, attachments, ipAddress, lastScheduler, lastMessage,
				errorCount);
		mQueueDao.save(mQueue);

		JSONObject json = new JSONObject();
		json.put("seq", seq);

		return new Response(0, "Save success.", json);
	}

	@Override
	public long getNewSeq() {
		return serialSerivce.getSerialNo("base", "mqueue", "mqueue", Long.MAX_VALUE);
	}

	@Override
	public Response get(String qid, int count, int skip) {
		if (count == 0) {
			return new Response(500, "Count 必须是不为零的正负整数。", null);
		}
		JSONArray array = new JSONArray();
		List<MQueue> msgs = mQueueDao.getMessages(qid, count, skip);
		for (MQueue msg : msgs) {
			array.add(JSONObject.fromObject(msg.getData()));
		}

		return new Response(0, "success", array);
	}

}
