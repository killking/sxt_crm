package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.query.CustomerLossQuery;
import com.shsxt.crm.service.CustomerLossService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by kingkill on 2018/5/4.
 */
@Controller
@RequestMapping("customerLoss")
public class CustomerLossController extends BaseController {
    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("index")
    public String index(){
        return "customer_loss";
    }
    @RequestMapping("queryCustomerLossByParams")
    @ResponseBody
    public Map<String,Object> queryCustomerLossByParams(@RequestParam(defaultValue = "1")Integer page,
                                                        @RequestParam(defaultValue = "10")Integer rows,
                                                   CustomerLossQuery customerLossQuery){
        customerLossQuery.setPageNum(page);
        customerLossQuery.setPageSize(rows);
        return customerLossService.queryForPage(customerLossQuery);
}
}