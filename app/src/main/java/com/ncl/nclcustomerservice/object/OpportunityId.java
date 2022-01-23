package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by sowmy on 10/16/2018.
 */

public class OpportunityId {

    @SerializedName("opportunity_id")
    @Expose
    public int opportunityId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunityId", opportunityId).toString();
    }
}
