package com.hduhelp.apidemo.utils;

import com.google.gson.Gson;
import com.hduhelp.apidemo.common.response.BaseResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ResponseUtils {
    private static final Gson GSON=new Gson();

    public static void responseJson(HttpServletResponse response, HttpStatus status, Object body) throws IOException {
        response.setStatus(status.value());
        String json= GSON.toJson(body);
        response.getOutputStream().write(json.getBytes());
        response.flushBuffer();
    }

    public static <T> ResponseEntity<BaseResponse<T>> responseInController(HttpStatus status, BaseResponse<T> body){
        return new ResponseEntity<>(body,status);
    }
}
