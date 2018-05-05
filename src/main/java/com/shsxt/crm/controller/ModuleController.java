package com.shsxt.crm.controller;

import com.shsxt.base.BaseController;
import com.shsxt.crm.constants.CrmConstant;
import com.shsxt.crm.dto.ModuleDto;
import com.shsxt.crm.model.ResultInfo;
import com.shsxt.crm.po.Role;
import com.shsxt.crm.query.ModuleQuery;
import com.shsxt.crm.query.RoleQuery;
import com.shsxt.crm.service.ModuleService;
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
@RequestMapping("module")
public class ModuleController extends BaseController {

    @Resource
    private ModuleService moduleService;

    @RequestMapping("index")
    public  String index(){
        return "module";
    }


    @RequestMapping("queryModulesByParams")
    @ResponseBody
    public Map<String,Object> queryModulesByParams(
                        @RequestParam(defaultValue = "1")Integer page,
                        @RequestParam(defaultValue = "10") Integer rows,
                        ModuleQuery moduleQuery
    ){
        moduleQuery.setPageNum(page);
        moduleQuery.setPageSize(rows);
        return  moduleService.queryForPage(moduleQuery);
    }

}
