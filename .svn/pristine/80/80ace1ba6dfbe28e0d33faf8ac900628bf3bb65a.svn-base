package com.dafy.config.interceptor;

import com.dafy.config.domain.User;
import com.dafy.config.service.UserRoleService;
import com.dafy.config.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

/**
 * @author yanfeng
 * @create 2017-03-31 17:15
 **/
public class CommonInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserRoleService userRoleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getServletPath().contains("changePwd")){
            return true;
        }
        Principal principal = request.getUserPrincipal();
        if(principal != null && principal.getName().length()> 0 && !principal.getName().equals("admin") ){
            User user = userRoleService.getUserByUsername(principal.getName());
            if(user != null  && user.getPassword().equals(CommonUtils.md5("123456{" + user.getUsername() + "}"))){
                response.sendRedirect("/admin/user/changePwdView");
                return false;
            }
        }
        return super.preHandle(request, response, handler);
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        super.postHandle(request, response, handler, modelAndView);
    }
}
