package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 2/12/2019.
 */

public class DashboardSalesCall implements Serializable {
    @SerializedName("status_open")
    @Expose
    public float statusOpen;
    @SerializedName("status_missed")
    @Expose
    public float statusMissed;
    @SerializedName("status_completed")
    @Expose
    public float statusCompleted;
    private final static long serialVersionUID = -289661048446987433L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusOpen", statusOpen).append("statusMissed", statusMissed).append("statusCompleted", statusCompleted).toString();
    }
}
