package cn.weedien.countdown.common.log;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.TimeInterval;
import cn.weedien.countdown.common.exception.BusinessException;
import cn.weedien.countdown.util.HttpContextUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Aspect
@Component
public class GlobalLogAspect extends BaseAspectSupport {

    @Value("${spring.profiles.active}")
    private String active;

    @Pointcut("execution(public * cn.weedien.countdown.controller.*.*(..))")
    public void log() {
    }

    @Around("log()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {

        LogSubject logSubject = new LogSubject(active);
        // 记录时间定时器
        TimeInterval timer = DateUtil.timer(true);

        buildCommonLog(logSubject, joinPoint);

        // 执行结果
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            // 需要保证所有经过Controller处理的异常都是BusinessException
            // 因为这部分异常是需要记录业务处理时间的，而其他异常大概率为请求参数异常，没有经过业务代码处理，无需记录处理时间
            RuntimeException ex;
            if (e instanceof BusinessException businessException) {
                logSubject.setCode(businessException.getCode().value());
                ex = businessException;
            } else {
                logSubject.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
                ex = new BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
            }

            logSubject.setMessage(e.getMessage());
            // 执行消耗时间
            String endTime = timer.intervalPretty();
            logSubject.setSpendTime(endTime);

            String jsonLog = logSubject.toString();
            log.error(jsonLog);

            throw ex;
        }
        logSubject.setCode(HttpStatus.OK.value());
        logSubject.setResult(result);
        // 执行消耗时间
        String endTime = timer.intervalPretty();
        logSubject.setSpendTime(endTime);

        String jsonLog = logSubject.toString();
        log.info(jsonLog);
        return result;
    }

    private void buildCommonLog(LogSubject log, ProceedingJoinPoint joinPoint) {
        // 执行参数
        Method method = resolveMethod(joinPoint);
        log.setParameter(getParameter(method, joinPoint.getArgs()));

        HttpServletRequest request = HttpContextUtil.getRequest();
        // 接口请求时间
        log.setStartTime(DateUtil.now());
        // 请求链接
        log.setUrl(request.getRequestURL().toString());
        // 请求方法GET,POST等
        log.setMethod(request.getMethod());
        // 请求设备信息
        log.setDevice(HttpContextUtil.getDevice());
        // 请求地址
        log.setIp(HttpContextUtil.getIpAddr());
    }

    /**
     * 根据方法和传入的参数获取请求参数
     */
    private Object getParameter(Method method, Object[] args) {
        List<Object> argList = new ArrayList<>();
        Parameter[] parameters = method.getParameters();
        Map<String, Object> map = new HashMap<>();
        for (int i = 0; i < parameters.length; i++) {
            // 将RequestBody注解修饰的参数作为请求参数
            RequestBody requestBody = parameters[i].getAnnotation(RequestBody.class);
            // 将RequestParam注解修饰的参数作为请求参数
            RequestParam requestParam = parameters[i].getAnnotation(RequestParam.class);
            String key = parameters[i].getName();
            if (requestBody != null) {
                argList.add(args[i]);
            } else if (requestParam != null) {
                map.put(key, args[i]);
            } else {
                map.put(key, args[i]);
            }
        }
        if (!map.isEmpty()) {
            argList.add(map);
        }
        if (argList.isEmpty()) {
            return null;
        } else if (argList.size() == 1) {
            return argList.getFirst();
        } else {
            return argList;
        }
    }
}