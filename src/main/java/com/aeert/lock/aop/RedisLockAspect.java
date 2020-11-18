package com.aeert.lock.aop;

import com.aeert.lock.annotation.RedisLock;
import com.aeert.lock.exception.RrException;
import io.vavr.Function3;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.redis.util.RedisLockRegistry;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.concurrent.locks.Lock;

/**
 * @Author l'amour solitaire
 * @Description redis分布式锁的切面
 * @Date 2020/11/17 上午10:47
 **/
@Slf4j
@Aspect
@Component
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RedisLockAspect {

    private final RedisLockRegistry redisLockRegistry;

    @Around(value = "@annotation(redisLock)")
    public Object redisLock(ProceedingJoinPoint joinPoint, RedisLock redisLock) {
        return Try.of(() -> {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            signature.getClass();
            Method method = signature.getMethod();
            Object[] arguments = joinPoint.getArgs();

            String key = keyFormatter.apply(redisLock.key(), method, arguments);
            Lock lock = redisLockRegistry.obtain(key);
            return Try.of(() -> {
                boolean ifLock = lock.tryLock();
                log.info("线程[{}]是否获取到了锁：{}", Thread.currentThread().getName(), ifLock);
                if (ifLock) {
                    return joinPoint.proceed();
                } else {
                    throw new RrException("操作正在进行中～");
                }
            }).onFailure((e) -> {
                log.error("执行核心扫描时出错:{}", e.getMessage());
            }).andFinally(() -> {
                try {
                    lock.unlock();
                    log.info("[{}]解锁成功", key);
                } catch (Exception e) {
                    log.error("解锁dealAction出错:{}", e.getMessage());
                }
            }).get();
        }).onFailure((e) -> log.error("aop redis distributed lock error:{}", e.getLocalizedMessage())).get();
    }

    /**
     * key格式化
     **/
    Function3<String, Method, Object[], String> keyFormatter = (key, method, arguments) -> {
        if (StringUtils.hasText(key)) {
            key = key.replace("targetClass", method.getDeclaringClass().getName() + ".");
            key = key.replace("methodName", method.getName() + ".");
            for (int i = 0; i < arguments.length; i++) {
                key = key.replace("#p" + i, arguments[i].toString() + ",");
            }
            key = key.replace(". + ':' + ", ":");
            key = key.replace(". + ", ".");
            key = key.replace(", + ", ",");
            if (key.endsWith(",")) {
                key = key.substring(0, key.length() - 1);
            }
        } else {
            key = key = method.getDeclaringClass().getName() + "." + method.getName() + ":";
            for (int i = 0; i < arguments.length; i++) {
                key += arguments[i].toString() + ",";
            }
            if (key.endsWith(",") || key.endsWith(":")) {
                key = key.substring(0, key.length() - 1);
            }
        }
        return key;
    };

}

