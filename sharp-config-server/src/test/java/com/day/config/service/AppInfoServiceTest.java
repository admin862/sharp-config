package com.day.config.service;

import com.dafy.config.domain.AppInfo;
import com.dafy.config.service.AppInfoService;
import com.day.config.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

/**
 * @author yanfeng
 * @create 2017-05-15 15:11
 **/
public class AppInfoServiceTest extends BaseTest {

    @Autowired
    private AppInfoService appInfoService;

    @Test
    public void testSave(){
        AppInfo appInfo = new AppInfo();
        assertEquals(true,appInfoService.save(appInfo));
    }

}
