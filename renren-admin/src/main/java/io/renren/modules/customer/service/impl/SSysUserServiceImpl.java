package io.renren.modules.customer.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.customer.dao.SSysUserDao;
import io.renren.modules.customer.entity.SSysUserEntity;
import io.renren.modules.customer.service.SSysUserService;


@Service("sSysUserService")
public class SSysUserServiceImpl extends ServiceImpl<SSysUserDao, SSysUserEntity> implements SSysUserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<SSysUserEntity> page = this.selectPage(
                new Query<SSysUserEntity>(params).getPage(),
                new EntityWrapper<SSysUserEntity>()
        );

        return new PageUtils(page);
    }

}
