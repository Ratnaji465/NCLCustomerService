package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/24/2018.
 */

public class PolylineReqVo {
    @SerializedName("tracking_id")
    @Expose
    public String trackingId;
    @SerializedName("latlon")
    @Expose
    public String latlon;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("trackingId", trackingId).append("latlon", latlon).toString();
    }

}
