package com.hduhelp.apidemo.oauth.controller.model;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.hduhelp.apidemo.common.compact.FromGoResponse;

public class ValidateResponse extends FromGoResponse {
    public boolean isValid() {
        return isSuccess();
    }

    @SerializedName("access_token")
    public String accessToken;
    @SerializedName("access_token_expire")
    public String accessTokenExpire;
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
