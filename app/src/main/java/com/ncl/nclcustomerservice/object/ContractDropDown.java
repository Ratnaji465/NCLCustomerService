package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/30/2018.
 */

public class ContractDropDown implements Serializable
{

    @SerializedName("customer_list")
    @Expose
    public List<Customer> customerList = null;
    private final static long serialVersionUID = 3289791117846928240L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerList", customerList).toString();
    }

}
