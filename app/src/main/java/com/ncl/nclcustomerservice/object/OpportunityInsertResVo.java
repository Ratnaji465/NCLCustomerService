package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class OpportunityInsertResVo implements Serializable
{

    @SerializedName("opportunity_id")
    @Expose
    public Integer opportunityId;
    private final static long serialVersionUID = -6892633553644247271L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunityId", opportunityId).toString();
    }
}
