package com.hduhelp.apidemo.api.oauth.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hduhelp.apidemo.model.BaseResponse;

public class TokenResponse extends BaseResponse{

    @Override
    public boolean isSuccess() {
        return super.isSuccess();
    }

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("access_token_expire")
    public Long accessTokenExpire;
    @SerializedName("refresh_token")
    public String refreshToken;
    @SerializedName("refresh_token_expire")
    public String refreshTokenExpire;
    @SerializedName("staff_id")
    public String staffID;

    public String getAccessToken() {
        return accessToken;
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
