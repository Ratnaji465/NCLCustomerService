package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DepartmentList {
    @SerializedName("department_id")
    @Expose
    public String departmentId;
    @SerializedName("department_name")
    @Expose
    public String departmentName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("departmentId", departmentId).append("departmentName", departmentName).toString();
    }
}
