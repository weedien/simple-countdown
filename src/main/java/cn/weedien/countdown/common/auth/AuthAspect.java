package cn.weedien.countdown.common.auth;

import cn.weedien.countdown.common.exception.CustomException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Aspect
@Component
public class AuthAspect implements HandlerInterceptor {

    @Resource
    private HttpServletRequest request;

    @Around("@annotation(cn.weedien.countdown.common.auth.Auth)")
    public Object authorize(ProceedingJoinPoint joinPoint) throws Throwable {
        boolean isAuthorized = checkAuthorization(request);
        if (isAuthorized) {
            return joinPoint.proceed();
        } else {
            throw new CustomException(HttpStatus.UNAUTHORIZED, "Unauthorized");
        }
    }

    private boolean checkAuthorization(HttpServletRequest request) {
        return "weedien".equals(request.getParameter("auth"));
    }
}