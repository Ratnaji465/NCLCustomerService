package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class PriceUserList implements Serializable
{

    @SerializedName("Product_price_master_id")
    @Expose
    public String productPriceMasterId;
    @SerializedName("Area")
    @Expose
    public String area;
    private final static long serialVersionUID = 3443663252074520969L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productPriceMasterId", productPriceMasterId).append("area", area).toString();
    }
}
