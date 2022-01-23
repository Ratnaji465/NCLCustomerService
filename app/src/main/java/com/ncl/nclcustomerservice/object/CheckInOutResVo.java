package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/20/2018.
 */

public class CheckInOutResVo {
    @SerializedName("tracking_id")
    @Expose
    public String trackingId;
    @SerializedName("check_out_time")
    @Expose
    public String checkOutTime;
    @SerializedName("distance")
    @Expose
    public String distance;
    @SerializedName("polyline")
    @Expose
    public String polyline;
    @SerializedName("meter_reading_checkout_image")
    @Expose
    public String meterReadingCheckoutImage;
    @SerializedName("personal_uses_km")
    @Expose
    public String personalUsesKm;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("trackingId", trackingId).append("checkOutTime", checkOutTime).append("distance", distance).append("polyline", polyline).append("meterReadingCheckoutImage", meterReadingCheckoutImage).append("personalUsesKm", personalUsesKm).toString();
    }

}
