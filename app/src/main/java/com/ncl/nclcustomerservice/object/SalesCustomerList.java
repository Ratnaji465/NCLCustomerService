package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 12/3/2018.
 */

public class SalesCustomerList implements Serializable{
    @SerializedName("customer_id")
    @Expose
    public int customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("contract_list")
    @Expose
    public List<SalesContractList> contractList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).append("contractList", contractList).toString();
    }

}
