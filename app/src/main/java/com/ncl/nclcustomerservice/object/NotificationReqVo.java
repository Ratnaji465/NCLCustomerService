package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/11/2019.
 */

public class NotificationReqVo implements Serializable
{

    @SerializedName("profile_id")
    @Expose
    public String profileId;
    private final static long serialVersionUID = -8554280205777718621L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("profileId", profileId).toString();
    }
}
