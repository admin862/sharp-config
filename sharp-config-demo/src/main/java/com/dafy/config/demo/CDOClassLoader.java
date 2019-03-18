package com.dafy.config.demo;

import java.io.IOException;

import com.cdoframework.cdolib.base.Return;
import com.cdoframework.cdolib.data.cdo.CDO;
import com.dafy.config.client.ConfigBuilder;
import com.dafy.config.client.ConfigInfo;
import com.dafy.config.client.SCache;
import com.dafy.dflib.business.BusinessService;

/**
 * 
 * 
 * 
 * Created by de on 2017/4/19.
 */
public class CDOClassLoader extends ConfigBuilder.BaseReload<Integer, CDO, CDO[]> {

	/**
	 * 加载数据
	 */
	@Override
	public CDO[] load(ConfigInfo configInfo) throws IOException {
		System.out.println("加载数据！！");
		CDO cdoRequest = CDO.newRequest("FundProviderService", "getAllFundProviders");
		CDO cdoResponse = new CDO();
		Return retGetConfig = BusinessService.getInstance().handleTrans(cdoRequest, cdoResponse);
		if (retGetConfig == null || retGetConfig.getCode() != 0) {
			throw new RuntimeException("init data config exception");
		}
		CDO[] cdosFundProvider = cdoResponse.getCDOArrayValue("cdosFundProvider");
		for (CDO cdo : cdosFundProvider) {
			System.out.println(cdo.toXML());
		}
		return cdosFundProvider;
	}

	/**
	 * 数据处理
	 */
	@Override
	public void dataProcess(SCache<Integer, CDO> cache, CDO[] content) {
		for (CDO cdo : content) {
			cache.put(cdo.getIntegerValue("nCode"), cdo);
		}
	}

}
