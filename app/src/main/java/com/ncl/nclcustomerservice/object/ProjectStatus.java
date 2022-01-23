package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class ProjectStatus implements Serializable
{

    @SerializedName("status_project_id")
    @Expose
    public String statusProjectId;
    @SerializedName("status_project_name")
    @Expose
    public String statusProjectName;
    private final static long serialVersionUID = -206104729055298033L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("statusProjectId", statusProjectId).append("statusProjectName", statusProjectName).toString();
    }
}
