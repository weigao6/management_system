package io.renren.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author wei.gao
 * @description 描述
 * @date 2018/9/30 14:28
 */

@Component
public class ApiProperties {

    /**
     * 微信appId
     */
    @Value("${wechat.appid}")
    private String appId;

    /**
     * 微信appSecret
     */
    @Value("${wechat.appsecret}")
    private String appSecret;

    public String getAppId() {
        return appId;
    }

    public String getAppSecret() {
        return appSecret;
    }
}
