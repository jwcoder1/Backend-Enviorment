package org.esy.base.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.esy.base.dao.ISerialDao;
import org.esy.base.entity.Serial;
import org.esy.base.service.ISerialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SerialServiceImpl implements ISerialService {
	
	@Autowired
	private ISerialDao serialDao;

	private static Map<String, Long> serialmap = new HashMap<String, Long>();

	@Override
	@Transactional
	public synchronized String getSerialString(String moduleName, String entityName, String serialKey, int strLength,
			long maxSize) {
		Long serialValue = getSerialNo(moduleName, entityName, serialKey, maxSize);
		String serialString = String.format("%0" + strLength + "d", serialValue);
		serialString = serialString.substring(serialString.length() - strLength, strLength);
		return serialKey + serialString;
	}

	@Override
	@Transactional
	public synchronized long getSerialNo(String moduleName, String entityName, String serialKey, long maxSize) {
		Serial serial = serialDao.getSerial(moduleName, entityName, serialKey);
		if (serial == null) {
			return -1l;
		}
		Long value;
		String key = moduleName + "$" + entityName + "$" + serialKey;
		if (serialmap.containsKey(key)) {
			serialmap.put(key, serialmap.get(key) + 1L);
			value = serialmap.get(key);
		} else {
			serialmap.put(key, serial.getSerialValue() + 1L);
			value = serialmap.get(key);
		}
		if (value > maxSize) {
			value = 1l;
		}
		serial.setSerialValue(value);
		serialDao.save(serial);
		return value;
	}

}
