package io.renren.form.inner;

/**
 * @author wei.gao
 * @description 描述
 * @date 2018/9/30 15:32
 */
public class WechatEntity {

    private String openid;

    private String sessionKey;

    private String unionid;

    private String name;

    /**
     * 性别 0-男，1-女,2-其他
     */
    private String sex;

    /**
     * 证件类型0-居民身份证
     * 1-护照
     * B-港澳居民往来内地通行证
     * C-台湾居民来往大陆通行证
     * E-户口簿
     * F-临时居民身份证
     */
    private String identityType;

    private String identityCode;

    /**
     * 头像地址
     */
    private String imgUrl;

    /**
     * 用户名
     */
    private String username;
    /**
     * 手机号
     */
    private String mobile;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getIdentityType() {
        return identityType;
    }

    public void setIdentityType(String identityType) {
        this.identityType = identityType;
    }

    public String getIdentityCode() {
        return identityCode;
    }

    public void setIdentityCode(String identityCode) {
        this.identityCode = identityCode;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
