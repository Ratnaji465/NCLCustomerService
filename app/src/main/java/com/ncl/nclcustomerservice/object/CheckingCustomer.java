package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 12/17/2018.
 */

public class CheckingCustomer implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    private final static long serialVersionUID = 6387148890651014070L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).toString();
    }

}
