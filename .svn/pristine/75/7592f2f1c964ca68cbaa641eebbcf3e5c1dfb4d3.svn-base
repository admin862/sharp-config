package com.day.config.service;

import com.dafy.config.service.GroupInfoService;
import com.day.config.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class GroupInfoServiceTest extends BaseTest {

    @Autowired
    private GroupInfoService groupInfoService;

    @Test
    public void testGetGroupWhiteList(){
        assert(groupInfoService.getGroupWhiteList().size() != 0);
    }

    @Test
    public void testIsInWhiteList(){
        assertEquals(true, groupInfoService.isInWhiteList(1));
    }

}
