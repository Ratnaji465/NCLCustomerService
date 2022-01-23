package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/6/2018.
 */

public class ListOpportunityResVo implements Serializable
{
    @SerializedName("opportunities_Customers")
    @Expose
    public List<ListOpportunitiesCustomer> opportunitiesCustomers = null;
    private final static long serialVersionUID = -3843388601726706976L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunitiesCustomers", opportunitiesCustomers).toString();
    }
}
