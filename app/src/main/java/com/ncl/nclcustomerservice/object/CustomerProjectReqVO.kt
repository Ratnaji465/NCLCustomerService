package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import org.apache.commons.lang3.builder.ToStringBuilder
import java.io.Serializable

class CustomerProjectReqVO : Serializable {
    @SerializedName("customer_project_id")
    @Expose
    var customerProjectId:String?=null
    @SerializedName("project_name")
    @Expose
    var projectName: String? = null

    @SerializedName("project_address")
    @Expose
    var projectAddress: String? = null

    @SerializedName("state")
    @Expose
    var state: String? = null

    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("pincode")
    @Expose
    var pincode: String? = null

    @SerializedName("contractor_team_size")
    @Expose
    var contractorTeamSize: String? = null

    @SerializedName("created_by")
    @Expose
    var createdBy: String? = null

    @SerializedName("modified_by")
    @Expose
    var modifiedBy: String? = null

    @SerializedName("created_datetime")
    @Expose
    var createdDatetime: String? = null

    @SerializedName("modified_datetime")
    @Expose
    var modifiedDatetime: String? = null

    @SerializedName("contractors")
    @Expose
    var contractors: List<Contractor>? = null

    @SerializedName("project_heads")
    @Expose
    var projectHeads: List<ProjectHead>? = null
    override fun toString(): String {
        return ToStringBuilder(this)
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
            .append("projectHeads", projectHeads).toString()
    }

    class ProjectHead : Serializable {
        @SerializedName("contact_project_head_id")
        @Expose
        var contactProjectHeadId: String? = null

        @SerializedName("associate_contacts")
        @Expose
        var associateContacts: List<AssociateContact>? = null
        override fun toString(): String {
            return ToStringBuilder(this)
                .append("contactProjectHeadId", contactProjectHeadId)
                .append("associateContacts", associateContacts)
                .toString()
        }
    }

    class AssociateContact : Serializable {
        @SerializedName("contact_projecthead_associatecontact_id")
        @Expose
        var contactProjectheadAssociatecontactId: String? = null
        override fun toString(): String {
            return ToStringBuilder(this)
                .append(
                    "contactProjectheadAssociatecontactId",
                    contactProjectheadAssociatecontactId
                ).toString()
        }
    }

    class Contractor : Serializable {
        @SerializedName("contact_contractor_id")
        @Expose
        var contactContractorId: String? = null

        @SerializedName("team")
        @Expose
        var team: List<Team>? = null
        override fun toString(): String {
            return ToStringBuilder(this)
                .append("contactContractorId", contactContractorId)
                .append("team", team).toString()
        }
    }

    class Team : Serializable {
        @SerializedName("contact_contractor_team_id")
        @Expose
        var contactContractorTeamId: String? = null
        override fun toString(): String {
            return ToStringBuilder(this)
                .append("contactContractorTeamId", contactContractorTeamId).toString()
        }
    }
}