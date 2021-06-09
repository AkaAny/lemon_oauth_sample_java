package com.hduhelp.apidemo.oauth.service;

import com.hduhelp.apidemo.utils.HttpUtils;
import com.hduhelp.apidemo.oauth.controller.model.TokenResponse;
import com.hduhelp.apidemo.oauth.controller.model.ValidateResponse;
import com.hduhelp.apidemo.oauth.config.OAuthConfig;
import okhttp3.Request;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Service
public class OAuthService {
    private static final String RESPONSE_TYPE="code";

    private final OAuthConfig mOAuthConfig;

    public OAuthService(OAuthConfig config) {
        this.mOAuthConfig = config;
    }

    public String getAuthUrl(String state) {
        String subUrl="/oauth/authorize";
        Map<String,String> queryMap=new HashMap<>();
         queryMap.put("response_type",RESPONSE_TYPE);
               queryMap.put("client_id",mOAuthConfig.getClientID());
        queryMap.put("redirect_uri",mOAuthConfig.getCallbackUrl());
        queryMap.put("state",state);
        return HttpUtils.makeHttpUrl(mOAuthConfig.getHost(),subUrl,queryMap).toString();
    }

    public String getHost(){
        return mOAuthConfig.getHost();
    }

    public TokenResponse getAccessToken(String code, String state) throws IOException,IllegalStateException {
        String subUrl="/oauth/token";
        Map<String,String> queryMap=new HashMap<>();
        queryMap.put("client_id",mOAuthConfig.getClientID());
        queryMap.put("client_secret", mOAuthConfig.getClientSecret());
        queryMap.put("grant_type","authorization_code");
        queryMap.put("code",code);
        queryMap.put("state",state);
        Request.Builder builder=new Request.Builder();
        Request request= builder.url(HttpUtils.makeHttpUrl(mOAuthConfig.getHost(), subUrl,queryMap)).build();
        return HttpUtils.sendAndGetDeserializedResponse(request, TokenResponse.class);
    }

    public ValidateResponse validate(String accessToken) throws IOException,IllegalStateException {
        String subUrl="/oauth/token/validate";
        Request.Builder builder=new Request.Builder();
        builder.url(HttpUtils.makeHttpUrl(mOAuthConfig.getHost(), subUrl,null));
        builder.header("Authorization","token "+accessToken);
        return HttpUtils.sendAndGetDeserializedResponse(builder.build(), ValidateResponse.class);
    }

    public String getCallbackRedirectUrl(){
        return mOAuthConfig.getCallbackRedirectUrl();
    }
}