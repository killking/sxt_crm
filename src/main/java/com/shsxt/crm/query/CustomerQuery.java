package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by kingkill on 2018/4/26.
 */
public class CustomerQuery extends BaseQuery {
    private String name;
    private String khno;
    private String fr;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKhno() {
        return khno;
    }

    public void setKhno(String khno) {
        this.khno = khno;
    }

    public String getFr() {
        return fr;
    }

    public void setFr(String fr) {
        this.fr = fr;
    }
}