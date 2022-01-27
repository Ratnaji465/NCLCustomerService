package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class CustomerProjectReqVO implements Serializable {

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
    @SerializedName("contractors")
    @Expose
    public List<Contractor> contractors = null;
    @SerializedName("project_heads")
    @Expose
    public List<ProjectHead> projectHeads = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
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
                .append("projectHeads",projectHeads).toString();
    }
    public class ProjectHead implements Serializable{

        @SerializedName("contact_project_head_id")
        @Expose
        public Integer contactProjectHeadId;
        @SerializedName("associate_contacts")
        @Expose
        public List<AssociateContact> associateContacts = null;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactProjectHeadId", contactProjectHeadId)
                    .append("associateContacts",associateContacts)
                    .toString();
        }
    }
    public class AssociateContact implements Serializable{

        @SerializedName("contact_projecthead_associatecontact_id")
        @Expose
        public String contactProjectheadAssociatecontactId;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactProjectheadAssociatecontactId", contactProjectheadAssociatecontactId).toString();
        }
    }
    public class Contractor implements Serializable{

        @SerializedName("contact_contractor_id")
        @Expose
        public Integer contactContractorId;
        @SerializedName("team")
        @Expose
        public List<Team> team = null;
        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactContractorId", contactContractorId)
                    .append("team",team).toString();
        }
    }
    public class Team implements Serializable{

        @SerializedName("contact_contractor_team_id")
        @Expose
        public Integer contactContractorTeamId;

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("contactContractorTeamId", contactContractorTeamId).toString();
        }
    }
}
