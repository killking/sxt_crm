package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.CustomerQuery;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.CustomerService;
import com.shsxt.crm.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * Created by xlf on 2018/4/24.
 */

@Controller
@RequestMapping("customer")
public class CustmerController extends BaseController {

    @Resource
    private CustomerService customerService;
    @RequestMapping("index")
    public  String index(){
        return "customer";
    }
   @RequestMapping("queryCustomersByParams")
    @ResponseBody
    public Map<String,Object> queryCustomersByParams(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer rows,
            CustomerQuery customerQuery
    ){
        customerQuery.setPageNum(page);
        customerQuery.setPageSize(rows);
        return customerService.queryForPage(customerQuery);
    }
    @RequestMapping("addOrUpdateCustomer")
    @ResponseBody
    public ResultInfo addOrUpdateCustomer(Customer customer){
                customerService.addOrUpdateCustomer(customer);
                return success(CrmConstant.OPS_SUCCESS_MSG);
    }
    @RequestMapping("deleteCustomer")
    @ResponseBody
    public ResultInfo deleteCustomer(Integer[] ids){
        customerService.deleteBatch(ids);
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }

    @RequestMapping("addLossCustomer")
    @ResponseBody
    public ResultInfo addLossCustomer(){
        customerService.addLossCustomer();
        return success(CrmConstant.OPS_SUCCESS_MSG);
    }
}
