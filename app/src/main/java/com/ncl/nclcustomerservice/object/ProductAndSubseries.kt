package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductAndSubseries(
    @SerializedName(
        "division_id",
        alternate = ["division_master_id"]
    ) @Expose
    var productId: String,

    @SerializedName("product_id")
    @Expose
    var subSeriesId: String = "",

    @SerializedName("customerproject_clientproject_products_id")
    @Expose
    var customerProjectClientProjectProductsId: String = "",

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

    ):Serializable

/*  "customerproject_clientproject_products_id": "114",
  "division_master_id": "3",
  "division_name": "Blocks",
  "division_sap_code": "30",
  "product_id": "6812",
  "product_name": "sentej",
  "product_code": "P90"*/
//request

//below one is request
data class ClientProject(
    @SerializedName("requestname") var requestName: String = "",
    @SerializedName("requesterid") var requesterId: String ="",
    @SerializedName("cs_customerproject_clientproject_detailsid") var csCustomerprojectClientProjectDetailsId: String="",
    @SerializedName("customer_project_id") var customerProjectId: String ="",
    @SerializedName("oa_number") var oaNumber: String ="",
    @SerializedName("material_dispatch_date") var materialDispatchDate: String ="",
    @SerializedName("any_shortage") var anyShortage: String ="",
    @SerializedName("work_start_date") var workStartDate: String ="",
    @SerializedName("total_sft") var totalSft: String ="",
    @SerializedName("installed_sft") var installedSft: String ="",
    @SerializedName("balance_to_install") var balanceToInstall: String ="",
    @SerializedName("installation_completion_date") var installationCompletionDate: String ="",
    @SerializedName("no_of_days_for_installation") var noOfDaysForInstallation: String ="",
    @SerializedName("handing_over_done") var handingOverDone: String ="",
    @SerializedName("balance_handing_over") var balanceHandingOver: String ="",
    @SerializedName("work_completion_certificate_image_path") var workCompletionCertificateImagePath: String ="",
    @SerializedName("work_completion_certificate") var workCompletionCertificate: String ="",
    @SerializedName("work_completion_certificate_received_date") var workCompletionCertificateReceivedDate: String ="",
    @SerializedName("no_of_days_for_handing_over") var noOfDaysForHandingOver: String ="",
    @SerializedName("work_status") var workStatus: String ="",
    @SerializedName("no_of_days_for_installation_and_handing_over") var noOfDaysForInstallationAndHandingOver: String ="",
    @SerializedName("client_project_date") var clientProjectDate: String ="",
    @SerializedName("Remarks") var Remarks: String ="",
    @SerializedName("created_by") var createdBy: String ="",
    @SerializedName("modified_by") var modifiedBy: String ="",
    @SerializedName("created_datetime") var createdDatetime: String ="",
    @SerializedName("modified_datetime") var modifiedDatetime: String ="",

    @SerializedName("products") var products: List<ProductAndSubseries> = listOf(),
):Serializable

//below on response
data class ClientProjectResponse(
    @SerializedName("client_projects") var clientProjects: List<ClientProject> = listOf(),
)