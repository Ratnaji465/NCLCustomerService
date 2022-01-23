package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 8/27/2018.
 */

public class DistributorsList implements Serializable {

    @SerializedName("customer_id")
    @Expose
    public int customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("credit_limit")
    @Expose
    public String creditLimit;
    @SerializedName("osa")
    @Expose
    public int osa;
    @SerializedName("ageing")
    @Expose
    public String ageing;
    @SerializedName("code")
    @Expose
    public String code;
    @SerializedName("address")
    @Expose
    public String address;
    @SerializedName("company_name")
    @Expose
    public String companyName;
    private final static long serialVersionUID = 6484367539281337106L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).append("creditLimit", creditLimit).append("osa", osa).append("ageing", ageing).append("code", code).append("address", address).append("companyName", companyName).toString();
    }
}
