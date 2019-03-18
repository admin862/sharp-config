package com.dafy.config.client.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Properties;

import com.dafy.config.client.DataProcess;
import com.dafy.config.client.SCache;

public class DefaultDataProcessor<K,V> implements DataProcess<K,V, String>{

	@Override
	public void dataProcess(SCache<K, V> cache, String content) {
		Properties properties = new Properties();
      try(ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"))){
    	  properties.load(new InputStreamReader(is, "UTF-8"));
//          properties.load(is);
          for (Map.Entry<Object,Object> entry : properties.entrySet()){
              cache.put((K)entry.getKey(), (V)entry.getValue());
          }
      } catch (IOException e) {
          e.printStackTrace();
      }
	}

	public static <K,V> DefaultDataProcessor<K,V> getDefaultDataProcessor(){
		return new DefaultDataProcessor<>();
	}
}
