package io.renren.common.exception;

/**
 * @author wei.gao
 * @description 错误码描述
 * @date 2018/9/29 10:40
 */

public enum RRExceptionCode {

    /** 没有登录 */
    SUCCESS(0, "success"),

    /** 没有登录 */
    NOT_LOGIN(400, "没有登录"),

    /** 发生异常 */
    EXCEPTION(500, "发生异常"),

    /** 系统错误 */
    SYS_ERROR(402, "系统错误"),

    /** 参数错误 */
    PARAMS_ERROR(403, "参数错误 "),

    /** 不支持或已经废弃 */
    NOT_SUPPORTED(410, "不支持或已经废弃"),

    /** token错误 */
    INVALID_TOKEN(444, "无效的token"),

    /** 太频繁的调用 */
    TOO_FREQUENT(445, "太频繁的调用"),

    /** 未知的错误 */
    UNKNOWN_ERROR(499, "未知错误");

    private RRExceptionCode(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }

    private int code;
    private String msg;
}

