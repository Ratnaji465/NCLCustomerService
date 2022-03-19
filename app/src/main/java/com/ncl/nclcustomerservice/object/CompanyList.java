package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

public class CompanyList {
    @SerializedName("company_or_client_name")
    @Expose
    public String companyOrClientName;
    @SerializedName("project_list")
    @Expose
    public List<ProjectList> projectList;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("companyOrClientName", companyOrClientName).append("projectList", projectList).toString();
    }

}
