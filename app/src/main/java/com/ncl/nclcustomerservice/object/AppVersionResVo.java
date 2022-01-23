package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class AppVersionResVo {

    @SerializedName("app_id")
    @Expose
    public String appId;
    @SerializedName("app_version_id")
    @Expose
    public String appVersionId;
    @SerializedName("app_version_name")
    @Expose
    public String appVersionName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("appId", appId).append("appVersionId", appVersionId).append("appVersionName", appVersionName).toString();
    }
}
