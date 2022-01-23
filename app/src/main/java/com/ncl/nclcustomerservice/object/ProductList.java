package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/20/2018.
 */

public class ProductList implements Serializable
{

    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_code")
    @Expose
    public String productCode;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("rate_per_sft")
    @Expose
    public String rate_per_sft;
    private final static long serialVersionUID = 952871353316191856L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productId", productId).append("productCode", productCode).append("productName", productName).toString();
    }

}
