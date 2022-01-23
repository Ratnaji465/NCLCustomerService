package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/9/2018.
 */

public class ComplaintDeleteReqVo implements Serializable
{
    @SerializedName("complaint_id")
    @Expose
    public int complaintId;
    private final static long serialVersionUID = 3397651365029095943L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("complaintId", complaintId).toString();
    }
}
