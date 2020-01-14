package com.zxq.cloud.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller切面处理
 * @author zxq
 * @date 2020/1/14 15:02
 **/
@Component
@Aspect
@Slf4j
public class ControllerAspect {

    /**
     * 指定切点
     * 匹配 com.zxq.cloud.controller包及其子包下的所有类的所有方法
     */
    @Pointcut("execution(* com.zxq.cloud.controller..*.*(..))")
    public void controllerPointcut() {}

    /**
     * 环绕通知,环绕增强，相当于MethodInterceptor
     * @param pjp
     * @return
     */
    @Around("controllerPointcut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable{
        // 获取请求参数
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        StringBuffer requestURL = request.getRequestURL();
        String remoteAddr = request.getRemoteAddr();

        // 计时开始
        StopWatch clock = new StopWatch();
        clock.start();

        Object[] objs = pjp.getArgs();

        Object result = null;
        try {
            result = pjp.proceed();
        } catch (Exception e) {
            // 监控的参数
            log.error("方法执行异常==> 请求URL：{}，请求地址：{}，参数：{}，异常：{}", requestURL, remoteAddr, objs, e);
            throw e;
        }
        // 计时结束
        clock.stop();

        log.info("方法执行正常==> 请求URL：{}，请求地址：{}，执行时间：{} 毫秒，参数：{}", requestURL, remoteAddr, clock.getTotalTimeMillis(), objs);

        return result;
    }
}
