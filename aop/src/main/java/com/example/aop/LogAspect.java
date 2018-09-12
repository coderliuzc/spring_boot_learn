package com.example.aop;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Enumeration;

//使用AOP打印日志
@Component
@Aspect
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);
    private static final ThreadLocal<Long> timeThreadLocal = new ThreadLocal<>();

    @Order(10)//同个切面优先级，越小优先级越高
    //使用execution表达式定义切点
    @Pointcut("execution(* com.example.aop.TestController.*(..))")
    public void log() {}

    @Before("log()")
    public void before(JoinPoint joinPoint) {
        timeThreadLocal.set(System.currentTimeMillis());
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //获取请求的request
        HttpServletRequest request = attributes.getRequest();
        //正在被通知的方法相关信息
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取被拦截的方法
        Method method = methodSignature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        logger.info("AOP begin");
        logger.info("AOP before()方法, 请求开始方法:{}", method.getDeclaringClass() + "." + methodName + "()");
        //获取所有请求参数key和value
        String keyValue = getReqParameter(request);
        logger.info("请求url = {}", request.getRequestURL().toString());
        logger.info("请求方法requestMethod = {}", request.getMethod());
        logger.info("请求资源uri = {}", request.getRequestURI());
        logger.info("所有的请求参数 key：value = {}", keyValue);
    }

    @After("log()")
    public void after() {
        logger.info("AOP的after()方法");
    }

    //controller请求结束返回时调用
    @AfterReturning(returning = "result", pointcut = "log()")
    public Object afterReturn(Object result) {
        logger.info("AOP afterReturn()方法,返回值result = {}", result.toString());
        long startTime = timeThreadLocal.get();
        double callTime = (System.currentTimeMillis() - startTime) / 1000.0;
        logger.info("调用controller花费时间time = {}s", callTime);
        logger.info("AOP end");
        return result;
    }

    private String getReqParameter(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Enumeration<String> enumeration = request.getParameterNames();
        JSONArray jsonArray = new JSONArray();
        while (enumeration.hasMoreElements()) {
            String key = enumeration.nextElement();
            String value = request.getParameter(key);
            JSONObject json = new JSONObject();
            json.put(key, value);
            jsonArray.add(json);
        }
        return jsonArray.toString();
    }
}
