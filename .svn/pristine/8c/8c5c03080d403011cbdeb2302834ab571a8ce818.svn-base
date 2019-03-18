package com.dafy.config.client;

/**
 *
 * 数据处理过程。获取数据T，解析并存放到cache中
 *
 * 由使用者实现具体的处理方式，如Properties/XML/JSON/DB等数据
 * 
 * K=缓存中Key的类型；V：缓存中元素的类型；T 获取数据的类型
 *
 * Created by de on 2017/4/8.
 */
public interface DataProcess<K,V,T> {

    void dataProcess(final SCache<K, V> cache, T content);

//    /**
//     * 默认properties处理方式
//     */
//    DataProcess defaultPropertiesDataProcessor<K,V> = new DataProcess<K,V,String>() {
//        @Override
//        public void dataProcess(Cache<K, V> cache, String content) {
//            Properties properties = new Properties();
//            try(ByteArrayInputStream is = new ByteArrayInputStream(content.getBytes("UTF-8"))){
//                properties.load(is);
//                for (Map.Entry<Object,Object> entry : properties.entrySet()){
//                    cache.put((K)entry.getKey(), (V)entry.getValue());
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//    };
}
