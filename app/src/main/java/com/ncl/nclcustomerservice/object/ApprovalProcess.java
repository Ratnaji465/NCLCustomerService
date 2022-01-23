package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 12/4/2018.
 */

public class ApprovalProcess implements Serializable {
    @SerializedName("Step")
    @Expose
    public String step;
    @SerializedName("Action")
    @Expose
    public String action;
    @SerializedName("date_time")
    @Expose
    public String dateTime;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Assigned_to")
    @Expose
    public String assignedTo;
    @SerializedName("Assigned_to_name")
    @Expose
    public String assignedToName;
    @SerializedName("Comments")
    @Expose
    public String comments;
    public boolean isSubmitted=false;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("step", step).append("action", action).append("dateTime", dateTime).append("status", status).append("assignedTo", assignedTo).append("assignedToName", assignedToName).append("comments", comments).toString();
    }
}