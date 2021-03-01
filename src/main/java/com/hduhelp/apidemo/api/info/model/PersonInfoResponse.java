package com.hduhelp.apidemo.api.info.model;

import com.google.gson.annotations.SerializedName;
import com.hduhelp.apidemo.model.BaseResponse;

public class PersonInfoResponse extends BaseResponse {
    @SerializedName("GRADE")
    public String grade;

    @SerializedName("STAFFNAME")
    public String staffName;

    @SerializedName("STAFFTYPE")
    public String staffType;

    @SerializedName("STAFSTATE")
    public String staffState;

    @SerializedName("UNITCODE")
    public String unitCode;
}
