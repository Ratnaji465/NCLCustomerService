package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class CustomerUserReqVo implements Serializable
{
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    private final static long serialVersionUID = 1547636276759910598L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).toString();
    }
}
