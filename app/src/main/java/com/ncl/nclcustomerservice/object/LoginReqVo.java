package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/17/2018.
 */

public class LoginReqVo implements Serializable
{

    @SerializedName("users")
    @Expose
    public String users;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("fcmId")
    @Expose
    public String fcmId;
    @SerializedName("deviceid")
    @Expose
    public String deviceid;
    @SerializedName("device_type")
    @Expose
    public String deviceType;
    private final static long serialVersionUID = -8617954960624853520L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("users", users).append("password", password).append("fcmId", fcmId).append("deviceid", deviceid).append("device_type",deviceType).toString();
    }
}
