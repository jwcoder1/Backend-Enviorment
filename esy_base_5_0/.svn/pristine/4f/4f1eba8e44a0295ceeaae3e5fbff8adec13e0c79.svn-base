package org.esy.base.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.esy.base.dao.IMQueueCfgDao;
import org.esy.base.dao.IMQueueDao;
import org.esy.base.entity.MQueue;
import org.esy.base.entity.MQueueCfg;
import org.esy.base.service.IMQueueService;
import org.esy.base.util.HttpUtil;
import org.esy.base.util.JSUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

//import jdk.nashorn.internal.objects.NativeArray;
//import jdk.nashorn.internal.objects.NativeObject;

@SuppressWarnings("restriction")
@Service
public class MQueueProcessServiceImpl {

	@Autowired
	private IMQueueDao mQueueDao;

	@Autowired
	private IMQueueCfgDao mQueueCfgDao;

	@Autowired
	private IMQueueService mQueueService;

	@Scheduled(fixedDelayString = "${messagequeue.queueprocess.scheduled}")
	public void MQueueProcess() {
		List<MQueueCfg> cfgs = mQueueCfgDao.getAtiveCfg();
		for (MQueueCfg cfg : cfgs) {
			if (cfg.getType() == 1) {
				forward(cfg);
			}
			if (cfg.getType() == 2) {
				send(cfg);
			}
		}

	}

	private void forward(MQueueCfg cfg) {

		List<MQueue> msgs = mQueueDao.getMessages(cfg.getQid());
		for (MQueue msg : msgs) {
			// 非排队状态就不处理
			if (!"10".equals(msg.getState())) {
				return;
			}

			// NativeArray array = JSUtils.getArrayResult(cfg.getConfig(),
			// msg.getData());
			// msg.setLastScheduler(new Date());
			// // 如果返回失败，设定错误标识
			// if (array == null) {
			// msg.setState("20");
			// msg.setLastMessage("目标队列返回值格式错误。");
			// mQueueDao.save(msg);
			// return;
			// }
			// for (int i = 0; i < array.size(); i++) {
			// String qid = array.get(i).toString();
			// MQueueCfg mQueueCfg = mQueueCfgDao.getById(qid);
			// if (mQueueCfg == null) {
			// msg.setState("20");
			// msg.setLastMessage("目标队列不存在：" + qid);
			// mQueueDao.save(msg);
			// return;
			// }
			// MQueue newMsg = new MQueue(qid, mQueueService.getNewSeq(),
			// msg.getData(), "10", msg.getAttachments(),
			// msg.getIpAddress(), null, "", 0);
			// mQueueDao.save(newMsg);
			//
			// }
			// mQueueDao.delete(msg);
		}

	}

	private void send(MQueueCfg cfg) {

		List<MQueue> msgs = mQueueDao.getMessages(cfg.getQid());
		for (MQueue msg : msgs) {
			// 非排队状态就不处理
			if (!"10".equals(msg.getState())) {
				return;
			}

			// NativeObject object;
			// String url;
			// // String method;
			// // String ContentType;
			// int maxTrying;
			// String intervalSec;
			// String[] interval;
			// String failedQueue;
			//
			// try {
			// object = JSUtils.getObjectResult(cfg.getConfig(), msg.getData());
			//
			// url = object.valueOf("url").toString();
			// // method = object.get("method").toString();
			// // ContentType = object.get("Content-Type").toString();
			// maxTrying = (int)
			// Double.parseDouble(object.valueOf("maxTrying").toString());
			// intervalSec = object.valueOf("intervalSec").toString();
			// interval = intervalSec.split(",");
			// failedQueue = object.valueOf("failedQueue").toString();
			// } catch (Exception e) {
			// msg.setState("20");
			// msg.setLastMessage("发送队列配置格式错误。");
			// mQueueDao.save(msg);
			// return;
			// }
			//
			// msg.setLastScheduler(new Date());
			//
			// try {
			// if (msg.getErrorCount() == 0) {
			// String result = HttpUtil.postUsingJson(url, msg.getData()); //
			// 首次直接提交
			// System.out.println(result);
			// mQueueDao.delete(msg);
			// } else {
			// int sec;
			// if (msg.getErrorCount() - 1 < interval.length) {
			// sec = Integer.parseInt(interval[msg.getErrorCount() - 1]); //
			// 累计提交间隔时间（秒）
			// } else {
			// sec = Integer.parseInt(interval[interval.length - 1]); //
			// 最大间隔提交时间（秒）
			// }
			// if (((new Date()).getTime() - msg.getLastScheduler().getTime()) /
			// 3600 > sec) {
			// String result = HttpUtil.postUsingJson(url, msg.getData()); //
			// 首次直接提交
			// System.out.println(result);
			// mQueueDao.delete(msg);
			// }
			// }
			//
			// } catch (Exception e) {
			//
			// msg.setErrorCount(msg.getErrorCount() + 1);
			// msg.setLastMessage("数据提交错误：" + url);
			//
			// if (msg.getErrorCount() > maxTrying) {
			// if (!"".equals(failedQueue)) {
			// msg.setQid(failedQueue);
			// msg.setSeq(mQueueService.getNewSeq());
			// mQueueDao.save(msg);
			// } else {
			// msg.setState("20");
			// mQueueDao.save(msg);
			// return;
			// }
			// } else {
			// mQueueDao.save(msg);
			// return;
			// }
			//
			// }

		}

	}

	@Scheduled(fixedDelayString = "${messagequeue.queuemessageremove.scheduled}")
	public void MQueueRemove() {
		List<MQueueCfg> cfgs = mQueueCfgDao.getAtiveCfg();
		for (MQueueCfg cfg : cfgs) {
			remove(cfg);
		}

	}

	private void remove(MQueueCfg cfg) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, cfg.getStorageDay() * -1);
		int removeCount = mQueueDao.remove(cfg.getQid(), cal.getTime());
		if (removeCount > 0) {
			System.out.println(cfg.getQid() + " -- Remove message queue count : " + removeCount);
		}
	}

}
