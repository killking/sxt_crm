package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.po.User;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by xlf on 2018/4/24.
 */
@Controller
public class MainController extends BaseController {

    @Resource
    private UserService userService;

    @RequestMapping("main")
    public String index(HttpServletRequest request){
        Integer id = LoginUserUtil.releaseUserIdFromCookie(request);
        User user =userService.queryById(id);
        request.getSession().setAttribute("user",user);
        return "main";
    }
}
