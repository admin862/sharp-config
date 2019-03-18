package com.dafy.config.client;

import java.io.IOException;

import com.dafy.config.client.exception.SharpConfigException;

/**
 * @author yanfeng
 * @create 2017-04-04 23:38
 **/
public class Bootstrap {

	public static void main(String[] args) throws InterruptedException {

		try {
			ConfigBuilder.build().bind("app.properties").start();
			ConfigBuilder.build().bind("app.properties").start();
		} catch (SharpConfigException e) {
			e.printStackTrace();
		}

		// Properties p = new Properties();
		// try {
		// p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("sharp.config.properties"));
		// StateNotice stateNotice = new StateNotice(p);
		//
		// } catch (IOException e) {
		// e.printStackTrace();
		// }

		while (true) {
			System.out.println("URL=" + ConfigBuilder.build().on("app.properties").getString("URL"));
			System.out.println("URL=" + ConfigBuilder.build().on("app.properties").getString("URLX", "xxx"));
			System.out.println("URL=" + ConfigBuilder.build().on("app.properties").getInt("MaxConnection"));
			System.out.println("URL=" + ConfigBuilder.build().on("app.properties").getInt("MaxConnectionx", 1001));
//			System.out.println("P=" + ConfigBuilder.build().on("config.json").getString("p"));
			// System.out.println("config.para1="+ConfigBuilder.build().on("config.properties").getString("para1"));
			// System.out.println("redis.para0="+ConfigBuilder.build().on("redis.properties").getString("para0"));
			// System.out.println("redis.para1="+ConfigBuilder.build().on("redis.properties").getString("para1"));
			Thread.sleep(5000);
		}
		// ConfigClientMgr configClientMgr = new ZooKeeperModelImpl();
		// if(!configClientMgr.start("/config/online/KaoLa/KaoLaGateway")){
		// LOGGER.error("start fail");
		// return;
		// }

		// TimeUnit.MINUTES.sleep(10);
		// configClientMgr.close();
	}

}
