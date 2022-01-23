package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/13/2018.
 */

public class FreightList {
    @SerializedName("freight_id")
    @Expose
    public String freightId;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("location")
    @Expose
    public String location;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("freightId", freightId).append("price",price).append("location", location).toString();
    }
}
