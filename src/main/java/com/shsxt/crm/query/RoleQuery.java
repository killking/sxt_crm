package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by kingkill on 2018/4/26.
 */
public class RoleQuery extends BaseQuery {
    private String RoleName;
    private String createDate;

    public String getRoleName() {
        return RoleName;
    }

    public void setRoleName(String roleName) {
        RoleName = roleName;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}