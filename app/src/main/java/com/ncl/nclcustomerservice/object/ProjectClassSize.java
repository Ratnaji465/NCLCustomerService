package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class ProjectClassSize implements Serializable
{

    @SerializedName("size_class_id")
    @Expose
    public String sizeClassId;
    @SerializedName("size_class_name")
    @Expose
    public String sizeClassName;
    @SerializedName("type")
    @Expose
    public String type;
    private final static long serialVersionUID = 7109832529620090154L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("sizeClassId", sizeClassId).append("sizeClassName", sizeClassName).toString();
    }

}
