package com.hduhelp.apidemo.api;

import com.hduhelp.apidemo.model.BaseResponse;
import com.hduhelp.apidemo.api.oauth.OAuthAPI;
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
    private static final Logger logger = LoggerFactory.getLogger(OAuthAPI.class);

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

    public static <T extends BaseResponse> T sendAndGetDeserializedResponse(Request request, Class<T> classOfT)
            throws IOException,IllegalStateException {
        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(request).execute();
        String json = Objects.requireNonNull(response.body()).string();
        logger.debug(json);
        return BaseResponse.fromJson(json).getData(classOfT);
    }
}
