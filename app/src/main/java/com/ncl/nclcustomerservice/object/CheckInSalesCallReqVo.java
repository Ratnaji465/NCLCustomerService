package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suprasoft on 10/1/2018.
 */

public class CheckInSalesCallReqVo {

    @SerializedName("sales_call_id")
    @Expose
    public String sales_call_id;
    @SerializedName("tracking_id")
    @Expose
    public String tracking_id;
    @SerializedName("lat_lon_val")
    @Expose
    public String lat_lon_val;

    @Override
    public String toString() {
        return "CheckInSalesCallReqVo{" +
                "sales_call_id='" + sales_call_id + '\'' +
                ", tracking_id='" + tracking_id + '\'' +
                ", lat_lon_val='" + lat_lon_val + '\'' +
                '}';
    }
}
