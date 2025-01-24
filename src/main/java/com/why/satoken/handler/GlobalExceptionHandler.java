package com.why.satoken.handler;

import cn.dev33.satoken.exception.NotPermissionException;
import cn.dev33.satoken.util.SaResult;
import com.why.satoken.entity.base.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 未被捕获的异常会到这里
     * NotPermissionException除外，因为下面有更详细的定义
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> handlerException(Exception e) {
        e.printStackTrace();
        return Result.createError(e.getMessage());
    }

    /**
     * 没有权限会到这个地方
     * @param e
     * @return
     */
    @ExceptionHandler
    public Result<String> handlerException(NotPermissionException e) {
        e.printStackTrace();
        System.out.println("没有权限");
        return Result.createError(e.getMessage());
    }
}