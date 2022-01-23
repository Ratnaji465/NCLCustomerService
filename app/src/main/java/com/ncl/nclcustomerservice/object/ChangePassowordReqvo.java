package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/17/2018.
 */

public class ChangePassowordReqvo implements Serializable
{

    @SerializedName("password")
    @Expose
    public String password;
    private final static long serialVersionUID = 6721786088783525379L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("password", password).toString();
    }
}
