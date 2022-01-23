package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncl.nclcustomerservice.typeconverter.ContractorTeamMemberTC;
import com.ncl.nclcustomerservice.typeconverter.LeadActionTOTC;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;


public class CustomerContactResponseVo implements Serializable
{
    @SerializedName("contact_list")
    @Expose
    public List<ContactContractorList> contactList;

    @Entity
    public static class ContactContractorList implements Serializable
    {
        @PrimaryKey
        @NonNull
        @SerializedName("contact_contractor_id")
        @Expose
        public String contactContractorId;
        @SerializedName("contact_id")
        @Expose
        public int contactId;
        @SerializedName("contractor_name")
        @Expose
        public String contractorName;
        @SerializedName("contact_number")
        @Expose
        public String contactNumber;
        @SerializedName("category")
        @Expose
        public String category;
        @SerializedName("contractor_firm_name")
        @Expose
        public String contractorFirmName;
        @SerializedName("contractor_mobile_no")
        @Expose
        public String contractorMobileNo;
        @SerializedName("contractor_aadhar_number")
        @Expose
        public String contractorAadharNumber;
        @SerializedName("contractor_aadhar_image_path")
        @Expose
        public String contractorAadharImagePath;
        @SerializedName("contractor_pan_number")
        @Expose
        public String contractorPanNumber;
        @SerializedName("contractor_pan_image_path")
        @Expose
        public String contractorPanImagePath;
        @SerializedName("contractor_gst_number")
        @Expose
        public String contractorGstNumber;
        @SerializedName("contractor_team_size")
        @Expose
        public String contractorTeamSize;
        @SerializedName("contractor_address")
        @Expose
        public String contractorAddress;
        @SerializedName("contractor_state")
        @Expose
        public String contractorState;
        @SerializedName("contractor_city")
        @Expose
        public String contractorCity;
        @SerializedName("contractor_country")
        @Expose
        public String contractorCountry;
        @SerializedName("contractor_pincode")
        @Expose
        public String contractorPincode;
        @SerializedName("contact_contractor_remarks")
        @Expose
        public String contactContractorRemarks;
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
        @SerializedName("team_members")
        @Expose
        @TypeConverters(ContractorTeamMemberTC.class)
        public List<TeamMemberResVo> teamMembers = null;
        @NonNull
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactContractorId", contactContractorId)
                    .append("contactId", contactId)
                    .append("contractorName", contractorName)
                    .append("contactNumber",contactNumber)
                    .append("category",category)
                    .append("contractorFirmName", contractorFirmName)
                    .append("contractorMobileNo", contractorMobileNo)
                    .append("contractorAadharNumber", contractorAadharNumber)
                    .append("contractorAadharImagePath", contractorAadharImagePath)
                    .append("contractorPanNumber", contractorPanNumber)
                    .append("contractorPanImagePath", contractorPanImagePath)
                    .append("contractorGstNumber", contractorGstNumber)
                    .append("contractorTeamSize", contractorTeamSize)
                    .append("contractorAddress", contractorAddress)
                    .append("contractorState", contractorState)
                    .append("contractorCity", contractorCity)
                    .append("contractorCountry", contractorCountry)
                    .append("contractorPincode", contractorPincode)
                    .append("contactContractorRemarks", contactContractorRemarks)
                    .append("createdBy",createdBy)
                    .append("modifiedBy",modifiedBy)
                    .append("createdDatetime",createdDatetime)
                    .append("modifiedDatetime",modifiedDatetime)
                    .append("teamMembers", teamMembers)
                    .toString();
        }
    }

    public static class TeamMemberResVo implements Serializable
    {
        @SerializedName("contact_contractor_team_id")
        @Expose
        public String contactContractorTeamId;
        @SerializedName("contact_id")
        @Expose
        public String contactId;
        @SerializedName("team_member_name")
        @Expose
        public String teamMemberName;
        @SerializedName("team_member_mobile_no")
        @Expose
        public String teamMemberMobileNo;
        @SerializedName("teammember_aadhar_number")
        @Expose
        public String teammemberAadharNumber;
        @SerializedName("team_member_aadhar_image_path")
        @Expose
        public String teamMemberAadharImagePath;
        @NonNull
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactContractorTeamId", contactContractorTeamId)
                    .append("contactId",contactId)
                    .append("teamMemberName",teamMemberName)
                    .append("teamMemberMobileNo",teamMemberMobileNo)
                    .append("teammemberAadharNumber",teammemberAadharNumber)
                    .append("teamMemberAadharImagePath",teamMemberAadharImagePath).toString();
        }
    }
}
