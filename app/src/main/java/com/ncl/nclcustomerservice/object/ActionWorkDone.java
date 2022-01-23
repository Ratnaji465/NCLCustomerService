package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class ActionWorkDone implements Serializable {
    @SerializedName("action_work_done_id")
    @Expose
    public String actionWorkDoneId;
    @SerializedName("action_work_done_date")
    @Expose
    public String actionWorkDoneDate;
    @SerializedName("action_work_done_remarks")
    @Expose
    public String actionWorkDoneRemarks;
}
