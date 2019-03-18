package com.dafy.config.client.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.NoHttpResponseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpRequestRetryHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import javax.net.ssl.SSLException;
import javax.net.ssl.SSLHandshakeException;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.UnknownHostException;

/**
 *
 */
public class HttpClientFactory {

    private static final Logger LOGGER = Logger.getLogger(HttpClientFactory.class);

    private HttpClientFactory(){createHttpClientConnectionManager();}

    private static class HttpClientFactoryInner{
        public static final HttpClientFactory httpClientFactory = new HttpClientFactory();
    }

    public static HttpClientFactory getInstance(){
        return HttpClientFactoryInner.httpClientFactory;
    }

    /**
     * 连接超时时间 可以配到配置文件  （单位毫秒）
     */
    private static final int MAX_TIME_OUT = 30000;

    //设置整个连接池最大连接数
    private static final int MAX_CONN = 200;
    //设置单个路由默认连接数
    private static final int SINGLE_ROUTE_MAX_CONN = 100;

    //连接丢失后,重试次数
    private static final int MAX_EXECUT_COUNT = 0;

    // 创建连接管理器
    private PoolingHttpClientConnectionManager connManager =null;

    private HttpClient httpClient = null;

    /**
     * 设置HttpClient连接池
     */
    private void createHttpClientConnectionManager() {
        try {
            if(httpClient != null){
                return ;
            }

            // 创建SSLSocketFactory
            // 定义socket工厂类    指定协议（Http、Https）
            Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.getSocketFactory())
                    //.register("https", createSSLConnSocketFactory())//SSLConnectionSocketFactory.getSocketFactory()
                    .build();

            // 创建连接管理器
            connManager = new PoolingHttpClientConnectionManager(registry);
            connManager.setMaxTotal(MAX_CONN);//设置最大连接数
            connManager.setDefaultMaxPerRoute(SINGLE_ROUTE_MAX_CONN);//设置每个路由默认连接数

            //设置目标主机的连接数
//		    HttpHost host = new HttpHost("account.dafy.service");//针对的主机
//		    connManager.setMaxPerRoute(new HttpRoute(host), 50);//每个路由器对每个服务器允许最大50个并发访问

            // 创建httpClient对象
            httpClient = HttpClients.custom()
                    .setConnectionManager(connManager)
                    .setRetryHandler(httpRequestRetry())
                    .setDefaultRequestConfig(config())
                    .build();

        } catch (Exception e) {
            LOGGER.error("获取httpClient(https)对象池异常:" + e.getMessage(), e);
        }
    }

    /**
     * 配置请求连接重试机制
     */
    private HttpRequestRetryHandler httpRequestRetry(){
        HttpRequestRetryHandler httpRequestRetryHandler = new HttpRequestRetryHandler() {
            public boolean retryRequest(IOException exception,int executionCount, HttpContext context) {
                if (executionCount >= MAX_EXECUT_COUNT) {// 如果已经重试MAX_EXECUT_COUNT次，就放弃
                    return false;
                }
                if (exception instanceof NoHttpResponseException) {// 如果服务器丢掉了连接，那么就重试
                    LOGGER.error("httpclient 服务器连接丢失");
                    return true;
                }
                if (exception instanceof SSLHandshakeException) {// 不要重试SSL握手异常
                    LOGGER.error("httpclient SSL握手异常");
                    return false;
                }
                if (exception instanceof InterruptedIOException) {// 超时
                    LOGGER.error("httpclient 连接超时");
                    return false;
                }
                if (exception instanceof UnknownHostException) {// 目标服务器不可达
                    LOGGER.error("httpclient 目标服务器不可达");
                    return false;
                }
                if (exception instanceof ConnectTimeoutException) {// 连接被拒绝
                    LOGGER.error("httpclient 连接被拒绝");
                    return false;
                }
                if (exception instanceof SSLException) {// ssl握手异常
                    LOGGER.error("httpclient SSLException");
                    return false;
                }

//                HttpClientContext clientContext = HttpClientContext.adapt(context);
//                HttpRequest request = clientContext.getRequest();
                // 如果请求是幂等的，就再次尝试   暂时没理解先注释
//				if (!(request instanceof HttpEntityEnclosingRequest)) {
//					return true;
//				}
                return false;
            }
        };
        return httpRequestRetryHandler;
    }


    /**
     * 配置默认请求参数
     */
    private static RequestConfig config() {
        // 配置请求的超时设置  其他参数可以追加
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectionRequestTimeout(MAX_TIME_OUT)// 设置从连接池获取连接实例的超时
                .setConnectTimeout(MAX_TIME_OUT)// 设置连接超时
                .setSocketTimeout(MAX_TIME_OUT)// 设置读取超时
                .build();
        return requestConfig;
    }

    public synchronized void close(){
        if(connManager == null){
            return ;
        }
        // 关闭连接池
        connManager.shutdown();
        // 设置httpClient失效
        httpClient = null;
        connManager = null;
    }

    public synchronized boolean isOpen(){
        if(connManager == null){
            return false;
        }
        return true;
    }

    public String getForText(String strUrl){
        return getForText(strUrl, null);
    }

    public String getForText(String strUrl, String strBody){
        if(!isOpen()){
            return "本地连接池关闭";
        }

        HttpResponse response = null;

        long start = System.currentTimeMillis();
        // 开始进行get请求
        String strReturn = "";
        try {
            if(LOGGER.isDebugEnabled()){
                LOGGER.info("HttpClient GET for:" + strUrl);
            }

            if(strBody != null && strBody.length() > 0){
                strUrl = strUrl + "?" + strBody;
            }

            HttpGet get = new HttpGet(strUrl);
            get.setHeader("Content-type", new StringBuilder().append("application/x-www-form-urlencoded;charset=").append("UTF-8").toString());
            response = httpClient.execute(get);
            int reqStatus=response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if(entity != null){
                strReturn = EntityUtils.toString(entity,"UTF-8");
            }
            if(reqStatus==200){
                LOGGER.info("\n\t正常响应数据:\n"+strReturn);
            }else{
                LOGGER.error("\n\t异常响应数据:\n请求数据="+strUrl+"\n"+strReturn);
                strReturn="";
            }
            long end = System.currentTimeMillis();
            LOGGER.info("HttpClient GET for:" + strUrl + ", 参数：【" + strBody + "】请求花费时间：" + (end - start) + "ms");
            return strReturn;
        } catch (ClientProtocolException e) {
            throw new RuntimeException("ClientProtocolException:" + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("IOException:" + e.getMessage(), e);
        }finally{
            //释放连接
            if(response != null) {
                try {
                    EntityUtils.consume(response.getEntity());//会自动释放连接
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public String postForText(String strUrl, String strBody){
        if(!isOpen()){
            return "本地连接池关闭";
        }
        long start = System.currentTimeMillis();

        HttpResponse response = null;

        // 开始进行post请求
        String strReturn = "";
        String strLog = "";
        try {
            if(LOGGER.isDebugEnabled()){
                LOGGER.info("HttpClient POST for:" + strUrl);
            }

            HttpPost post = new HttpPost(strUrl);
//            post.setHeader("Content-type", "application/json");
            post.setHeader("Content-type","application/x-www-form-urlencoded");
            if(strBody != null){
                LOGGER.info(strBody);
                post.setEntity(new StringEntity(strBody, "UTF-8"));
//				post.setEntity(new StringEntity(strBody));
                strLog = strBody;
            }
            response = httpClient.execute(post);
            int reqStatus = response.getStatusLine().getStatusCode();
            HttpEntity entity = response.getEntity();
            if(entity != null){
                strReturn = EntityUtils.toString(entity,"UTF-8");
            }
            if(reqStatus == 200){
                LOGGER.info("\n\t正常响应数据:\n"+strReturn);
            }else{
                LOGGER.error("\n\t异常响应数据:\n请求数据="+strUrl+"?"+strBody+"\n"+strReturn);
                strReturn = "";
            }
            long end = System.currentTimeMillis();
            LOGGER.info("HttpClient POST for:" + strUrl + ", 参数：【" + strLog + "】请求花费时间：" + (end - start) + "ms");
            return strReturn;
        } catch (ClientProtocolException e) {
            throw new RuntimeException("strUrl="+strUrl+" ClientProtocolException:" + e.getMessage(), e);
        } catch (IOException e) {
            throw new RuntimeException("strUrl="+strUrl+" \nstrBody="+strBody+" \nIOException:" + e.getMessage(), e);
        }finally{
            //释放连接
            if(response != null) {
                try {
                    EntityUtils.consume(response.getEntity());//会自动释放连接
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String hf=HttpClientFactory.getInstance().getForText("https://118.186.255.63:28888/huaxing.callback");
        System.out.println(hf);
    }

}


