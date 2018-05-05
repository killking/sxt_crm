package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.dao.CusDevplanDao;
import com.shsxt.crm.dao.SaleChanceDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.po.CusDevplan;
import com.shsxt.crm.po.SaleChance;
import com.shsxt.crm.po.User;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class CusDevplanService extends BaseService<CusDevplan>{
    @Resource
    private CusDevplanDao cusDevplanDao;
    @Resource
    private SaleChanceDao saleChanceDao;
    //根据有无id 判断是添加还是修改

    public void addOrUpdateCusDevplan(CusDevplan cusDevplan){

        checkCusDevplanParams(cusDevplan.getSaleChanceId(),cusDevplan.getPlanDate(),
                cusDevplan.getPlanItem(),cusDevplan.getExeAffect());
            //检查营销机会是否为空
        SaleChance saleChance= saleChanceDao.queryById(cusDevplan.getSaleChanceId());
        AssertUtil.isTrue(saleChance==null,"失败!记录不存在或者已经删除");

        cusDevplan.setUpdateDate(new Date());
        if (null==cusDevplan.getId()){
            //id不存在 说明是添加
            cusDevplan.setCreateDate( new Date());
            cusDevplan.setIsValid(1);
            AssertUtil.isTrue(cusDevplanDao.save(cusDevplan)<1,"营销计划添加失败");
            if (saleChance.getDevResult()==0){
                saleChance.setDevResult(1);
                AssertUtil.isTrue(saleChanceDao.update(saleChance)<1,"添加营销机会失败!");
            }
        }else {//更新
            AssertUtil.isTrue(cusDevplanDao.update(cusDevplan)<1,"更新营销机会失败");
        }

    }


    private void checkCusDevplanParams(Integer id, Date planDate, String planItem, String exeAffect) {
        AssertUtil.isTrue(null==id,"记录不存在");
        AssertUtil.isTrue(null==planDate,"计划时间为空");
        AssertUtil.isTrue(StringUtils.isBlank(planItem),"计划内容为空");
        AssertUtil.isTrue(StringUtils.isBlank(exeAffect),"执行结果为空");
    }
}
