package com.why.satoken.entity.base;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)

import lombok.Data;

import java.io.Serializable;


public class Result<T> implements Serializable {
    private final String code;
    private final String subCode;
    private final String message;
    private final Boolean success;
    private final T data;

    public String getCode() {
        return code;
    }

    public String getSubCode() {
        return subCode;
    }

    public String getMessage() {
        return message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public Result() {
        this((String)null, (String)null, (Boolean)null);
    }

    private Result(String code, String message, Boolean success) {
        this(code, message, success, null);
    }

    private Result(String code, String message, Boolean success, T data) {
        this(code, message, success, data, code);
    }

    private Result(String code, String message, Boolean success, T data, String subCode) {
        this.code = code;
        this.message = message;
        this.success = success;
        this.data = data;
        this.subCode = subCode;
    }
    public static <T> Result<T> createSuccess(T data) {
        return new Result("200", "成功", true, data);
    }
    public static <T> Result<T> createError(T data) {
        return new Result("500", "失败", false, data);
    }

    public static <T> Result<T> createError(String message, T data) {
        return new Result("500", message, false, data);
    }

}
