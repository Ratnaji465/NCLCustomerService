package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 10/4/2018.
 */

public class SalesCallDelete {

    @SerializedName("sales_call_id")
    @Expose
    public int salesCallId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallId", salesCallId).toString();
    }
}
