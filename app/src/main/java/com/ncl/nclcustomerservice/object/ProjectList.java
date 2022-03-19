package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ProjectList {
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_project_head_id")
    @Expose
    public String contactProjectHeadId;
    @SerializedName("client_code")
    @Expose
    public String clientCode;
    @SerializedName("project_head_name")
    @Expose
    public String projectHeadName;
    @SerializedName("project_name")
    @Expose
    public String projectName;
    @SerializedName("project_head_mobile")
    @Expose
    public String projectHeadMobile;
    @SerializedName("project_head_email")
    @Expose
    public String projectHeadEmail;
    @SerializedName("project_head_address")
    @Expose
    public String projectHeadAddress;
    @SerializedName("project_head_state")
    @Expose
    public String projectHeadState;
    @SerializedName("project_head_country")
    @Expose
    public String projectHeadCountry;
    @SerializedName("project_head_pincode")
    @Expose
    public String projectHeadPincode;
    @SerializedName("project_head_department")
    @Expose
    public String projectHeadDepartment;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("contactId", contactId)
                .append("contactProjectHeadId", contactProjectHeadId)
                .append("clientCode", clientCode)
                .append("projectHeadName", projectHeadName)
                .append("projectName", projectName).toString();
    }
}
