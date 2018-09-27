package io.renren.modules.customer.controller;

import java.util.Arrays;
import java.util.Map;

import io.renren.common.validator.ValidatorUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.renren.modules.customer.entity.SSysUserEntity;
import io.renren.modules.customer.service.SSysUserService;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.R;



/**
 * 商户管理模块
 *
 * @author wei.gao
 * @email weigao_work@163.com
 * @date 2018-09-27 15:54:03
 */
@RestController
@RequestMapping("customer/ssysuser")
public class SSysUserController {
    @Autowired
    private SSysUserService sSysUserService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("customer:ssysuser:list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = sSysUserService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{customerNo}")
    @RequiresPermissions("customer:ssysuser:info")
    public R info(@PathVariable("customerNo") String customerNo){
        SSysUserEntity sSysUser = sSysUserService.selectById(customerNo);

        return R.ok().put("sSysUser", sSysUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("customer:ssysuser:save")
    public R save(@RequestBody SSysUserEntity sSysUser){
        sSysUserService.insert(sSysUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("customer:ssysuser:update")
    public R update(@RequestBody SSysUserEntity sSysUser){
        ValidatorUtils.validateEntity(sSysUser);
        sSysUserService.updateAllColumnById(sSysUser);//全部更新
        
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("customer:ssysuser:delete")
    public R delete(@RequestBody String[] customerNos){
        sSysUserService.deleteBatchIds(Arrays.asList(customerNos));

        return R.ok();
    }

}
