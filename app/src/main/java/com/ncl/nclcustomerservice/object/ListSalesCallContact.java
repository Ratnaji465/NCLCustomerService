package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/5/2018.
 */

public class ListSalesCallContact implements Serializable {

    @SerializedName("sales_call_id")
    @Expose
    public String salesCallId;
    @SerializedName("Subject")
    @Expose
    public String subject;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Call_Type")
    @Expose
    public String callType;
    @SerializedName("Assigned_To")
    @Expose
    public String assignedTo;
    @SerializedName("Priority")
    @Expose
    public String priority;
    @SerializedName("Owner")
    @Expose
    public String owner;
    @SerializedName("type")
    @Expose
    public String type;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallId", salesCallId).append("subject", subject).append("name", name).append("status", status).append("callType", callType).append("assignedTo", assignedTo).append("priority", priority).append("owner", owner).append("type", type).toString();
    }
}
