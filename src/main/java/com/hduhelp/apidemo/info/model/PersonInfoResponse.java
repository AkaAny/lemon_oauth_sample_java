package com.hduhelp.apidemo.info.model;

import com.google.gson.annotations.SerializedName;
import com.hduhelp.apidemo.common.compact.FromGoResponse;

public class PersonInfoResponse extends FromGoResponse {
    @SerializedName("STAFFID")
    public String staffID;

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

    @SerializedName("UNITNAME")
    public String unitName;
}
