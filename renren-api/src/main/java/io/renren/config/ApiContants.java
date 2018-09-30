package io.renren.config;

/**
 * @author wei.gao
 * @description 描述
 * @date 2018/9/30 14:19
 */
public interface ApiContants {
    /**
     * 微信code2Session 服务地址
     * GET https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     */
    String WECHAT_HOST = "https://api.weixin.qq.com";

    String WECHAT_METHOD = "/sns/jscode2session";

    /**
     * 微信用户默认密码
     */
    String WECHAT_PWD = "1QAZ2WSX.";
}
