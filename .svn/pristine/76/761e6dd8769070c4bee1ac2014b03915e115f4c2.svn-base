package com.dafy.config.demo;

import com.cdoframework.cdolib.base.Return;
import com.dafy.dflib.business.BusinessService;
import org.apache.log4j.Logger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;

public class ApplicationListener implements ServletContextListener, ServletRequestListener {

	private static final Logger LOGGER = Logger.getLogger(ApplicationListener.class);

	public void contextInitialized(ServletContextEvent arg0) {
		Return ret = BusinessService.getInstance().start();
		if (ret == null || ret.getCode() != 0) {
			throw new RuntimeException("ServiceBus init exception");
		}




//        Properties p = new Properties();
//        try {
//            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sharp.config.properties"));
//            StateNotice stateNotice = new StateNotice(p);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

//		new Thread(new Runnable() {
//			public void run() {
//				while (true) {
//					System.out.println("config.para0="+ConfigBuilder.build().on("config.properties").getString("para0"));
//					System.out.println("config.para1="+ConfigBuilder.build().on("config.properties").getString("para1"));
//					System.out.println("redis.para0="+ConfigBuilder.build().on("redis.properties").getString("para0"));
//					System.out.println("redis.para1="+ConfigBuilder.build().on("redis.properties").getString("para1"));
//					try {
//						TimeUnit.SECONDS.sleep(5000);
//					} catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}).start();


		LOGGER.info("BusinessService starts success!");
	}

	public void contextDestroyed(ServletContextEvent arg0) {
		BusinessService businessService = BusinessService.getInstance();
		if (businessService.isRunning() == false) {
			LOGGER.info("BusinessService is not running!");
			return;
		}
		businessService.stop();
		LOGGER.info("BusinessService stop!");
	}

	public void requestInitialized(ServletRequestEvent arg0) {
		HttpServletRequest request = (HttpServletRequest) arg0.getServletRequest();
		String strClientIP = request.getRemoteAddr();
		String strURL = request.getRequestURL().toString();
		if (strURL.endsWith(".htm") == false && strURL.endsWith(".cdo") == false && strURL.endsWith(".html") == false) {
			return;
		}
		if (request.getQueryString() != null) {
			strURL += "?" + request.getQueryString();
		}
		LOGGER.debug(strClientIP + ": " + strURL);
	}

	public void requestDestroyed(ServletRequestEvent arg0) {

	}

}
