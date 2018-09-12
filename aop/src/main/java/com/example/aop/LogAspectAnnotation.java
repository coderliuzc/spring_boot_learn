package com.example.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

//使用AOP做登录认证
@Component
@Aspect
public class LogAspectAnnotation {

    @Order(5)//同个切面优先级，越小优先级越高
    //使用自定义的注解设置切点
    @Pointcut("@annotation(com.example.aop.Login)")
    public void pointcut() {}

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint point){

        //正在被通知的方法相关信息
        MethodSignature signature = (MethodSignature) point.getSignature();
        //获取被拦截的方法
        Method method = signature.getMethod();
        //获取被拦截的方法名
        String methodName = method.getName();
        //返回的结果
        Object result = null;
        //返回方法参数
        Object[] args = point.getArgs();
        for(Object arg : args) {
            //获取request请求
            if(arg instanceof HttpServletRequest) {
                HttpServletRequest request = (HttpServletRequest) arg;
                //判断用户是否登录
                if(!isLogin(request)){
                    result = "没有登录";
                }
            }
        }
        try{
            if(result == null)
                // 一切正常的情况下，继续执行被拦截的方法
                result = point.proceed();
        }catch (Throwable e){
            result = "发生异常";
        }
        return result;
    }

    //判断是否登录
    private boolean isLogin(HttpServletRequest request) {

        return false;
    }
}
