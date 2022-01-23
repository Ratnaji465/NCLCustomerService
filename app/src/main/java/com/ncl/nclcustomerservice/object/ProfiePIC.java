package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 8/23/2018.
 */

public class ProfiePIC
{

    @SerializedName("profile_image")
    @Expose
    public String profileImage;
    private final static long serialVersionUID = -3952652580832821419L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("profileImage", profileImage).toString();
    }
}
