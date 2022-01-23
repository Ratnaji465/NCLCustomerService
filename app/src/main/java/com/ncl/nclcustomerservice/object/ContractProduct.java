package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 11/5/2018.
 */

public class ContractProduct {
    @SerializedName("Product")
    @Expose
    public String product;
    @SerializedName("ListPrice")
    @Expose
    public String listPrice;
    @SerializedName("Quantity")
    @Expose
    public String quantity;
    @SerializedName("Discount")
    @Expose
    public String discount;
    @SerializedName("Subtotal")
    @Expose
    public String subtotal;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("product", product).append("listPrice", listPrice).append("quantity", quantity).append("discount", discount).append("subtotal", subtotal).toString();
    }
}
