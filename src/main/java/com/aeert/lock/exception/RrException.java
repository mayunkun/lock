/**
 * Copyright (c) 2016-2019 人人开源 All rights reserved.
 * <p>
 * https://www.renren.io
 * <p>
 * 版权所有，侵权必究！
 */

package com.aeert.lock.exception;

/**
 * @Author l'amour solitaire
 * @Description 自定义异常
 * @Date 2020/11/17 下午2:42
 **/
public class RrException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public RrException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public RrException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public RrException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public RrException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


}
