package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/20/2018.
 */

public class ProfileUpdateReqVo implements Serializable
{

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("password")
    @Expose
    public String password;
    @SerializedName("username")
    @Expose
    public String username;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("email")
    @Expose
    public String email;
    private final static long serialVersionUID = 3504971060225626010L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("name", name).append("password", password).append("username", username).append("phone", phone).append("email", email).toString();
    }

}
