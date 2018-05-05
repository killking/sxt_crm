package com.shsxt.crm.query;

import com.shsxt.base.BaseQuery;

/**
 * Created by kingkill on 2018/4/26.
 */
public class SaleChanceQuery extends BaseQuery {
    private String customerName;
    private Integer state;
    private Integer devResult;
    private String createDate;
    public SaleChanceQuery() {
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Integer getDevResult() {
        return devResult;
    }

    public void setDevResult(Integer devResult) {
        this.devResult = devResult;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }


}
