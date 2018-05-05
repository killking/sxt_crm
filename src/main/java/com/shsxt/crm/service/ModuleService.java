package com.shsxt.crm.service;

import com.shsxt.base.BaseService;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dao.ModuleDao;
import com.shsxt.crm.dao.RoleDao;
import com.shsxt.crm.dao.UserDao;
import com.shsxt.crm.dto.UserDto;
import com.shsxt.crm.model.UserModel;
import com.shsxt.crm.po.Module;
import com.shsxt.crm.po.User;
import com.shsxt.crm.po.UserRole;
import com.shsxt.crm.utils.AssertUtil;
import com.shsxt.crm.utils.Md5Util;
import com.shsxt.crm.utils.UserIDBase64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kingkill on 2018/4/24.
 */
@Service
public class ModuleService extends BaseService<Module>{
   @Resource
    private ModuleDao moduleDao;

}
