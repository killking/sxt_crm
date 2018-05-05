package com.shsxt.crm.dto;

import com.shsxt.crm.po.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingkill on 2018/5/2.
 */
public class UserDto extends User{
    private String roleName;
    private List<Integer> roleIds = new ArrayList<>();

    public List<Integer> getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(List<Integer> roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
