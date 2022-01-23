package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/9/2019.
 */

public class DistributionChannelList implements Serializable
{

    @SerializedName("DistributionChannel_id")
    @Expose
    public String distributionChannelId;
    @SerializedName("ditribution_name")
    @Expose
    public String ditributionName;
    private final static long serialVersionUID = 2002668198394224272L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("distributionChannelId", distributionChannelId).append("ditributionName", ditributionName).toString();
    }
}
