package com.hduhelp.apidemo.info.model;

import com.google.gson.annotations.SerializedName;
import com.hduhelp.apidemo.common.compact.FromGoResponse;

public class StudentInfoResponse extends FromGoResponse {
    @SerializedName("CLASSID")
    public String classID;

    @SerializedName("DORMBUILDING")
    public String dormBuilding;

    @SerializedName("DORMROOM")
    public String dormRoom;

    @SerializedName("MAJORCODE")
    public String majorCode;

    @SerializedName("MAJORNAME")
    public String majorName;

    @SerializedName("RUXUESJ")
    public String ruXueSJ;

    @SerializedName("STAFFID")
    public String staffID;

    @SerializedName("STAFFNAME")
    public String staffName;

    @SerializedName("TEACHERID")
    public String teacherID;

    @SerializedName("TEACHERNAME")
    public String teacherName;

    @SerializedName("UNITCODE")
    public String unitCode;

    @SerializedName("UNITNAME")
    public String unitName;
}
