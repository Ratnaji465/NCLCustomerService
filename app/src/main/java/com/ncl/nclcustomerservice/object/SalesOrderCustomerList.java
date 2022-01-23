package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 12/3/2018.
 */

public class SalesOrderCustomerList implements Serializable {
    @SerializedName("customers_list")
    @Expose
    public List<SalesCustomerList> customersList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customersList", customersList).toString();
    }
}
