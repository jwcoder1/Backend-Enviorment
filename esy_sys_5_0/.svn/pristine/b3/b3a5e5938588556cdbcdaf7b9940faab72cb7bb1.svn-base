package org.esy.base.util;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;

import jdk.nashorn.internal.objects.NativeArray;
import jdk.nashorn.internal.objects.NativeObject;

/**
 * @author Victor Javascript 脚本工具
 */
@SuppressWarnings("restriction")
public class JSUtils {

	public static NativeArray getArrayResult(String script, String msgStr) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			String code = "function getArray(msg) {";
			code += "var msg = " + msgStr + ";";
			code += script;
			code += "}";

			engine.eval(code);
			Invocable inv = (Invocable) engine;

			return (NativeArray) inv.invokeFunction("getArray", msgStr);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	public static NativeObject getObjectResult(String script, String msgStr) {
		ScriptEngineManager factory = new ScriptEngineManager();
		ScriptEngine engine = factory.getEngineByName("JavaScript");
		try {
			String code = "function getObject(msg) {";
			code += "var msg = " + msgStr + ";";
			code += script;
			code += "}";

			engine.eval(code);
			Invocable inv = (Invocable) engine;

			return (NativeObject) inv.invokeFunction("getObject", msgStr);

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

}
