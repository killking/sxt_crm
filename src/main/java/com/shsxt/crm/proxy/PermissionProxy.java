package com.shsxt.crm.proxy;

import com.mchange.v1.db.sql.ConnectionUtils;
import com.shsxt.crm.annotations.Mypermission;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.po.Permission;
import com.shsxt.crm.utils.AssertUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.binding.MapperMethod;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Collection;
import java.util.List;

/**
 * Created by kingkill on 2018/5/3.
 */
@Component
@Aspect
public class PermissionProxy {
    @Resource
    private HttpSession session;


    /*定义切入点,切面
    *
    * */
    @Pointcut("@annotation(com.shsxt.crm.annotations.Mypermission)")
    public void  cut(){}

    //定义通知
    @Around("cut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        MethodSignature signature = (MethodSignature)pjp.getSignature();
        Method method= signature.getMethod();
        Mypermission annotation=method.getAnnotation(Mypermission.class);
        if (null!=annotation){//加注解
            List<String>  permissions= (List<String>) session.getAttribute(CrmConstant.USER_PERMISSIONS);
            AssertUtil.isTrue(CollectionUtils.isEmpty(permissions),"没有权限");
            String aclValue=annotation.aclValue();
            AssertUtil.isTrue(!permissions.contains(aclValue),"没有权限");
        }
        return pjp.proceed();
    }
}
