package com.ncl.nclcustomerservice.`object`

import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.google.gson.Gson
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import java.io.Serializable


data class ProductAndSubseries(
        @SerializedName("product_id")
        @Expose
        var subSeriesId: String = "",

        @SerializedName("customerproject_clientproject_products_id")
        @Expose
        var customerProjectClientProjectProductsId: String = "",

        @SerializedName("division_id")
        @Expose
        var divisionId: String = "",

        @SerializedName("product_sft")
        @Expose
        var productSft: String = "",

        @SerializedName("division_master_id")
        @Expose
        var divisionMasterId: String = "",

        @SerializedName("division_name")
        @Expose
        var divisionName: String = "",

        @SerializedName("division_sap_code")
        @Expose
        var divisionSapCode: String = "",

        @SerializedName("product_name")
        @Expose
        var productName: String = "",

        @SerializedName("product_code")
        @Expose
        var productCode: String = "",

        ) : Serializable
/*  "customerproject_clientproject_products_id": "114",
  "division_master_id": "3",
  "division_name": "Blocks",
  "division_sap_code": "30",
  "product_id": "6812",
  "product_name": "sentej",
  "product_code": "P90"*/
//request

data class InstalledSftDetails(
        @SerializedName("installation_period_from_date")
        @Expose
        var installationPeriodFromDate: String = "",

        @SerializedName("installation_period_to_date")
        @Expose
        var installationPeriodToDate: String = "",

        @SerializedName("installation_sft")
        @Expose
        var installationSft: String = "",
) : Serializable


//below one is request
data class ClientProject(
        @SerializedName("requestname") var requestName: String = "",
        @SerializedName("requesterid") var requesterId: String = "",
        @SerializedName("cs_customerproject_clientproject_detailsid") var csCustomerprojectClientProjectDetailsId: String = "",
        @SerializedName("customer_project_id") var customerProjectId: String = "",
        @SerializedName("oa_number") var oaNumber: String = "",
        @SerializedName("material_dispatch_date") var materialDispatchDate: String = "",
        @SerializedName("any_shortage") var anyShortage: String = "",
        @SerializedName("shortage_remarks") var shortageRemarks: String = "",
        @SerializedName("remark_date") var remarkDate: String = "",
        @SerializedName("shortage_meterial_received_on") var shortageMaterialReceived: String = "",
        @SerializedName("work_start_date") var workStartDate: String = "",
        @SerializedName("total_sft") var totalSft: String = "",
        @SerializedName("installed_sft") var installedSft: String = "",
        @SerializedName("balance_to_install") var balanceToInstall: String = "",
        @SerializedName("installation_completion_date") var installationCompletionDate: String = "",
        @SerializedName("no_of_days_for_installation") var noOfDaysForInstallation: String = "",
        @SerializedName("handing_over_done") var handingOverDone: String = "",
        @SerializedName("balance_handing_over") var balanceHandingOver: String = "",
        @SerializedName("work_completion_certificate_image_path") var workCompletionCertificateImagePath: String = "",
        @SerializedName("work_completion_certificate") var workCompletionCertificate: String = "",
        @SerializedName("work_completion_certificate_received_date") var workCompletionCertificateReceivedDate: String = "",
        @SerializedName("no_of_days_for_handing_over") var noOfDaysForHandingOver: String = "",
        @SerializedName("work_status") var workStatus: String = "",
        @SerializedName("no_of_days_for_installation_and_handing_over") var noOfDaysForInstallationAndHandingOver: String = "",
        @SerializedName("client_project_date") var clientProjectDate: String = "",
        @SerializedName("Remarks") var Remarks: String = "",
        @SerializedName("created_by") var createdBy: String = "",
        @SerializedName("modified_by") var modifiedBy: String = "",
        @SerializedName("created_datetime") var createdDatetime: String = "",
        @SerializedName("modified_datetime") var modifiedDatetime: String = "",
        @SerializedName("products")
        @Expose
        @TypeConverters(ProductAndSubseriesTC::class)
        public var products: List<ProductAndSubseries> = listOf(),
        @SerializedName("installed_sft_details")
        @Expose
        @TypeConverters(InstalledSftDetailsTC::class)
        public var installedSftDetails: List<InstalledSftDetails> = listOf(),
) : Serializable

//below on response
data class ClientProjectResponse(
        @SerializedName("client_projects") var clientProjects: List<ClientProject> = listOf(),
) : Serializable


public class ClientProjectTC {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<ClientProject> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<ClientProject?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<ClientProject?>?): String {
        return Gson().toJson(someObjects)
    }
}

public class InstalledSftDetailsTC {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<InstalledSftDetails> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<InstalledSftDetails?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<InstalledSftDetails?>?): String {
        return Gson().toJson(someObjects)
    }
}

class ProductAndSubseriesTC {
    @TypeConverter
    fun stringToSomeObjectList(data: String?): List<ProductAndSubseries> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<ProductAndSubseries?>?>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<ProductAndSubseries?>?): String {
        return Gson().toJson(someObjects)
    }
}
