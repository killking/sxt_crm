package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class SaleChanceService extends BaseService<SaleChance>{
    @Resource
    private SaleChanceDao saleChanceDao;
    @Resource
    private UserDao userDao;
    public void addOrUpdateSaleChance(SaleChance saleChance,Integer userId){
        saleChance.setCreateDate(new Date());
        if (StringUtils.isBlank(saleChance.getAssignMan())){
            //为空为未分配
            saleChance.setState(0);
        }else {
            saleChance.setState(1);
            saleChance.setAssignTime(new Date());
        }
        //判断是否有id传过来,有id说明是更新,没有说明是添加
        if(saleChance.getId()==null){
            User user = userDao.queryById(userId);
            saleChance.setCreateMan(user.getTrueName());// 真实创建人
            saleChance.setIsValid(1);//有效数据
            saleChance.setDevResult(0);//未开发
            AssertUtil.isTrue(saleChanceDao.save(saleChance)<1, "营销机会添加失败");
        }else{
               AssertUtil.isTrue(saleChanceDao.update(saleChance)<1,"营销机会更新失败");
        }
    }
    public List<Map> queryCustomerManagers(){
        return userDao.queryCustomerManagers();
    }

    public void deleteSaleChance(Integer[] ids){
        AssertUtil.isTrue(ids==null,"没有选择用户!");
        AssertUtil.isTrue(saleChanceDao.deleteBatch(ids)<ids.length,"批量删除失败");
    }
}
