package com.jwolbert.testablefunctions.annotation;
 
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
 
@Aspect
@Component
public class TestableAspect {
 
    @Around("@annotation(Testable)")
    public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
 
        System.out.println("Input :\n" + joinPoint.getArgs()[0]);
 
        Object result = joinPoint.proceed();

        System.out.println(joinPoint.getSignature());

        System.out.println(joinPoint.getSourceLocation());
 
        System.out.println(result);
 
        return result;
    }
 
}
