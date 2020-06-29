package org.esy.base.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * @author 蔡琼伟
 * @version 创建时间：2015年12月23日 下午5:49:12
 * 类说明 实现jsonobjec的date类型转换格式与时间戳
 */
public class JsonDateValueProcessor implements JsonValueProcessor {
	
	private String format = null;  
    
    public JsonDateValueProcessor() {  
        super();  
    }  
    
    /**
     * 如果format为null 则为时间戳,否则转为字符串
     * @param format
     */
    public JsonDateValueProcessor(String format) {  
        super();  
        this.format = format;  
    }  
  
    @Override  
    public Object processArrayValue(Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
  
    @Override  
    public Object processObjectValue(String paramString, Object paramObject,  
            JsonConfig paramJsonConfig) {  
        return process(paramObject);  
    }  
      
      
    private Object process(Object value){
        if(value instanceof Date){
        	if(format==null){
        		return value == null ? null : ((Date)value).getTime();
        	}else{
        		SimpleDateFormat sdf = new SimpleDateFormat(format);    
                return sdf.format(value);        		
        	}
        }
        return value;    
    }  
}
