package com.team07.online_shopping_mall.exception;

import com.team07.online_shopping_mall.common.ApiRestReasponse;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ApiRestReasponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return handleBindingResult(e.getBindingResult());
    }

    private ApiRestReasponse handleBindingResult(BindingResult result) {
        //把异常处理为对外暴露的提示
        List<String> list = new ArrayList<>();
        if (result.hasErrors()) {
            List<ObjectError> allErrors = result.getAllErrors();
            for (ObjectError objectError : allErrors) {
                String message = objectError.getDefaultMessage();
                list.add(message);
            }
        }
        if (list.size() == 0) {
            return ApiRestReasponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR);
        }
        return ApiRestReasponse.error(MallExceptionEnum.REQUEST_PARAM_ERROR.getCode(), list.toString());
    }
}
