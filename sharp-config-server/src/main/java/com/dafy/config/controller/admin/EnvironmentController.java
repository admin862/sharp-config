package com.dafy.config.controller.admin;

import com.dafy.config.controller.BaseController;
import com.dafy.config.domain.AppInfo;
import com.dafy.config.domain.CommonResult;
import com.dafy.config.domain.Environment;
import com.dafy.config.domain.GroupInfo;
import com.dafy.config.service.AppInfoService;
import com.dafy.config.service.EnvironmentService;
import com.dafy.config.service.GroupInfoService;
import com.google.common.base.Predicates;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanfeng
 * @create 2017-04-16 17:12
 **/
@Controller
@RequestMapping("/admin/environment")
public class EnvironmentController extends BaseController {

   @Autowired
   private EnvironmentService environmentService;

    @RequestMapping("addUI")
    public ModelAndView addUI(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/environment/add");
        return modelAndView;
    }

    @RequestMapping("add")
    @ResponseBody
    public String add(Environment environment){
        if(environment == null || Strings.isNullOrEmpty(environment.getName()) || Strings.isNullOrEmpty(environment.getPath())){
            return CommonResult.PARAMETER_ERROR;
        }
        if(!environmentService.save(environment)){
           return CommonResult.OPERATE_FAIL;
        }
        return CommonResult.OPERATE_SUCCESS;
    }

    @RequestMapping("list")
    public ModelAndView list(String name){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/environment/list");
        if(name == null || name.length() <= 0){
            modelAndView.addObject("environmentList",environmentService.getAllEnvironmentList());
            return modelAndView;
        }
        List<Environment> environmentList = new ArrayList<>();
        Environment environment = environmentService.getEnvironmentByName(name);
        if(environment != null){
            environmentList.add(environment);
        }
        modelAndView.addObject("environmentList",environmentList);
        return modelAndView;
    }

}
