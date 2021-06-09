package com.hduhelp.apidemo.common.response;

import org.springframework.lang.Nullable;

public class BaseResponse<T> {
    public boolean cache;
    public int error;
    public String msg;
    public T data;

    public static <T> BaseResponse<T> createSuccess(@Nullable T data){
        BaseResponse<T> response=new BaseResponse<>();
        response.error=0;
        response.msg="success";
        response.data=data;
        return response;
    }

    public static <T> BaseResponse<T> create(int error,String msg){
        BaseResponse<T> response=new BaseResponse<>();
        response.error=error;
        response.msg=msg;
        return response;
    }
}
