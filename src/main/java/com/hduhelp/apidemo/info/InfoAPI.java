package com.hduhelp.apidemo.info;

import com.hduhelp.apidemo.info.model.PersonInfoResponse;
import com.hduhelp.apidemo.info.model.StudentInfoResponse;
import com.hduhelp.apidemo.utils.HttpUtils;
import okhttp3.Request;

import java.io.IOException;

public class InfoAPI {
    private static final String HOST="https://api.hduhelp.com/base";
    public static PersonInfoResponse getPersonInfo(String accessToken) throws IOException {
        Request.Builder builder=new Request.Builder();
        builder.url(HttpUtils.makeHttpUrl(HOST,"/person/info",null));
        builder.header("Authorization", "token "+accessToken);
        return HttpUtils.sendAndGetDeserializedResponse(builder.build(), PersonInfoResponse.class);
    }

    public static StudentInfoResponse getStudentInfo(String accessToken) throws IOException {
        Request.Builder builder=new Request.Builder();
        builder.url(HttpUtils.makeHttpUrl(HOST,"/student/info",null));
        builder.header("Authorization", "token "+accessToken);
        return HttpUtils.sendAndGetDeserializedResponse(builder.build(), StudentInfoResponse.class);
    }
}
