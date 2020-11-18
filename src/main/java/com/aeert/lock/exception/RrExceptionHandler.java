/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.aeert.lock.exception;

import com.aeert.lock.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author l'amour solitaire
 * @Description 异常处理器
 * @Date 2020/11/17 下午2:38
 **/
@Slf4j
@RestControllerAdvice
public class RrExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(RrException.class)
    public R handleRrException(RrException e) {
        log.error(e.getMessage(), e);
        return R.error(e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception e) {
        log.error(e.getMessage(), e);
        return R.error();
    }
}
