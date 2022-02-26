package com.team07.online_shopping_mall.common;

import com.team07.online_shopping_mall.exception.MallExceptionEnum;

public class ApiRestReasponse<T> {
    private Integer status;

    private String msg;

    private T data;

    private static final int OK_CODE = 666;

    private static final String OK_MSG = "SUCCESS";

    public ApiRestReasponse(Integer status, String msg, T data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public ApiRestReasponse(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public ApiRestReasponse() {
        this(OK_CODE, OK_MSG);
    }

    public static <T> ApiRestReasponse<T> success() {
        return new ApiRestReasponse<>();
    }

    public static <T> ApiRestReasponse<T> success(T result) {
        ApiRestReasponse<T> response = new ApiRestReasponse<>();
        response.setData(result);
        return response;
    }

    public static <T> ApiRestReasponse<T> error(Integer code, String msg) {
        return new ApiRestReasponse<>(code, msg);
    }

    public static <T> ApiRestReasponse<T> error(MallExceptionEnum ex) {
        return new ApiRestReasponse<>(ex.getCode(), ex.getMsg());
    }

    @Override
    public String toString() {
        return "ApiRestReasponse{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public static int getOkCode() {
        return OK_CODE;
    }

    public static String getOkMsg() {
        return OK_MSG;
    }
}
