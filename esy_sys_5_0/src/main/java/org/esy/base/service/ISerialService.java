package org.esy.base.service;

public interface ISerialService {

	String getSerialString(String moduleName, String entityName, String serialKey, int strLength, long maxSize);

	long getSerialNo(String moduleName, String entityName, String serialKey, long maxSize);

}
