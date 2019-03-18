package com.dafy.config.service;

import com.dafy.config.dao.DAO;
import com.dafy.config.domain.AuditTask;
import com.dafy.config.domain.AuditTask.AuditState;
import com.dafy.config.domain.PageModel;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AuditTaskService {

    @Autowired
    private DAO<AuditTask> daoAuditTask;

    public int save(AuditTask auditTask){
        return daoAuditTask.save("AuditTaskMapper.save",auditTask);
    }

    public int processState(long id,AuditState auditState,String auditUsername){
        Map<String,Object> map = Maps.newHashMap();
        map.put("id",id);
        map.put("state",auditState.getValue());
        map.put("auditUsername",auditUsername);
        return daoAuditTask.update("AuditTaskMapper.updateById",map);
    }

    public void processState(long configId,int fromVersion,int toVersion,AuditState auditState){
        Map<String,Object> map = Maps.newHashMap();
        map.put("configId",configId);
        map.put("fromVersion",fromVersion);
        map.put("toVersion",toVersion);
        map.put("state",auditState.getValue());
        daoAuditTask.update("AuditTaskMapper.updateBy",map);
    }

    public List<AuditTask> getAuditTaskListBy(long configId){
        return daoAuditTask.getListBy("AuditTaskMapper.getConfigAudit",configId);
    }

    public PageModel<AuditTask> getAuditTaskListBy(int page, int pageSize){
        int limit = (page - 1) * pageSize;
        Map<String,Object> parameterMap = Maps.newHashMap();
        parameterMap.put("offset",limit);
        parameterMap.put("pageSize",pageSize);

        long totalCount = daoAuditTask.getCountBy("AuditTaskMapper.countList",null);
        List<AuditTask> dataList = daoAuditTask.getListBy("AuditTaskMapper.getConfigAuditList",parameterMap);
        for (AuditTask task : dataList) {
            if(task.getState() == 0){
                task.setShowStateText("待审核");
            } else if (task.getState() == 1){
                task.setShowStateText("审核通过");
            } else if (task.getState() == 2){
                task.setShowStateText("审核失败");
            } else if (task.getState() == 3){
                task.setShowStateText("已完成");
            } else {
                task.setShowStateText("未知");
            }
        }

        PageModel<AuditTask> pageModel = new PageModel<>();
        pageModel.setCount(totalCount);
        pageModel.setDataList(dataList);
        return pageModel;
    }

    public AuditTask getAuditTaskBy(long configId,int fromVersion,int toVersion){
        Map<String,Object> map = Maps.newHashMap();
        map.put("configId",configId);
        map.put("fromVersion",fromVersion);
        map.put("toVersion",toVersion);
        return daoAuditTask.getModelBy("AuditTaskMapper.getConfigAuditBy",map);
    }

}
