package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/5/2018.
 */

public class ProductDrop implements Serializable
{

    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("product_code")
    @Expose
    public String productCode;
//    @SerializedName("price_list")
//    @Expose
//    public List<PriceList> priceList = null;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("weight")
    @Expose
    public String weight;
    private final static long serialVersionUID = -8510254541458357390L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productId", productId).append("productName", productName).append("productCode", productCode)/*.append("priceList", priceList)*/.toString();
    }
}
