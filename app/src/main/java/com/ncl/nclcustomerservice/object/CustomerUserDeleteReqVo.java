package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class CustomerUserDeleteReqVo implements Serializable
{

    @SerializedName("customer_user_id")
    @Expose
    public String customerUserId;
    private final static long serialVersionUID = -8638958717991038013L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerUserId", customerUserId).toString();
    }

}
