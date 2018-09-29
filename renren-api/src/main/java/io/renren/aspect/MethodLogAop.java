package io.renren.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.renren.annotation.MethodLog;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 方法进出日志aop
 *
 * @author weigao
 * @date 2018/9/13 15:22
 */
@Aspect
@Component
public class MethodLogAop {

    /**
     * 指定注解
     */
    @Pointcut("@annotation(io.renren.annotation.MethodLog)")
    public void methodLogPointCut() {
    }

    /**
     * 代理实例
     *
     * @param jp 代理对象
     * @return
     * @throws Throwable
     */
    @Around("methodLogPointCut()")
    public Object methodBefore(ProceedingJoinPoint jp) throws Throwable {
        Signature signature = jp.getSignature();
        if (!(signature instanceof MethodSignature)) {
            throw new IllegalArgumentException("该注解只能用于方法");
        }
        MethodSignature methodSignature = (MethodSignature) signature;
        Method targetMethod = methodSignature.getMethod();
        // 获取执行类日志对象
        Logger logger = LoggerFactory.getLogger(jp.getTarget().getClass());

        MethodLog annotation = targetMethod.getAnnotation(MethodLog.class);
        if (annotation != null) {
            String message = annotation.value();
            try {
                logger.info("方法名:{}|{},开始执行-->", message, targetMethod.getName());
                if (annotation.printParam()) {
                    Object[] args = jp.getArgs();
                    String[] parameterNames = methodSignature.getParameterNames();
                    if (parameterNames == null || parameterNames.length == 0) {
                        logger.info("该方法:{}|{},方法上没有参数", message, targetMethod.getName());
                    } else {
                        JSONObject jsonObject = new JSONObject();
                        for (int i = 0; i < parameterNames.length; i++) {
                            boolean b = true;
                            // 排除的参数
                            if (annotation.excludePrintParam().length > 0) {
                                for (String excludeParam : annotation.excludePrintParam()) {
                                    if (parameterNames[i].equals(excludeParam)) {
                                        b = false;
                                    }
                                }
                            }
                            if (b) {
                                jsonObject.put(parameterNames[i], JSON.toJSON(args[i]));
                            }
                        }
                        logger.info("请求参数-->{}", jsonObject);
                    }
                }
                if (annotation.printExecuteTime()) {
                    long start = System.currentTimeMillis();
                    Object proceed = jp.proceed();
                    long end = System.currentTimeMillis();
                    logger.info("该方法:{}|{},执行该次执行时间-->{}毫秒", message, targetMethod.getName(), (end - start));
                    return proceed;
                } else {
                    return jp.proceed();
                }
            } catch (Throwable throwable) {
                logger.error("方法名:{}|{},发生异常-->{}", message, targetMethod.getName(), throwable.getMessage());
                throw throwable;
            } finally {
                logger.info("方法名:{}|{},执行结束<--", message, targetMethod.getName());
            }
        } else {
            return jp.proceed();
        }
    }

}
