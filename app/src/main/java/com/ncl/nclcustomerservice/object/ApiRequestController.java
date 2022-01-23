package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by Suprosoft on 15-12-2017.
 */

public class ApiRequestController implements Serializable {
    @SerializedName("requesterid")
    @Expose
    public int requesterid;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("requestparameters")
    @Expose
    public Object requestparameters;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requesterid", requesterid).append("requestname", requestname).append("requestparameters", requestparameters).toString();
    }
}
