package io.renren.annotation;

import java.lang.annotation.*;

/**
 * 方法日志，打印方法开始结束，打印参数，打印方法运算时间
 *
 * @author weigao
 * @date 2018/9/13 15:38
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface MethodLog {

    /**
     * 方法名
     *
     * @return
     */
    String value();

    /**
     * 是否打印参数
     * 打印 - true
     * 不打印 - false(默认)
     *
     * @return
     */
    boolean printParam() default false;

    /**
     * 打印参数排除参数(String 数组)
     *
     * @return
     */
    String[] excludePrintParam() default {};

    /**
     * 是否打印方法执行时间
     * 打印 - true(默认)
     * 不打印 - false
     *
     * @return
     */
    boolean printExecuteTime() default true;
}
