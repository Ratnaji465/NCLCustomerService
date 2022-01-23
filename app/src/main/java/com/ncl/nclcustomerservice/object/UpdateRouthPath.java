package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suprasoft on 10/30/2018.
 */

public class UpdateRouthPath {
    @SerializedName("employee_activity_id")
    @Expose
    public int employeeActivityId;
    @SerializedName("route_path")
    @Expose
    public String routePath;
}
