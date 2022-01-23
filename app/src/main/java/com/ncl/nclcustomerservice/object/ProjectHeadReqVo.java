package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncl.nclcustomerservice.typeconverter.ProjectHeadAssociateContactTC;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
public class ProjectHeadReqVo implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("contact_project_head_id")
    @Expose
    public String contactProjectHeadId;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("project_head_name")
    @Expose
    public String projectHeadName;
    @SerializedName("company_or_client_name")
    @Expose
    public String companyOrClientName;
    @SerializedName("project_head_mobile")
    @Expose
    public String projectHeadMobile;
    @SerializedName("project_head_department")
    @Expose
    public String projectHeadDepartment;
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
    @SerializedName("project_head_contact_remarks")
    @Expose
    public String projectHeadContactRemarks;
    @SerializedName("project_head_email")
    @Expose
    public String projectHeadEmail;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_number")
    @Expose
    public String contactNumber;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("modified_by")
    @Expose
    public String modifiedBy;
    @SerializedName("created_datetime")
    @Expose
    public String createdDatetime;
    @SerializedName("modified_datetime")
    @Expose
    public String modifiedDatetime;
    @SerializedName("associate_contacts")
    @Expose
    @TypeConverters(ProjectHeadAssociateContactTC.class)
    public List<AssociateContact> associateContacts = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("contactProjectHeadId",contactProjectHeadId)
                .append("category", category)
                .append("projectHeadName", projectHeadName)
                .append("companyOrClientName", companyOrClientName)
                .append("projectHeadMobile", projectHeadMobile)
                .append("projectHeadDepartment", projectHeadDepartment)
                .append("projectHeadAddress", projectHeadAddress)
                .append("projectHeadState", projectHeadState)
                .append("projectHeadCountry", projectHeadCountry)
                .append("projectHeadPincode",projectHeadPincode)
                .append("projectHeadContactRemarks",projectHeadContactRemarks)
                .append("projectHeadEmail",projectHeadEmail)
                .append("contactId",contactId)
                .append("createdBy",createdBy)
                .append("modifiedBy",modifiedBy)
                .append("createdDatetime",createdDatetime)
                .append("modifiedDatetime",modifiedDatetime)
                .append("associateContacts",associateContacts)
                .toString();
    }

    public static class AssociateContact implements Serializable {
        @SerializedName("contact_projecthead_associatecontact_id")
        @Expose
        public String contactProjectheadAssociatecontactId;
        @SerializedName("contact_id")
        @Expose
        public String contactId;
        @SerializedName("contact_project_head_associate_contact_name")
        @Expose
        public String contactProjectHeadAssociateContactName;
        @SerializedName("contact_project_head_associate_contact_designation")
        @Expose
        public String contactProjectHeadAssociateContactDesignation;
        @SerializedName("contact_project_head_associate_contact_mobile")
        @Expose
        public String contactProjectHeadAssociateContactMobile;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactProjectheadAssociatecontactId",contactProjectheadAssociatecontactId)
                    .append("contactId",contactId)
                    .append("contactProjectHeadAssociateContactName", contactProjectHeadAssociateContactName)
                    .append("contactProjectHeadAssociateContactDesignation", contactProjectHeadAssociateContactDesignation)
                    .append("contactProjectHeadAssociateContactMobile", contactProjectHeadAssociateContactMobile)
                    .toString();
        }
    }
}
