package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/1/2018.
 */

public class CustomerDropDown implements Serializable {

    @SerializedName("Customer_id")
    @Expose
    public String customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("customer_number")
    @Expose
    public String customer_number;
    @SerializedName("CustomerSAPCode")
    @Expose
    public String CustomerSAPCode;
    @SerializedName("Contact_list")
    @Expose
    public List<ContactDropDown> contactList = null;


    private final static long serialVersionUID = -7918679452178200860L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).toString();
    }

}