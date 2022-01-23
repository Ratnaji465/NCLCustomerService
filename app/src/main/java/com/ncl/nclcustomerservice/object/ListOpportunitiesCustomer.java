package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/6/2018.
 */

public class ListOpportunitiesCustomer implements Serializable
{
    @SerializedName("opportunity_id")
    @Expose
    public String opportunityId;
    @SerializedName("opp_id")
    @Expose
    public String oppId;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("TotalPrice")
    @Expose
    public String totalPrice;
    @SerializedName("CloseDate")
    @Expose
    public String closeDate;
    @SerializedName("Stage")
    @Expose
    public String stage;
    @SerializedName("OpportunityOwner")
    @Expose
    public String opportunityOwner;
    private final static long serialVersionUID = -4195837722835813764L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunityId", opportunityId).append("oppId", oppId).append("customer", customer).append("totalPrice", totalPrice).append("closeDate", closeDate).append("stage", stage).append("opportunityOwner", opportunityOwner).toString();
    }
}
