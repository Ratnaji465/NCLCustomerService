package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 10/1/2018.
 */

public class CustomerListresVo implements Serializable {

    @SerializedName("customer_list")
    @Expose
    public List<CustomerList> customerList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerList", customerList).toString();
    }

}
