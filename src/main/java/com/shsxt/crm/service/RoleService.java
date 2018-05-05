package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CusDevplanDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.CusDevplan;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class RoleService extends BaseService<Role>{
    @Resource
    private RoleDao roleDao;

    public void addOrUpdateRole(Role role){
        //根据是否有id 判断是添加还是修改
        AssertUtil.isTrue(role.getRoleName()==null,"角色名为空 ");
        role.setUpdateDate( new Date());
        if (role.getId()==null){
            role.setCreateDate(new Date());
            role.setIsValid(1);
            AssertUtil.isTrue(roleDao.save(role)<1, CrmConstant.OPS_FAILED_MSG);
        }else {
            AssertUtil.isTrue(roleDao.update(role)<1,CrmConstant.OPS_FAILED_MSG);
        }
    }

public List<Role> queryAllRoles(){
    return roleDao.queryAllRoles();
}

public List<ModuleDto> queryAllModuleByRoleId(Integer roleId){
    AssertUtil.isTrue(null==roleId,CrmConstant.OPS_FAILED_MSG);
    return roleDao.queryAllModuleByRoleId(roleId);
}

    public void deleteRoles(Integer []ids){
    AssertUtil.isTrue(ids==null||ids.length==0,CrmConstant.OPS_FAILED_MSG);
    //删除用户角色,和删除角色关联的信息
        for(Integer id:ids){
            Integer totle = roleDao.queryRolesByRoleId(id);
            if (totle>0){
                AssertUtil.isTrue(roleDao.delRolesByUserId(id)<totle,CrmConstant.OPS_FAILED_MSG);
            }
            //删除角色
            AssertUtil.isTrue(roleDao.deleteBatch(ids)<ids.length,CrmConstant.OPS_FAILED_MSG);
        }


    };


}
