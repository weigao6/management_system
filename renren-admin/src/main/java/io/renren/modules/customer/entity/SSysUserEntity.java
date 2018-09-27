package io.renren.modules.customer.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 
 * @author wei.gao
 * @email weigao_work@163.com
 * @date 2018-09-27 15:54:03
 */
@TableName("s_sys_user")
public class SSysUserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 系统生成，生成规则待定。用于API调用过程中身份验证，主键
	 */
	@TableId
	private String customerNo;
	/**
	 * 1-个人，2-企业
	 */
	private Integer userType;
	/**
	 * 签名公钥（仅用来验证报文的有效性，不需要存留多份）
	 */
	private String pubKey;
	/**
	 * 0-停用，1-正常
	 */
	private Integer serviceState;
	/**
	 * 自研签章开关0-停用，1-正常
	 */
	private Integer signService;
	/**
	 * 0-注销，1-正常，2-冻结,3-删除
	 */
	private Integer userState;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 绑定ip地址，数组的json格式
	 */
	private String ipAddress;
	/**
	 * 身份验证标识
	 */
	private String token;
	/**
	 * 用户回调地址
	 */
	private String pushUrl;

	/**
	 * 设置：系统生成，生成规则待定。用于API调用过程中身份验证，主键
	 */
	public void setCustomerNo(String customerNo) {
		this.customerNo = customerNo;
	}
	/**
	 * 获取：系统生成，生成规则待定。用于API调用过程中身份验证，主键
	 */
	public String getCustomerNo() {
		return customerNo;
	}
	/**
	 * 设置：1-个人，2-企业
	 */
	public void setUserType(Integer userType) {
		this.userType = userType;
	}
	/**
	 * 获取：1-个人，2-企业
	 */
	public Integer getUserType() {
		return userType;
	}
	/**
	 * 设置：签名公钥（仅用来验证报文的有效性，不需要存留多份）
	 */
	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}
	/**
	 * 获取：签名公钥（仅用来验证报文的有效性，不需要存留多份）
	 */
	public String getPubKey() {
		return pubKey;
	}
	/**
	 * 设置：0-停用，1-正常
	 */
	public void setServiceState(Integer serviceState) {
		this.serviceState = serviceState;
	}
	/**
	 * 获取：0-停用，1-正常
	 */
	public Integer getServiceState() {
		return serviceState;
	}
	/**
	 * 设置：自研签章开关0-停用，1-正常
	 */
	public void setSignService(Integer signService) {
		this.signService = signService;
	}
	/**
	 * 获取：自研签章开关0-停用，1-正常
	 */
	public Integer getSignService() {
		return signService;
	}
	/**
	 * 设置：0-注销，1-正常，2-冻结,3-删除
	 */
	public void setUserState(Integer userState) {
		this.userState = userState;
	}
	/**
	 * 获取：0-注销，1-正常，2-冻结,3-删除
	 */
	public Integer getUserState() {
		return userState;
	}
	/**
	 * 设置：创建时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	/**
	 * 获取：创建时间
	 */
	public Date getCreateTime() {
		return createTime;
	}
	/**
	 * 设置：更新时间
	 */
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	/**
	 * 获取：更新时间
	 */
	public Date getUpdateTime() {
		return updateTime;
	}
	/**
	 * 设置：绑定ip地址，数组的json格式
	 */
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	/**
	 * 获取：绑定ip地址，数组的json格式
	 */
	public String getIpAddress() {
		return ipAddress;
	}
	/**
	 * 设置：身份验证标识
	 */
	public void setToken(String token) {
		this.token = token;
	}
	/**
	 * 获取：身份验证标识
	 */
	public String getToken() {
		return token;
	}
	/**
	 * 设置：用户回调地址
	 */
	public void setPushUrl(String pushUrl) {
		this.pushUrl = pushUrl;
	}
	/**
	 * 获取：用户回调地址
	 */
	public String getPushUrl() {
		return pushUrl;
	}
}
