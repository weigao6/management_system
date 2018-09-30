package io.renren.controller;


import com.cxytiandi.encrypt.anno.Decrypt;
import com.cxytiandi.encrypt.anno.Encrypt;
import io.renren.annotation.Login;
import io.renren.annotation.MethodLog;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.form.LoginForm;
import io.renren.service.TokenService;
import io.renren.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Map;

/**
 * 登录接口
 *
 * @author wei.gao
 * @email weigao_work@163.com
 * @date 2018-09-24 16:28
 */
@RestController
@RequestMapping("/api/v1.0/")
@Api(tags="登录接口")
public class ApiLoginController {

    private static final Logger logger = LoggerFactory.getLogger(ApiLoginController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;


//    @Encrypt
//    @Decrypt
    @PostMapping("login")
    @ApiOperation("登录")
    @MethodLog(value = "登录api", printParam = true)
    public R login(@RequestBody LoginForm form){
        //表单校验
        ValidatorUtils.validateEntity(form);

        //用户登录
        Map<String, Object> map = userService.login(form);

        return R.ok(map);
    }

    @Login
    @PostMapping("logout")
    @ApiOperation("退出")
    @MethodLog(value = "退出api", printParam = true)
    public R logout(@ApiIgnore @RequestAttribute("userId") long userId){
        tokenService.expireToken(userId);
        logger.info("用户" + userId + "已注销");
        return R.ok();
    }

}
