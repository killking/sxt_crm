package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class UserService extends BaseService<User>{
    @Resource
    private UserDao userDao;
    @Resource
    private RoleDao roleDao;

    @Resource
    private PermissionDao permissionDao;

    public void deleteUserBatch(Integer[] ids){
        /*
        删除 用户 和 用户的角色
        * */
        AssertUtil.isTrue(null==ids||ids.length<1,"未选择要删除的记录");

        for(Integer id:ids){
            List<UserRole> userRoleList=roleDao.queryRolesByUserId(id);
            Integer totle=userRoleList.size();
            if (totle>0){
                AssertUtil.isTrue(roleDao.delRolesByUserId(id)<totle,CrmConstant.OPS_FAILED_MSG);
            }
            //删除用户

        }
        AssertUtil.isTrue(userDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
    }



    public void addOrUpdateUser(UserDto user){
        checkUserParams(user.getUserName(),user.getEmail(),user.getPhone());
        user.setUpdateDate(new Date());
        User oldUser=userDao.queryUserByName(user.getUserName());
        if(user.getId()==null){
            //说明是添加
            AssertUtil.isTrue(null!=oldUser,"用户名已被注册");
            user.setIsValid(1);
            user.setCreateDate(new Date());
            //原始密码为空
            user.setUserPwd(Md5Util.encode("123456"));
            AssertUtil.isTrue(userDao.save(user)<1,"用户添加失败");
        }else{//修改
        AssertUtil.isTrue(user.getId()!=oldUser.getId(),"修改用户不存在或已删除");
        AssertUtil.isTrue(userDao.update(user)<1,"用户更新失败");
        }
        //当添加或者更新之后需要重新查询这个用户
        User realUser = userDao.queryUserByName(user.getUserName());
        relationRoles(realUser.getId(), user.getRoleIds());


    }

    private void relationRoles(Integer id, List<Integer> roleIds) {

        /*
        * 分配方式
        * 没有 的话直接添加
        * 有的话删除在添加*/
        if (roleIds.size()>0){
            List<UserRole> userRoles = roleDao.queryRolesByUserId(id);
            Integer total=userRoles.size();
            //先删除 再添加());
            if (total>0){
                AssertUtil.isTrue(roleDao.delRolesByUserId(id)<total, CrmConstant.OPS_FAILED_MSG);
            }
            //批量添加
            List<UserRole> userRoleList= new ArrayList<>();

            for (Integer roleId: roleIds){
                userRoleList.add(new UserRole(id,roleId,new Date(),new Date()));
            }
            AssertUtil.isTrue(roleDao.saveBatchUserRoles(userRoleList)<roleIds.size(),CrmConstant.OPS_SUCCESS_MSG);
        }
    }

    private void checkUserParams(String userName, String email, String phone) {
        AssertUtil.isTrue(StringUtils.isBlank(userName),"用户名不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(email),"email不能为空");
        AssertUtil.isTrue(StringUtils.isBlank(phone),"手机号不能为空");
    }


    public void updatePassword(Integer userId,String oldPassword,
                                  String newPassword,String confirmPassword){
    checkUpdateParams(userId,oldPassword,newPassword, confirmPassword);//能通过这一步,说明验证通过!
        //设置更新参数
        User user= new User();
        user.setId(userId);
        user.setUserPwd(Md5Util.encode(newPassword));
        AssertUtil.isTrue(userDao.updatePasswordById(user)<1,"更新密码失败!!!");
    }

    private void checkUpdateParams(Integer userId, String oldPassword,
                                   String newPassword, String confirmPassword) {
        User user= userDao.queryById(userId);
        AssertUtil.isTrue(user==null||userId==null,"用户不存或已注销!");
        AssertUtil.isTrue(org.apache.commons.lang3.StringUtils.isBlank(oldPassword),"原始密码为空!");
        AssertUtil.isTrue(!newPassword.equals(confirmPassword),"两次密码不一致11");
        AssertUtil.isTrue(!user.getUserPwd().equals(Md5Util.encode(oldPassword)),"原始密码错误!");
        AssertUtil.isTrue(org.apache.commons.lang3.StringUtils.isBlank(newPassword),"新密码为空");


    }

    public UserModel   checkLogin(String userName,String userPwd){
        checkLoginParams(userName,userPwd);
        User user = userDao.queryUserByName(userName);
        AssertUtil.isTrue(user==null,"用户不存在,或已注销");
        userPwd= Md5Util.encode(userPwd);
        AssertUtil.isTrue(!userPwd.equals(user.getUserPwd()),"用户名或密码错误");
        UserModel userModel = buildUserModel(user);
        return userModel;
    }

    private UserModel buildUserModel(User user) {
        UserModel userModel= new UserModel();
        userModel.setUserName(user.getUserName());
        userModel.setTrueName(user.getTrueName());
        userModel.setUserIdStr(UserIDBase64.encoderUserID(user.getId()));

        return userModel;
    }

    private void checkLoginParams(String userName, String userPwd) {
        AssertUtil.isTrue(com.mysql.jdbc.StringUtils.isNullOrEmpty(userName),"用户名为空!");
        AssertUtil.isTrue(com.mysql.jdbc.StringUtils.isNullOrEmpty(userPwd),"密码为空!");

    }

    public List<String> queryUserPermissions(String userName){
        AssertUtil.isTrue(StringUtils.isBlank(userName),CrmConstant.OPS_FAILED_MSG);
        User user=userDao.queryUserByName(userName);
        AssertUtil.isTrue(null==user,CrmConstant.OPS_FAILED_MSG);
        return  permissionDao.queryPermissionsByUserId(user.getId());
    };
    public List<Map> queryCustomerManagers(){
        return userDao.queryCustomerManagers();
    }
}
