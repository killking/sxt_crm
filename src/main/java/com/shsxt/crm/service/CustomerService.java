package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.CusDevplanDao;
import com.shsxt.crm.dao.CustomerDao;
import com.shsxt.crm.dao.CustomerLossDao;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.po.Customer;
import com.shsxt.crm.po.CustomerLoss;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.MathUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class CustomerService extends BaseService<Customer>{
   @Resource
    private CustomerDao customerDao;
   @Resource
   private CustomerLossDao customerLossDao;

   public void addOrUpdateCustomer(Customer customer){
       AssertUtil.isTrue(StringUtils.isBlank(customer.getName()),"客户名为空");
       //补全前台 没有传来的但是后台有参数...
       customer.setUpdateDate(new Date());
        //判断是添加还是更新
       if(customer.getId()==null){//tianjia
           customer.setCreateDate(new Date());
           customer.setIsValid(1);
           customer.setState(0);
            customer.setKhno(MathUtil.genereateKhCode());
            customerDao.save(customer);
       }else {
           Customer customer1=customerDao.queryById(customer.getId());
           AssertUtil.isTrue(customer1==null, CrmConstant.OPS_FAILED_MSG);
           AssertUtil.isTrue(customerDao.update(customer)<1,CrmConstant.OPS_SUCCESS_MSG);
       }

   }
    public void addLossCustomer(){
      /*
      * 查询出流失客户添加到 流失客户表
      *
      * */
      List<Customer> customers=customerDao.queryLossCustomers();
       List<CustomerLoss> customerLosses=new ArrayList<>();
       List<Integer> cusIds= new ArrayList<>();
       if (!CollectionUtils.isEmpty(customers)){
           for (Customer customer:customers){
               cusIds.add(customer.getId());//储存客户id
               CustomerLoss customerLoss= new CustomerLoss();
               customerLoss.setCreateDate(new Date());
               customerLoss.setUpdateDate(new Date());
               customerLoss.setState(0);
               customerLoss.setIsValid(1);
               customerLoss.setCusNo(customer.getKhno());
               customerLoss.setCusManager(customer.getCusManager());
               customerLoss.setCusName(customer.getName());
               customerLoss.setLossReason("达到流失预警");
               customerLosses.add(customerLoss);

               }
                //批量添加客户流失表
           AssertUtil.isTrue(customerLossDao.saveBatch(customerLosses)<customerLosses.size(),CrmConstant.OPS_FAILED_MSG);
            //再批量把客户表里的State设置成1, 避免下次查询到
           AssertUtil.isTrue(customerDao.updateCustomerBatch(cusIds)<cusIds.size(),CrmConstant.OPS_FAILED_MSG);
       }
    };

}
