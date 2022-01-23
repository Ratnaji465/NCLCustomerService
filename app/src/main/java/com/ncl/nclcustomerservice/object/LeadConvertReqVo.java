package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/4/2018.
 */

public class LeadConvertReqVo implements Serializable {
    @SerializedName("leads_id")
    @Expose
    public String leadsId;
    @SerializedName("customer_check")
    @Expose
    public String customerCheck;
    @SerializedName("opportunity_check")
    @Expose
    public String opportunity;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadsId", leadsId).append("customerCheck", customerCheck).append("opportunity", opportunity).toString();
    }

}
