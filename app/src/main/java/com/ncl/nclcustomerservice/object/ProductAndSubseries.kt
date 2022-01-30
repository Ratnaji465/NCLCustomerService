package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.SerializedName


data class ProductAndSubseries(
    @SerializedName("division_id") var productId: String,
    @SerializedName("product_id") var subSeriesId: String
)

data class ClientProjectRequest(
    @SerializedName("requestname") var requestName: String = "",
    @SerializedName("products") var products: List<ProductAndSubseries> = listOf(),
)