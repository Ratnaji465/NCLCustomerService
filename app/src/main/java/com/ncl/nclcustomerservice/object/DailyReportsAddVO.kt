package com.ncl.nclcustomerservice.`object`

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.ncl.nclcustomerservice.typeconverter.CustomerprojectClientprojectDetailsTC
import com.ncl.nclcustomerservice.typeconverter.DescriptionOfWorkTC
import java.io.Serializable

@Entity
class DailyReportsAddVO : Serializable {
    @PrimaryKey
    @NonNull
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
    var projectName: String? = null

    @SerializedName("project_head_name")
    @Expose
    var projectHeadName: String? = null

    @SerializedName("call_type")
    @Expose
    var callType: String? = null

    @SerializedName("newproject_name")
    @Expose
    var newprojectName : String?=null

    @SerializedName("newproject_contact_name")
    @Expose
    var newprojectContactName : String?=null

    @SerializedName("mobile_no")
    @Expose
    var mobileNo : String?=null

    @SerializedName("call_date")
    @Expose
    var callDate: String? = null

    @SerializedName("call_time")
    @Expose
    var callTime: String? = null

    @SerializedName("check_in_time")
    @Expose
    var checkInTime: String? = null

    @SerializedName("checkout_time")
    @Expose
    var checkoutTime: String? = null

    @SerializedName("call_status")
    @Expose
    var callStatus :Int ?=0

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
    @TypeConverters(CustomerprojectClientprojectDetailsTC::class)
    var csCustomerprojectClientprojectDetails: List<CustomerprojectClientprojectDetails>? = null

    @SerializedName("description_of_works")
    @Expose
    @TypeConverters(DescriptionOfWorkTC::class)
    var descriptionOfWorks: List<DescriptionOfWork>? = null

    class DescriptionOfWork: Serializable {
        @SerializedName("cs_dailyreport_description_of_works_id")
        @Expose
        var csDailyreportDescriptionOfWorksId: String? = null

        @SerializedName("cs_dailyreport_id")
        @Expose
        var csDailyreportId: String? = null

        @SerializedName("description_of_works")
        @Expose
        var descriptionOfWorks: String? = ""
    }

    class CustomerprojectClientprojectDetails(
        @SerializedName("cs_customerproject_clientproject_detailsid")
        @Expose
        var csCustomerprojectClientprojectDetailsid: String? = null
    ) : Serializable
}