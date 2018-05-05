package com.shsxt.crm.interceptors;

import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by kingkill on 2018/4/26.
 */

public class LoginInterceptor extends HandlerInterceptorAdapter {
    @Resource
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        Integer idr = LoginUserUtil.releaseUserIdFromCookie(request);
        AssertUtil.isNotLogin(null==idr||null==userService.queryById(idr),"用户未登录!!!");
        return true;
    }
}
