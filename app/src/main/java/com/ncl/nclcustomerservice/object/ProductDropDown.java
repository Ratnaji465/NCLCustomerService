package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/5/2018.
 */

public class ProductDropDown implements Serializable
{

    @SerializedName("product_drop")
    @Expose
    public List<ProductDrop> productDrop = null;
    private final static long serialVersionUID = 7769264771130979511L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productDrop", productDrop).toString();
    }
}
