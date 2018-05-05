package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.PermissionDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.utils.AssertUtil;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class PermissionService extends BaseService<Permission>{
    @Resource
    private PermissionDao permissionDao;
    @Resource
    private ModuleDao moduleDao;

    public Integer delModulesByRoleId(Integer roleId){
        return  permissionDao.delModulesByRoleId(roleId);
    };
    public void savePermissionBatch(Integer roleId, Integer[] moduleIds) {

        AssertUtil.isTrue(null==roleId, CrmConstant.OPS_FAILED_MSG);
        //先删除
        Integer total = permissionDao.queryModulesByRoleId(roleId);
        if(total>0){
            AssertUtil.isTrue(permissionDao.delModulesByRoleId(roleId)<total, CrmConstant.OPS_FAILED_MSG);
        }

        List<Permission> permissions = new ArrayList<>();
        if(null!=moduleIds&&moduleIds.length>0){
            for (Integer moduleId:moduleIds){
                Permission permission = new Permission();
                permission.setRoleId(roleId);
                Module module= moduleDao.queryById(moduleId);
                AssertUtil.isTrue(module==null,CrmConstant.OPS_FAILED_MSG);
                permission.setAclValue(module.getOptValue());
                permission.setModuleId(moduleId);
                permission.setCreateDate(new Date());
                permission.setUpdateDate(new Date());
                permissions.add(permission);
            }
            //添加
            AssertUtil.isTrue(permissionDao.saveBatch(permissions)<permissions.size(),CrmConstant.OPS_FAILED_MSG);
        }

    }
}
