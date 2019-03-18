package com.day.config.service;

import com.dafy.config.domain.AuditTask;
import com.dafy.config.service.AuditTaskService;
import com.day.config.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;

public class AuditTaskServiceTest extends BaseTest {

    @Autowired
    private AuditTaskService auditTaskService;

    @Test
    public void testSave(){
        AuditTask auditTask = new AuditTask();
        auditTask.setConfigId(1);
        auditTask.setFromConfigId(1);
        auditTask.setFromVersion(1);
        auditTask.setToVersion(2);
        auditTask.setFromConfigId(1);
        auditTask.setToConfigId(2);
        auditTask.setSubmitUsername("yanfeng1612");
        auditTask.setEnvName("online");
        auditTask.setGroupName("example");
        auditTask.setAppName("ExampleAPP");
        auditTask.setConfigName("config.properties");
        auditTask.setRemark("commit some");
        assertEquals(1,auditTaskService.save(auditTask));
    }

    @Test
    public void testProcessState(){
        assertEquals(1,auditTaskService.processState(16,AuditTask.AuditState.AUDIT_FAIL,null));
    }

    @Test
    public void testGetAuditTaskListBy(){
        assertEquals(1,auditTaskService.getAuditTaskListBy(1));
    }

}
