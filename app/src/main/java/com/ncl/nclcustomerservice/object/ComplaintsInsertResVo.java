package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class ComplaintsInsertResVo implements Serializable
{

    @SerializedName("Complaint_id")
    @Expose
    public Integer complaintId;
    private final static long serialVersionUID = 3586931996856272572L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("complaintId", complaintId).toString();
    }
}
