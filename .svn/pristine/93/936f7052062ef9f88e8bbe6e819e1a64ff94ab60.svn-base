package com.dafy.config.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author yanfeng
 * @create 2017-03-15 17:15
 **/
@Controller
public class MainController extends BaseController{

    /**访问系统首页
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/admin")
    public ModelAndView main(){
        ModelAndView mv = this.getModelAndView();
        mv.setViewName("index");
        return mv;
    }

}
