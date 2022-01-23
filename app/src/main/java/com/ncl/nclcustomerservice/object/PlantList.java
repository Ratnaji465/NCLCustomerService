package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/13/2018.
 */

public class PlantList {

    @SerializedName("plantid")
    @Expose
    public String plantid;
    @SerializedName("plantName")
    @Expose
    public String plantName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("plantid", plantid).append("plantName", plantName).toString();
    }
}
