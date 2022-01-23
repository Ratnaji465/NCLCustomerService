package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class CustomerContractorInsertReqVo implements Serializable {
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("requesterid")
    @Expose
    public String requesterid;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_contractor_id")
    @Expose
    public String contactContractorId;
    @SerializedName("category")
    @Expose
    public String category;
    @SerializedName("contractor_name")
    @Expose
    public String contractorName;
    @SerializedName("contractor_mobile_no")
    @Expose
    public String contractorMobileNo;
    @SerializedName("contractor_firm_name")
    @Expose
    public String contractorFirmName;
    @SerializedName("contractor_aadhar_number")
    @Expose
    public String contractorAadharNumber;
    @SerializedName("contractor_pan_number")
    @Expose
    public String contractorPanNumber;
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
    @SerializedName("team_member")
    @Expose
    public List<TeamMember> teamMember = null;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("requestname", requestname)
                .append("requesterid",requesterid)
                .append("contactId",contactId)
                .append("contactContractorId",contactContractorId)
                .append("category",category)
                .append("contractorName",contractorName)
                .append("contractorMobileNo",contractorMobileNo)
                .append("contractorFirmName",contractorFirmName)
                .append("contractorAadharNumber",contractorAadharNumber)
                .append("contractorPanNumber",contractorPanNumber)
                .append("contractorGstNumber",contractorGstNumber)
                .append("contractorTeamSize",contractorTeamSize)
                .append("contractorAddress",contractorAddress)
                .append("contractorState",contractorState)
                .append("contractorCity",contractorCity)
                .append("contractorCountry",contractorCountry)
                .append("contractorPincode",contractorPincode)
                .append("contactContractorRemarks",contactContractorRemarks)
                .append("teamMember",teamMember)
                .toString();
    }

    public static class TeamMember implements Serializable {
        @SerializedName("contact_contractor_team_id")
        @Expose
        public String contactContractorTeamId;
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
                    .append("teamMemberName", teamMemberName)
                    .append("teamMemberMobileNo",teamMemberMobileNo)
                    .append("teammemberAadharNumber",teammemberAadharNumber)
                    .toString();
        }
    }
}

