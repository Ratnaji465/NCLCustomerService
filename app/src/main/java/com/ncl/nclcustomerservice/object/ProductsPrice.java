package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/5/2018.
 */

public class ProductsPrice implements Serializable
{
    @SerializedName("Product_opportunities_id")
    @Expose
    public String productOpportunitiesId;
    @SerializedName("Product")
    @Expose
    public String product;
    @SerializedName("ListPrice")
    @Expose
    public String listPrice;
    @SerializedName("Quantity")
    @Expose
    public int quantity;
    @SerializedName("Discount")
    @Expose
    public int discount;
    @SerializedName("Subtotal")
    @Expose
    public int subtotal;
    @SerializedName("count")
    @Expose
    public int count;
    @SerializedName("Probability")
    @Expose
    public String probability;
    @SerializedName("schedule_date_from")
    @Expose
    public String scheduleDateFrom;
    @SerializedName("schedule_date_upto")
    @Expose
    public String scheduleDateUpto;
    private final static long serialVersionUID = 5837203282651657376L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productOpportunitiesId", productOpportunitiesId).append("product", product).append("listPrice", listPrice).append("quantity", quantity).append("discount", discount).append("subtotal", subtotal).append("count",count).append("probability", probability).append("scheduleDateFrom", scheduleDateFrom).append("scheduleDateUpto", scheduleDateUpto).toString();
    }
}
