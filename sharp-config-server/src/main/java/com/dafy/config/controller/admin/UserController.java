package com.dafy.config.controller.admin;

import com.dafy.config.controller.BaseController;
import com.dafy.config.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/admin/user")
public class UserController extends BaseController {

    @Autowired
    private UserRoleService userRoleService;

    @RequestMapping("changePwdView")
    public ModelAndView changePwdView(){
        ModelAndView modelAndView = this.getModelAndView();
        modelAndView.setViewName("setting/user/changePwd");
        return modelAndView;
    }

    @RequestMapping("changePwd")
    @ResponseBody
    public String changePwd(String originPwd,String newPwd,Principal principal){
        if(userRoleService.changePwd(principal.getName(),originPwd,newPwd)){
            return "OK";
        } else {
            return "请确认旧密码输入正确!";
        }
    }

}
