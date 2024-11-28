package com.poptsov.marketplace.aop;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;


@Slf4j
@Aspect
@Component
@Order(1)
public class LoggingAspect {


    @Pointcut("execution(public * com.poptsov.marketplace.service.*.*(..))")
    public void serviceLog() {
    }

    @Pointcut("execution(public * com.poptsov.marketplace.http.rest.*.*(..))")
    public void controllerLog() {
    }

    @Before("controllerLog()")
    public void doBeforeController(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if (attributes != null) {
            request = attributes.getRequest();
        }
        if (request != null) {
            log.info("RUN CONTROLLER: New Request: IP: {}, URL: {}, HTTP_METHOD: {}, CONTROLLER_METHOD: {}", request.getRemoteAddr(), request.getRequestURL().toString(), request.getMethod(), joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        }
    }

    @Before("serviceLog()")
    public void doBeforeService(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String argsString = args.length > 0 ? Arrays.toString(args) : "METHOD HAS NO ARGUMENTS";

        log.info("RUN SERVICE: SERVICE METHOD : {}.{}\n METHOD ARGUMENTS: [{}]", className, methodName, argsString);
    }

    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
    public void doAfterReturningController(Object returnObject) {
        log.info("CONTROLLER RETURN RESULT: {}", returnObject);
    }

    @AfterReturning(returning = "returnObject", pointcut = "serviceLog()")
    public void doAfterReturningService(Object returnObject) {
        log.info("SERVICE RETURN RESULT: {}", returnObject);
    }

    @After("controllerLog()")
    public void doAfterController(JoinPoint joinPoint) {
        log.info("CONTROLLER METHOD EXECUTED SUCCESSFULLY: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    @After("serviceLog()")
    public void doAfterService(JoinPoint joinPoint) {
        log.info("SERVICE METHOD EXECUTED SUCCESSFULLY: {}.{}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName());
    }

    @AfterThrowing(throwing = "ex", pointcut = "controllerLog()")
    public void doControllerAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("Controller exception in {}.{} with arguments {}, Exception message: {}", className, methodName, Arrays.toString(joinPoint.getArgs()), ex.getMessage());

    }

    @AfterThrowing(throwing = "ex", pointcut = "serviceLog()")
    public void doServiceAfterThrowing(JoinPoint joinPoint, Exception ex) {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.error("Service exception in {}.{} with arguments {}, Exception message: {}", className, methodName, Arrays.toString(joinPoint.getArgs()), ex.getMessage());

    }

    @Around("controllerLog()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        log.info("Execution method: {}.{}. Execution time: {}", joinPoint.getSignature().getDeclaringTypeName(), joinPoint.getSignature().getName(), executionTime);
        return proceed;
    }
}
