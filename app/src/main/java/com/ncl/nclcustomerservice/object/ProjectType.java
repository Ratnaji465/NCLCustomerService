package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class ProjectType implements Serializable
{

    @SerializedName("project_type_id")
    @Expose
    public String projectTypeId;
    @SerializedName("project_type_name")
    @Expose
    public String projectTypeName;
    private final static long serialVersionUID = 2963183053187410510L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("projectTypeId", projectTypeId).append("projectTypeName", projectTypeName).toString();
    }

}
