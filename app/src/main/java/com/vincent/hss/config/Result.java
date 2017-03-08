package com.vincent.hss.config;

import java.io.Serializable;

/**
 * description ：网络请求的结果返回封装
 * project name：MyAppProject
 * author : Vincent
 * creation date: 2017/2/21 16:20
 *
 * @version 1.0
 */

public class Result<T> implements Serializable {

    private String status;
    private String errorCode;
    private String msg;
    private T data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
