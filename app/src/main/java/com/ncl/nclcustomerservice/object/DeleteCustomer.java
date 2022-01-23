package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 10/2/2018.
 */

public class DeleteCustomer {
    @SerializedName("customer_id")
    @Expose
    public int customerId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).toString();
    }

}
