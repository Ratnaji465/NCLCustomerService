package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/6/2018.
 */

public class ListContactResVo implements Serializable
{

    @SerializedName("contact_list_Customers")
    @Expose
    public List<ListContactListCustomer> contactListCustomers = null;
    private final static long serialVersionUID = -8248035922000823451L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactListCustomers", contactListCustomers).toString();
    }
}
