/**
 * Copyright 2018 人人开源 http://www.renren.io
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package io.renren.service.impl;


import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.exception.RRException;
import io.renren.common.utils.BeanUtil;
import io.renren.common.validator.Assert;
import io.renren.config.ApiContants;
import io.renren.dao.UserDao;
import io.renren.entity.TokenEntity;
import io.renren.entity.UserEntity;
import io.renren.form.LoginForm;
import io.renren.form.inner.WechatEntity;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
	@Autowired
	private TokenService tokenService;

	@Override
	public UserEntity queryByMobile(String mobile) {
		UserEntity userEntity = new UserEntity();
		userEntity.setMobile(mobile);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public UserEntity queryByOpenid(String openid) {
		UserEntity userEntity = new UserEntity();
		userEntity.setOpenid(openid);
		return baseMapper.selectOne(userEntity);
	}

	@Override
	public void saveOrUpdateWechatUser(long userId, WechatEntity wechatEntity) {
        UserEntity userEntity = new UserEntity();
	    if(userId != 0){
            userEntity.setUserId(userId);
            userEntity.setUpdateTime(new Date());
        } else {
            userEntity.setCreateTime(new Date());
            userEntity.setPassword(DigestUtils.sha256Hex(ApiContants.WECHAT_PWD));
        }
		BeanUtil.copyEntity(wechatEntity, userEntity);
        this.insertOrUpdate(userEntity);
	}

	/**
	 * 手机号码登录
	 * @param form    登录表单
	 * @return
	 */
	@Override
	public Map<String, Object> login(LoginForm form) {
		UserEntity user = queryByMobile(form.getMobile());
		Assert.isNull(user, "手机号或密码错误");

		//密码错误
		if(!user.getPassword().equals(DigestUtils.sha256Hex(form.getPassword()))){
			throw new RRException("手机号或密码错误");
		}

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

    /**
	 * 微信用户登录
	 * @param wechatEntity    登录表单
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public Map<String, Object> wechatLogin(long userId, WechatEntity wechatEntity) {
        saveOrUpdateWechatUser(userId, wechatEntity);
		UserEntity user = queryByOpenid(wechatEntity.getOpenid());

		//获取登录token
		TokenEntity tokenEntity = tokenService.createToken(user.getUserId());

		Map<String, Object> map = new HashMap<>(2);
		map.put("token", tokenEntity.getToken());
		map.put("expire", tokenEntity.getExpireTime().getTime() - System.currentTimeMillis());

		return map;
	}

}
