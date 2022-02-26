package com.team07.online_shopping_mall.exception;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 处理统一异常的handler
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handleException(Exception e) {
        return ApiRestReasponse.error(MallExceptionEnum.SYSTEM_ERROR);
    }

    @ExceptionHandler(MallException.class)
    @ResponseBody
    public Object handleMallException(MallException e) {
        return ApiRestReasponse.error(e.getCode(), e.getMessage());
    }
}
