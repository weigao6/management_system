package io.renren.modules.customer.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.customer.entity.SSysUserEntity;

import java.util.Map;

/**
 * 
 *
 * @author wei.gao
 * @email weigao_work@163.com
 * @date 2018-09-27 15:54:03
 */
public interface SSysUserService extends IService<SSysUserEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

