package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 10/3/2018.
 */

public class LeadDelete {

    @SerializedName("leads_id")
    @Expose
    public int leadsId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadsId", leadsId).toString();
    }
}
