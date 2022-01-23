package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 10/3/2018.
 */

public class ConvertCheckResVo {
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("contact_name")
    @Expose
    public String contactName;
    @SerializedName("customer_check")
    @Expose
    public String customerCheck;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerName", customerName).append("contactName", contactName).append("customerCheck", customerCheck).toString();
    }
}
