package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/4/2018.
 */

public class OpportunityDeleteReqVo  implements Serializable
{

    @SerializedName("opportunity_id")
    @Expose
    public int opportunityId;
    private final static long serialVersionUID = 9199378903190859778L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunityId", opportunityId).toString();
    }
}
