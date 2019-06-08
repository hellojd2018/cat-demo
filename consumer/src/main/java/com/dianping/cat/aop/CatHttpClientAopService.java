package com.dianping.cat.aop;

import com.dianping.cat.Cat;
import com.dianping.cat.CatConstants;
import com.dianping.cat.message.Transaction;
import org.apache.http.client.methods.HttpUriRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

@Aspect
public class CatHttpClientAopService {
    @Around("execution(* org.apache.http.client.HttpClient.execute(..))")
    public Object aroundMethod(ProceedingJoinPoint pjp) {
        MethodSignature joinPointObject = (MethodSignature) pjp.getSignature();
        Method method = joinPointObject.getMethod();
        Parameter[] parameters = method.getParameters();
        Object result=null;
        if (parameters.length > 0) {
            Object parameter0 = pjp.getArgs()[0];
            if (parameter0 instanceof HttpUriRequest) {
                HttpUriRequest httpUriRequest = (HttpUriRequest) pjp.getArgs()[0];
                String url = httpUriRequest.getURI().toString();
                Transaction t = Cat.newTransaction(CatConstants.TYPE_REMOTE_CALL, url);
                try {
                    result=pjp.proceed();
                    Cat.logEvent(CatConstants.TYPE_REMOTE_CALL+".method",httpUriRequest.getMethod());
                    t.setSuccessStatus();
                    t.complete();
                } catch (Throwable e) {
                    t.setStatus(e);
                    Cat.logError(e);
                } finally {
                    t.complete();
                }
            }else {
                try {
                    result= pjp.proceed();
                } catch (Throwable throwable) {
                    Cat.logError(throwable);
                }
            }
        }else {
            try {
                result= pjp.proceed();
            } catch (Throwable throwable) {
                Cat.logError(throwable);
            }
        }
            return result;
    }

}
