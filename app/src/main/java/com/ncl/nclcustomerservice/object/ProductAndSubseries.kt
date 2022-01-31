package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class ProductAndSubseries(
    @SerializedName(
        "division_id",
        alternate = ["division_master_id"]
    ) @Expose var productId: String,

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
    @SerializedName("products") var products: List<ProductAndSubseries> = listOf(),
):Serializable

//below on response
data class ClientProjectResponse(
    @SerializedName("client_projects") var clientProjects: List<ClientProject> = listOf(),
)