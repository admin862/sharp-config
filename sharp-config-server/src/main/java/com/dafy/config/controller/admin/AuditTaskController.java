package com.dafy.config.controller.admin;

import com.alibaba.fastjson.JSON;
import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.AuditTask;
import com.dafy.config.domain.ConfigVersion;
import com.dafy.config.domain.LayUIResult;
import com.dafy.config.domain.PageModel;
import com.dafy.config.service.AuditTaskService;
import com.dafy.config.service.ConfigVersionService;
import com.dafy.config.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

/**
 * 审批管理-控制器
 */
@Controller("auditTaskController")
@RequestMapping("/admin/audit")
public class AuditTaskController extends BaseController {

    @Autowired
    private AuditTaskService auditTaskService;

    @Autowired
    private ConfigVersionService versionService;

    @RequestMapping("list")
    public ModelAndView list(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("audit/list");
//        modelAndView.addObject("auditList",auditTaskService.getAuditTaskListBy(0,100));
        return modelAndView;
    }

    @RequestMapping("dataList")
    @ResponseBody
    public String dataList(int page,int limit){
        PageModel<AuditTask> pageModel = auditTaskService.getAuditTaskListBy(page,limit);
        LayUIResult result = new LayUIResult();
        result.setCode(0);
        result.setCount(pageModel.getCount());
        result.setData(pageModel.getDataList());
        return JSON.toJSONString(result);
    }

    @RequestMapping("operate")
    @ResponseBody
    public String operate(long id, int state, Principal principal){
        if(!(state == AuditTask.AuditState.AUDIT_SUCCESS.getValue() || state == AuditTask.AuditState.AUDIT_FAIL.getValue())){
            return "状态不正确";
        }
        auditTaskService.processState(id,AuditTask.AuditState.from(state),principal.getName());
        return "OK";
    }

    @RequestMapping("diff")
    public ModelAndView diff(long fromConfigId,long toConfigId){
        ModelAndView modelAndView = this.getModelAndView();
        ConfigVersion currentVersion = versionService.getConfigVersionById(fromConfigId);
        ConfigVersion nextVersion = versionService.getConfigVersionById(toConfigId);

        modelAndView.setViewName("audit/diff");
        modelAndView.addObject("currentVersion",currentVersion);
        modelAndView.addObject("nextVersion",nextVersion);
        modelAndView.addObject("diffData",CommonUtils.getDiffVersionHtmlData(currentVersion.getDataInfo(),nextVersion.getDataInfo()));
        return modelAndView;
    }

}
