package com.hduhelp.apidemo.utils;

import com.hduhelp.apidemo.common.compact.FromGoResponse;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.Nullable;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);

    public static HttpUrl makeHttpUrl(String host,String subUrl, @Nullable Map<String,String> queryMap){
        HttpUrl httpUrl= Objects.requireNonNull(HttpUrl.parse(host+subUrl));
        logger.debug("http url:"+httpUrl);
        HttpUrl.Builder builder= httpUrl.newBuilder();
        if (queryMap!=null) {
            for (Map.Entry<String, String> entry : queryMap.entrySet()) {
                builder.addQueryParameter(entry.getKey(), entry.getValue());
            }
        }
        return builder.build();
    }

    public static <T extends FromGoResponse> T sendAndGetDeserializedResponse(Request request, Class<T> classOfT)
            throws IOException,IllegalStateException {
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String json = Objects.requireNonNull(response.body()).string();
        logger.info(json);
        return FromGoResponse.fromJson(json).getData(classOfT);
    }
}