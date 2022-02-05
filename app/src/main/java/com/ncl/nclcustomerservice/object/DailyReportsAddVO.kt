package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import java.io.Serializable

class DailyReportsAddVO : Serializable {
    @SerializedName("cs_dailyreport_id")
    @Expose
    var csDailyreportId: String? = null

    @SerializedName("related_to")
    @Expose
    var relatedTo: String? = null

    @SerializedName("contact_contractor_id")
    @Expose
    var contactContractorId: String? = null

    @SerializedName("contractor_name")
    @Expose
    var contractorName: String? = null

    @SerializedName("contractor_mobile_no")
    @Expose
    var contractorMobileNo: String? = null

    @SerializedName("customer_project_id")
    @Expose
    var customerProjectId: String? = null

    @SerializedName("project_name")
    @Expose
    var projectName: Any? = null

    @SerializedName("project_head_name")
    @Expose
    var projectHeadName: Any? = null

    @SerializedName("call_type")
    @Expose
    var callType: String? = null

    @SerializedName("call_date")
    @Expose
    var callDate: String? = null

    @SerializedName("call_time")
    @Expose
    var callTime: String? = null

    @SerializedName("check_in_time")
    @Expose
    var checkInTime: Any? = null

    @SerializedName("checkout_time")
    @Expose
    var checkoutTime: Any? = null

    @SerializedName("cs_customerproject_clientproject_detailsid")
    @Expose
    var csCustomerprojectClientprojectDetailsid: String? = null

    @SerializedName("oa_numbers")
    @Expose
    var oaNumbers: String? = null

    @SerializedName("user_id")
    @Expose
    var userId: String? = null

    @SerializedName("userName")
    @Expose
    var userName: String? = null

    @SerializedName("cs_customerproject_clientproject_details")
    @Expose
    var csCustomerprojectClientprojectDetails: List<CustomerprojectClientprojectDetails>? = null

    @SerializedName("description_of_works")
    @Expose
    var descriptionOfWorks: List<DescriptionOfWork>? = null

    class DescriptionOfWork {
        @SerializedName("cs_dailyreport_description_of_works_id")
        @Expose
        var csDailyreportDescriptionOfWorksId: String? = null

        @SerializedName("cs_dailyreport_id")
        @Expose
        var csDailyreportId: String? = null

        @SerializedName("description_of_works")
        @Expose
        var descriptionOfWorks: String? = null
    }

    class CustomerprojectClientprojectDetails(
        @SerializedName("cs_customerproject_clientproject_detailsid")
        @Expose
        var csCustomerprojectClientprojectDetailsid: String? = null
    ) : Serializable
}