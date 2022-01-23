package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 10/3/2018.
 */

public class ConvertCheckReqVo {
    @SerializedName("leads_id")
    @Expose
    public String leadsId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadsId", leadsId).toString();
    }
}
