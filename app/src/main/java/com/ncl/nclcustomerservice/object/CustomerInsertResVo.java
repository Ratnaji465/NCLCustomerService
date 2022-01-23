package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/28/2019.
 */

public class CustomerInsertResVo implements Serializable
{
    @SerializedName("customer_id")
    @Expose
    public Integer customerId;
    private final static long serialVersionUID = -4042132417804090133L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).toString();
    }

}
