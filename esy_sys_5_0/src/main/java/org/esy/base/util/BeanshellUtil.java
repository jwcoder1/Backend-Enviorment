/*
 * Copyright (c) , JianFa Ltd. and/or its affiliates, and/or Yes-soft Ltd. All rights reserved.
 * Use is subject to license terms.
 */
package org.esy.base.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

import bsh.EvalError;
import bsh.Interpreter;
import bsh.TargetError;

/**
 * <pre>
 * ClssName: BeanshellUtil 
 * Description:  Beanshell工具类
 * </pre>
 *
 * @author cx
 * @since 1.7
 * @version $ Id:BeanshellUtil.java 1.0 2015年12月3日上午9:59:17 $
 */
public abstract class BeanshellUtil {

	/* Interpreter pool 对象 */
	private static GenericObjectPool<Interpreter> pool = initPool();;

	/**
	 * Description:解析beanshell
	 * 
	 * @param statements
	 *            脚本
	 * @return
	 */
	public static Object eval(String statements) {
		Interpreter inter = null;
		try {
			inter = pool.borrowObject();
			return inter.eval(statements);
		} catch (Exception ep) {
			throw new RuntimeException(findRootCause(ep));
		} finally {
			pool.returnObject(inter);
		}
	}

	/**
	 * Description:beanshell设置参数
	 * 
	 * @param statements
	 *            脚本
	 * @param args
	 *            多个参数
	 * @return
	 */
	public static Object eval(String statements, Map<String, Object> param) {
		Interpreter inter = null;
		try {
			inter = pool.borrowObject();
			for (String key : param.keySet()) {
				inter.set(key, param.get(key));
			}
			return inter.eval(statements);
		} catch (Exception e) {
			throw new RuntimeException(findRootCause(e));
		} finally {
			unSet(inter, param);
			pool.returnObject(inter);
		}
	}

	/**
	 * Description:unset
	 * @param inter
	 * @param param
	 */
	public static void unSet(Interpreter inter, Map<String, Object> param) {
		try {
			for (String key : param.keySet()) {
				inter.unset(key);
			}
		} catch (EvalError e) {
			throw new RuntimeException(findRootCause(e));
		}
	}

	/**
	 * <pre>
	 * ClssName: InterpreterFactory 
	 * Description:  InterpreterFactory 工厂类
	 * </pre>
	 *
	 * @author cx
	 * @since 1.7
	 * @version $ Id:BeanshellUtil.java 1.0 2015年12月3日上午11:05:14 $
	 */
	private static class InterpreterFactory extends BasePooledObjectFactory<Interpreter> {
		@Override
		public Interpreter create() throws Exception {
			return new Interpreter();
		}

		@Override
		public PooledObject<Interpreter> wrap(Interpreter obj) {
			return new DefaultPooledObject<Interpreter>(obj);
		}

	}

	/**
	 * Description:初始化Interpreter池对象
	 * 
	 * @return Interpreter池对象
	 */
	private static GenericObjectPool<Interpreter> initPool() {
		GenericObjectPoolConfig conf = new GenericObjectPoolConfig();
		conf.setMaxTotal(20);
		conf.setMaxIdle(20);
		conf.setMaxWaitMillis(20);
		return new GenericObjectPool<Interpreter>(new InterpreterFactory(), conf);
	}

	/**
	 * Description:解析异常
	 * 
	 * @param be
	 * @return
	 */
	private static Throwable findRootCause(Exception be) {
		Throwable tCause = be;

		while (tCause.getCause() != null) {
			tCause = tCause.getCause();
			if (TargetError.class.isAssignableFrom(tCause.getClass())) {
				TargetError te = (TargetError) tCause;
				if (te.getTarget() != null) {
					tCause = te.getTarget();
				}
			}
		}

		return tCause;
	}

	/**
	 * 
	 * 根据脚本文件路径执行
	 * @author cx 2016年9月14日 下午5:25:51
	 * @param path 脚本路径
	 * @param params 参数
	 * @return
	 */
	public static Object source(String path, Map<String, Object> params) {
		Interpreter inter = null;
		try {
			inter = pool.borrowObject();
			Object key = null;
			Iterator<String> keys = params.keySet().iterator();
			while (keys.hasNext()) {
				key = keys.next();
				inter.set(key.toString(), params.get(key));
			}
			return inter.source(path);
		} catch (Exception ep) {
			throw new RuntimeException(findRootCause(ep));
		} finally {
			pool.returnObject(inter);
		}
	}

	public static void main(String[] args) {
		long t = System.currentTimeMillis();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("abc", "abcd");
		for (int i = 0; i < 1000; i++) {
			BeanshellUtil.eval("foo = abc;System.out.println(foo);", map);
		}
		System.out.println(System.currentTimeMillis() - t);
	}
}
