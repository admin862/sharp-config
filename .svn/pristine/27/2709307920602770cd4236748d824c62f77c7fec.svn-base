package com.day.config.service;

import com.dafy.config.domain.ConfigVersion;
import com.dafy.config.service.ConfigService;
import com.dafy.config.service.ConfigVersionService;
import com.day.config.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class ConfigServiceTest extends BaseTest {

    @Autowired
    private ConfigService configService;

    @Autowired
    private ConfigVersionService versionService;

    @Test
    public void testPrepare(){
        ConfigVersion configVersion = versionService.getConfigVersionById(1);
        assertEquals(true,configService.prepare(configVersion));
    }

    @Test
    public void testConfirm(){
        assertEquals(0,configService.confirm(1,2).getCode());
    }

}
