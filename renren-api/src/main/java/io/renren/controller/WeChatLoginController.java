package io.renren.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.toolkit.StringUtils;
import io.renren.annotation.Login;
import io.renren.annotation.MethodLog;
import io.renren.common.utils.BeanUtil;
import io.renren.common.utils.HttpClientSender;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.config.ApiContants;
import io.renren.config.ApiProperties;
import io.renren.entity.UserEntity;
import io.renren.form.WeChatLoginForm;
import io.renren.form.inner.WechatEntity;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 微信登录接口
 *
 * @author wei.gao
 * @email weigao_work@163.com
 * @date 2018-09-30 16:28
 */
@RestController
@RequestMapping("/api/v1.0/wechat/")
@Api(tags="微信登录接口")
public class WeChatLoginController {

    private static final Logger logger = LoggerFactory.getLogger(WeChatLoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ApiProperties apiProperties;

//    @Encrypt
//    @Decrypt
    @PostMapping("login")
    @ApiOperation("微信登录")
    @MethodLog(value = "微信登录api", printParam = true)
    public R login(@RequestBody WeChatLoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        String code = form.getCode();
        String appId = apiProperties.getAppId();
        String appSecret = apiProperties.getAppSecret();

        if(StringUtils.isEmpty(code) || StringUtils.isEmpty(appId) || StringUtils.isEmpty(appSecret)){
            return R.error("code不能为空");
        }
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("?appid=").append(appId).append("&secret=").append(appSecret).append("&js_code=")
                .append(code).append("&grant_type=authorization_code");

        String response = null;
        try {
            logger.info("开始调用微信服务端");
            byte[] responseBytes = HttpClientSender.sendHttpGet(ApiContants.WECHAT_HOST, ApiContants.WECHAT_METHOD, strBuilder.toString());
            response = new String(responseBytes, "utf-8");
        } catch (IOException e) {
            logger.error("调用微信服务端异常" + e.getLocalizedMessage());
            return R.error("code无效或微信服务异常");
        }
        JSONObject userObj = JSON.parseObject(response);
//        int number = userObj.getInteger("errcode");
//        if(number != 0){
//            logger.error("code异常，code： " + number);
//            return R.error(userObj.getString("errmsg"));
//        }
        WechatEntity wechatEntity = JSON.parseObject(response, WechatEntity.class);
        wechatEntity.setSessionKey(userObj.getString("session_key"));
        //用户登录
//        WechatEntity wechatEntity = new WechatEntity();
//        wechatEntity.setOpenid("321123");
//        wechatEntity.setMobile("15010467927");
//        wechatEntity.setUsername("15010467927");

        UserEntity user = userService.queryByOpenid(wechatEntity.getOpenid());
        long userId = 0;
        if(user != null){
            userId = user.getUserId();
        }
        Map<String, Object> map = userService.wechatLogin(userId, wechatEntity);

        return R.ok(map);
    }

}
