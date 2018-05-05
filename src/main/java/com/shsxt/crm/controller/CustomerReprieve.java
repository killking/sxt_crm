package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.service.CustomerLossService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by kingkill on 2018/5/5.
 */
@RequestMapping("customerReprieve")
public class CustomerReprieve extends BaseController{

    @Resource
    private CustomerLossService customerLossService;

    @RequestMapping("index")
    public String index(Integer id,Model model){
    CustomerLoss customerLoss=customerLossService.queryById(id);
    model.addAttribute(customerLoss);
    return "customer.loss.reprieve";
}

}
