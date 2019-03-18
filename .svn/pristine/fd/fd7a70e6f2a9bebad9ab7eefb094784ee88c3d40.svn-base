package com.dafy.config.demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.dafy.config.client.ConfigBuilder;
import com.dafy.config.client.DataProcess;
import com.dafy.config.client.SCache;
import com.dafy.config.client.exception.SharpConfigException;

/**
 * Created by de on 2017/4/19.
 */
public class ConfigServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	ConfigBuilder configBuilder;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		StringBuilder sb = new StringBuilder();
		sb.append("<html><h3>Config Demo</h3>");
		sb.append("<h3>URL=").append(configBuilder.on("app.properties").get("URL").toString()).append("</h3>");
		sb.append("<h3>MaxConnection=").append(configBuilder.on("app.properties").getInt("MaxConnection"))
				.append("</h3>");
		sb.append("<p>p=").append(configBuilder.on("config.json").getString("p")).append("</p>");
		sb.append("<textarea>cdo=").append(configBuilder.on("db-tbFundProvider").get(25).toString())
				.append("</textarea>");
		// CDO[] cods = (CDO[]) configBuilder.on("app.properties").get("A");
		// text += Arrays.toString(cods);
		sb.append("</html>\n");
		resp.getOutputStream().write(sb.toString().getBytes("UTF-8"));
		resp.getOutputStream().flush();
	}

	@Override
	public void init() throws ServletException {
		try {
			ConfigBuilder.build()
					.bind("app.properties")
					.bind("config.json", new DataProcess<String, String, String>() {

						@Override
						public void dataProcess(SCache<String, String> cache, String content) {
									System.out.println(content);
									System.out.println(JSON.parse(content));
									cache.put("p", content);
								}

							})
					.bind("db-tbFundProvider", new CDOClassLoader()).start();
		} catch (SharpConfigException e) {
			e.printStackTrace();
		}
		this.configBuilder = ConfigBuilder.build();
	}
}
