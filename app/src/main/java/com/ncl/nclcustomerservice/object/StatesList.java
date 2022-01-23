package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 10/1/2018.
 */

public class StatesList {
    @SerializedName("state_id")
    @Expose
    public String stateId;
    @SerializedName("state_name")
    @Expose
    public String stateName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("stateId", stateId).append("stateName", stateName).toString();
    }

}
