package com.dafy.config.utils;

import java.util.Properties;

public class PropertiesUtil {

	private static  Properties properties = new Properties();
	
	static {
		try {
			properties.load(PropertiesUtil.class.getClassLoader().getResourceAsStream("conf/config.properties") );
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	public static String getValue(String key){
		Object obj= properties.get(key);
		if(obj == null){
			return null;
		}else{
			return (String)(obj);
		}
	}

	public static String getValue(String key,String defaultValue){
		Object obj = properties.get(key);
		if(obj == null){
			return defaultValue;
		}else{
			return (String)(obj);
		}
	}

	public static void main(String[] args) {
		System.out.println(PropertiesUtil.getValue("MaxIdle"));
	}
}
