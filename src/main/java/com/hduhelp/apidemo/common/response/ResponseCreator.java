package com.hduhelp.apidemo.common.response;

import org.springframework.http.HttpStatus;

public interface ResponseCreator<T> {
    Class<T> getAppliedClass();
    HttpStatus getStatusCode();
    BaseResponse<?> create(T obj);
}
