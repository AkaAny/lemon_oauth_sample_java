package com.hduhelp.apidemo.api.oauth;

import com.hduhelp.apidemo.api.HttpUtils;
import com.hduhelp.apidemo.api.oauth.model.TokenResponse;
import com.hduhelp.apidemo.api.oauth.model.ValidateResponse;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Component
@PropertySource("classpath:application.yml")
@ConfigurationProperties("oauth.hduhelp")
public class OAuthAPI {
    private static final String RESPONSE_TYPE="code";

    @Value("${host}")
    private String host;

    public String getHost(){
        return host;
    }

    @Value("${client_id}")
    private String clientID;

    @Value("${client_secret}")
    private String clientSecret;

    @Value("${redirect_url}")
    private String redirectUrl;

    public String getAuthUrl(String state) {
        String subUrl="/oauth/authorize";
        Map<String,String> queryMap=new HashMap<>();
         queryMap.put("response_type",RESPONSE_TYPE);
               queryMap.put("client_id",clientID);
        queryMap.put("redirect_uri",redirectUrl);
        queryMap.put("state",state);
        return HttpUtils.makeHttpUrl(host,subUrl,queryMap).toString();
    }


    public TokenResponse getAccessToken(String code) throws IOException,IllegalStateException {
        String subUrl="/oauth/token";
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("client_id",clientID);
        queryMap.put("client_secret",clientSecret);
        queryMap.put("grant_type","authorization_code");
        queryMap.put("code",code);
        Request.Builder builder=new Request.Builder();
        Request request= builder.url(HttpUtils.makeHttpUrl(host,subUrl,queryMap)).build();
        return HttpUtils.sendAndGetDeserializedResponse(request,TokenResponse.class);
    }

    public ValidateResponse validate(String accessToken) throws IOException,IllegalStateException {
        String subUrl="/oauth/token/validate";
        Request.Builder builder=new Request.Builder();
        builder.url(HttpUtils.makeHttpUrl(host,subUrl,null));
        builder.header("Authorization","token "+accessToken);
        return HttpUtils.sendAndGetDeserializedResponse(builder.build(), ValidateResponse.class);
    }
}