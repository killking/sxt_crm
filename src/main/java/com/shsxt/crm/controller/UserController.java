package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.query.UserQuery;
import com.shsxt.crm.service.UserService;
import com.shsxt.crm.utils.LoginUserUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by kingkill on 2018/4/24.
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {
    @Resource
    private UserService userService;

    @RequestMapping("index")
    public String index(){
        return "user";
    }
    @RequestMapping("login")
    @ResponseBody
    public ResultInfo checkUSerLogin(String userName, String userPwd , HttpSession session){
        ResultInfo resultInfo= new ResultInfo();
        UserModel userModel= userService.checkLogin(userName,userPwd);
        List<String> permission = userService.queryUserPermissions(userName);
        session.setAttribute(CrmConstant.USER_PERMISSIONS,permission);
        System.err.println(permission);
        resultInfo.setResult(userModel);
        return resultInfo;
    }
    @RequestMapping("updateUserPwd")
    @ResponseBody
    public ResultInfo updateUserPwd(String oldPassword, String newPassword,
                                    String confirmPassword, HttpServletRequest request){
        Integer userId = LoginUserUtil.releaseUserIdFromCookie(request);
        userService.updatePassword(userId,oldPassword,newPassword,confirmPassword);

        return success("更新密码成功!");
    }
    @RequestMapping("queryUsers")
    @ResponseBody
    public Map<String, Object> queryUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            UserQuery userQuery){
        userQuery.setPageNum(page);
        userQuery.setPageSize(rows);
        return userService.queryForPage(userQuery);
    }
    @RequestMapping("addOrUpdateUser")
    @ResponseBody
    public ResultInfo addOrUpdateUser(UserDto user){
        userService.addOrUpdateUser(user);
         return success(CrmConstant.OPS_SUCCESS_MSG);
}
    @RequestMapping("delUser")
    @ResponseBody
    public ResultInfo delUser(Integer[] ids){
        userService.deleteUserBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
    @RequestMapping("queryCustomerManagers")
    @ResponseBody
    public List<Map> queryCustomerManagers(){
        return userService.queryCustomerManagers();
    }
}
