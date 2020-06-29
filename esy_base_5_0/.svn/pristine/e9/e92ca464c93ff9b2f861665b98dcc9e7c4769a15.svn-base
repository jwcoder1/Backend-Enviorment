package org.esy.base.service.impl;

import java.util.Date;
import java.util.Map;

import org.apache.shiro.SecurityUtils;
import org.esy.base.core.LogBody;
import org.esy.base.core.QueryResult;
import org.esy.base.core.Response;
import org.esy.base.dao.ILogDao;
import org.esy.base.dao.ILogEventDao;
import org.esy.base.dao.YSDao;
import org.esy.base.dao.core.PageResult;
import org.esy.base.entity.Log;
import org.esy.base.entity.LogEvent;
import org.esy.base.security.service.ShiroRealm.ShiroUser;
import org.esy.base.service.ILogService;
import org.esy.base.util.HttpUtil;
import org.esy.base.util.YESUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.sf.json.JSONObject;

@Service
public class LogServiceImpl implements ILogService {

	@Autowired
	private YSDao dao;

	@Autowired
	private ILogDao logDao;

	@Autowired
	private ILogEventDao logEventDao;

	@Value("${log.remoteUrl}")
	private String remoteUrl;

	@Override
	public void save(String moduleId, String eventId, String message, String localIp) {

		LogEvent logEvent = logEventDao.getById(moduleId, eventId);

		if (logEvent == null) {
			if ("".equals(message)) {
				message = "无";
			}
			if ("base".equals(moduleId) && "LogSaveFailed".equals(eventId)) {
				System.out.println("base$LogSaveFailed" + localIp + " 记录 [" + moduleId + ":" + eventId + "] 编号不存在，保存失败："
						+ message);
			} else {
				save("base", "LogSaveFailed", "记录 [" + moduleId + ":" + eventId + "] 编号不存在，保存失败：" + message, localIp);
			}
			return;
		} else {
			if (logEvent.isEnable()) {
				if ("".equals(message)) {
					message = logEvent.getDescription();
				}
				String userId = "";
				try {
					ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
					userId = user == null ? "" : user.getUserLoginID();
					userId = YESUtil.isEmpty(userId) ? "null" : userId;
				} catch (Exception e) {
					// TODO: handle exception
				}

				Log log = new Log(logEvent.getModuleId(), logEvent.getEventId(), userId, new Date(), localIp, message,
						false, ".");
				logDao.save(log);

				// if (!"".equals(remoteUrl) && !logEvent.isLocal()) {
				// LogBody logBody = new LogBody(moduleId, eventId, message,
				// userId,
				// YESUtil.getDatetimeString(new Date()), localIp);
				// try {
				// HttpUtil.postUsingJson(remoteUrl,
				// JSONObject.fromObject(logBody).toString());
				// save("base", "RemoteLogSaveSuccess",
				// "记录 [" + moduleId + ":" + eventId + "] 远程地址 [" + remoteUrl +
				// " 调用成功：" + message,
				// localIp);
				// } catch (Exception e) {
				// save("base", "RemoteLogSaveFailed",
				// "记录 [" + moduleId + ":" + eventId + "] 远程地址 [" + remoteUrl +
				// "] 调用失败：" + message,
				// localIp);
				// e.printStackTrace();
				// }
				// }
			}
		}
	}

	@Override
	public Response saveRemote(String moduleId, String eventId, String message, String localIp, String user,
			String date, String remoteIp) {

		LogEvent logEvent = logEventDao.getById(moduleId, eventId);

		if (logEvent == null) {
			if ("".equals(message)) {
				message = "无";
			}
			String errStr = remoteIp + "发送记录 [" + eventId + "] 编号不存在，保存失败：" + message;
			save("base", "LogSaveFailed", errStr, localIp);
			return new Response(404, errStr, null);
		} else {
			if (logEvent.isEnable()) {
				if ("".equals(message)) {
					message = logEvent.getDescription();
				}
				Log log = new Log(logEvent.getModuleId(), logEvent.getEventId(), user,
						YESUtil.getDateTimeByString(date), localIp, message, true, remoteIp);
				logDao.save(log);
				return new Response(0, "远程Log保存成功", log);
			} else {
				return new Response(0, "远程Log保存成功", null);
			}

		}
	}

	@Override
	public QueryResult query(Map<String, Object> parm) {
		return logDao.query(parm);
	}

	@Override
	public PageResult<Log> query(Log log, Pageable pageable) {
		PageResult<Log> ret = (PageResult<Log>) dao.query(Log.class, log, pageable);
		return ret;
	}

}
