package com.aeert.lock.annotation;

import java.lang.annotation.*;
import java.nio.file.OpenOption;

/**
 * @Author l'amour solitaire
 * @Description 用于标记redis锁
 * @Date 2020/11/17 下午3:14
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RedisLock {

    /**
     * redis锁的key值
     * Example: @RedisLock(key = "targetClass + methodName + ':' + #p0")
     */
    String key() default "";

    /**
     * redis锁的有效期(单位:秒)
     **/
    long expires() default 10L;

}
