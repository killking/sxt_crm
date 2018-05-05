package com.shsxt.crm.dao;

import com.shsxt.base.BaseDao;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.po.UserRole;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by kingkill on 2018/4/24.
 */
@Repository
public interface CustomerDao extends BaseDao<Customer> {

    public List<Customer> queryLossCustomers();

    public Integer updateCustomerBatch(List<Integer> ids);
}
