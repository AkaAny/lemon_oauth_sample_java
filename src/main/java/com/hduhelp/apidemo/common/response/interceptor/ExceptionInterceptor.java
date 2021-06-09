package com.hduhelp.apidemo.common.response.interceptor;

import com.hduhelp.apidemo.common.response.ResponseFactory;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
public class ExceptionInterceptor {
    //@RequestMapping
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)") //execution(public * *.controller.*.*(..))
    public void pointCut(){
    }

    @Around("pointCut()") //存在多个Around方法时，按照目标注解的定义顺序执行对应的Around方法
    public Object after(ProceedingJoinPoint pjp){
        try {
            return pjp.proceed();
        } catch (Throwable e) {
            return ResponseFactory.getInstance().create(e);
        }
    }
}
