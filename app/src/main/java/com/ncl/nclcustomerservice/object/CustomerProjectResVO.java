package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncl.nclcustomerservice.typeconverter.CusAssociateContactTC;
import com.ncl.nclcustomerservice.typeconverter.CusContractorTC;
import com.ncl.nclcustomerservice.typeconverter.CusProjectHeadTC;
import com.ncl.nclcustomerservice.typeconverter.CusTeamMemberTC;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@Entity
public class CustomerProjectResVO implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("customer_project_id")
    @Expose
    public String customerProjectId;
    @SerializedName("project_name")
    @Expose
    public String projectName;
    @SerializedName("project_address")
    @Expose
    public String projectAddress;
    @SerializedName("state")
    @Expose
    public String state;
    @SerializedName("country")
    @Expose
    public String country;
    @SerializedName("pincode")
    @Expose
    public String pincode;
    @SerializedName("contractor_team_size")
    @Expose
    public String contractorTeamSize;
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
    @SerializedName("project_head")
    @Expose
    @TypeConverters(CusProjectHeadTC.class)
    public List<ProjectHead> projectHead = null;
    @SerializedName("contractors")
    @Expose
    @TypeConverters(CusContractorTC.class)
    public List<Contractor> contractors = null;

    @SerializedName("client_projects")
    @Expose
    @TypeConverters(ClientProjectTC.class)
    public List<ClientProject> clientProjects = null;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerProjectId",customerProjectId)
                .append("projectName", projectName)
                .append("projectAddress", projectAddress)
                .append("state", state)
                .append("country", country)
                .append("pincode", pincode)
                .append("contractorTeamSize", contractorTeamSize)
                .append("createdBy", createdBy)
                .append("modifiedBy", modifiedBy)
                .append("createdDatetime", createdDatetime)
                .append("modifiedDatetime", modifiedDatetime)
                .append("contractors", contractors)
                .append("projectHeads",projectHead).toString();
    }
    public class ProjectHead implements Serializable{

        @SerializedName("customerproject_projectcontact_details_id")
        @Expose
        public String customerprojectProjectcontactDetailsId;
        @SerializedName("contact_project_head_id")
        @Expose
        public String contactProjectHeadId;
        @SerializedName("project_head_name")
        @Expose
        public String projectHeadName;
        @SerializedName("project_head_department")
        @Expose
        public String projectHeadDepartment;
        @SerializedName("project_head_mobile")
        @Expose
        public String projectHeadMobile;
        @SerializedName("project_head_email")
        @Expose
        public String projectHeadEmail;
        @SerializedName("associate_contacts")
        @Expose
        @TypeConverters(CusAssociateContactTC.class)
        public List<AssociateContact> associateContacts = null;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("customerprojectProjectcontactDetailsId",customerprojectProjectcontactDetailsId)
                    .append("contactProjectHeadId", contactProjectHeadId)
                    .append("projectHeadName", projectHeadName)
                    .append("projectHeadDepartment", projectHeadDepartment)
                    .append("projectHeadMobile", projectHeadMobile)
                    .append("projectHeadEmail", projectHeadEmail)
                    .append("associateContacts",associateContacts)
                    .toString();
        }
    }
    public class AssociateContact implements Serializable {

        @SerializedName("cs_customerproject_projectassociatecontact_details_id")
        @Expose
        public String csCustomerprojectProjectassociatecontactDetailsId;
        @SerializedName("contact_projecthead_associatecontact_id")
        @Expose
        public String contactProjectheadAssociatecontactId;
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
                    .append("csCustomerprojectProjectassociatecontactDetailsId", csCustomerprojectProjectassociatecontactDetailsId)
                    .append("contactProjectheadAssociatecontactId", contactProjectheadAssociatecontactId)
                    .append("contactProjectHeadAssociateContactName", contactProjectHeadAssociateContactName)
                    .append("contactProjectHeadAssociateContactDesignation", contactProjectHeadAssociateContactDesignation)
                    .append("contactProjectHeadAssociateContactMobile", contactProjectHeadAssociateContactMobile)
                    .toString();
        }
    }
    public class Contractor implements Serializable{

        @SerializedName("cs_customerproject_contractor_details_id")
        @Expose
        public String csCustomerprojectContractorDetailsId;
        @SerializedName("contractor_name")
        @Expose
        public String contractorName;
        @SerializedName("contractor_firm_name")
        @Expose
        public String contractorFirmName;
        @SerializedName("contractor_mobile_no")
        @Expose
        public String contractorMobileNo;
        @SerializedName("contractor_aadhar_number")
        @Expose
        public String contractorAadharNumber;
        @SerializedName("contractor_pan_number")
        @Expose
        public String contractorPanNumber;
        @SerializedName("team_members")
        @Expose
        @TypeConverters(CusTeamMemberTC.class)
        public List<TeamMember> teamMembers = null;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("csCustomerprojectContractorDetailsId", csCustomerprojectContractorDetailsId)
                    .append("contractorName", contractorName)
                    .append("contractorFirmName", contractorFirmName)
                    .append("contractorMobileNo", contractorMobileNo)
                    .append("contractorAadharNumber", contractorAadharNumber)
                    .append("contractorPanNumber", contractorPanNumber)
                    .append("teamMembers", teamMembers)
                    .toString();
        }
    }
    public class TeamMember implements Serializable{

        @SerializedName("cs_customerproject_contractor_teamdetails_id")
        @Expose
        public String csCustomerprojectContractorTeamdetailsId;
        @SerializedName("team_member_name")
        @Expose
        public String teamMemberName;
        @SerializedName("team_member_mobile_no")
        @Expose
        public String teamMemberMobileNo;
        @SerializedName("teammember_aadhar_number")
        @Expose
        public String teammemberAadharNumber;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("csCustomerprojectContractorTeamdetailsId", csCustomerprojectContractorTeamdetailsId)
                    .append("teamMemberName", teamMemberName)
                    .append("teamMemberMobileNo", teamMemberMobileNo)
                    .append("teammemberAadharNumber", teammemberAadharNumber)
                    .toString();
        }
    }

}
