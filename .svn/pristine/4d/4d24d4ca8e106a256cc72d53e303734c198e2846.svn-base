package com.dafy.config.controller.admin;

import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.CommonResult;
import com.dafy.config.service.EnvironmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 数据管理
 * @author yanfeng
 * @create 2017-04-27 10:34
 **/
@Controller
@RequestMapping("/admin/data")
public class DataManagerController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataManagerController.class);

    @Autowired
    private EnvironmentService environmentService;

    @RequestMapping("dataSynchronizeUI")
    public ModelAndView dataSynchronizeUI(){
        ModelAndView  modelAndView = this.getModelAndView();
        modelAndView.setViewName("data/synchronize");
        return modelAndView;
    }

    @RequestMapping("environmentSynchronize")
    @ResponseBody
    public String environmentSynchronize(Long envFromId,Long envToId){
        if(!environmentService.synchronizeEnvironment(envFromId,envToId)){
            return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("synchronizeSingleZooKeeperEnvironment")
    @ResponseBody
    public String synchronizeSingleZooKeeperEnvironment(long envId){
        if(!environmentService.synchronizeSingleZooKeeperEnvironment(envId)){
            return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("synchronizeAllZooKeeperEnvironment")
    @ResponseBody
    public String synchronizeAllZooKeeperEnvironment(){
        if(!environmentService.synchronizeAllZooKeeperEnvironment()){
            return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

}
