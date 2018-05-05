package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.po.CustomerOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Map;
@Repository
public interface CustomerOrderDao extends BaseDao<CustomerOrder> {


}