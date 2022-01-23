package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class LeadInsertResVo implements Serializable
{
    @SerializedName("leads_id")
    @Expose
    public Integer leadsId;
    private final static long serialVersionUID = 6583172404767525326L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadsId", leadsId).toString();
    }
}
