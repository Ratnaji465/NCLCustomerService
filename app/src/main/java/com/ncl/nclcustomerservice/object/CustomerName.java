package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 12/28/2018.
 */

public class CustomerName implements Serializable{
    @SerializedName("customer_name")
    @Expose
    public String customerName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerName", customerName).toString();
    }
}
