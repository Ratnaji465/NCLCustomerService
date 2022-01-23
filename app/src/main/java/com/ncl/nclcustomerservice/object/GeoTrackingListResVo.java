package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 12/24/2018.
 */

public class GeoTrackingListResVo {
    @SerializedName("geo_tracking_list")
    @Expose
    public List<Geo_Tracking_POJO> geoTrackingList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("geoTrackingList", geoTrackingList).toString();
    }
}
