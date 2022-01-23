package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 10/1/2018.
 */

public class LeadsResVo {
    @SerializedName("lead_list")
    @Expose
    public List<LeadInsertReqVo> leadList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadList", leadList).toString();
    }
}
