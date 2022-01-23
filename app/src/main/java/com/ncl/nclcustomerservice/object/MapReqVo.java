package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/24/2018.
 */

public class MapReqVo {
    @SerializedName("team_id")
    @Expose
    public String teamId;
    @SerializedName("days")
    @Expose
    public String days;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("teamId", teamId).append("days", days).toString();
    }

}
