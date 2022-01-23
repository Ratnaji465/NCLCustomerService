package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/4/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = SalesOrderList.class, parentColumns = "salesOrderId", childColumns = "saleslineItemId", onDelete = ForeignKey.CASCADE))
public class SalesOrderLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("sales_order_products_id")
    @Expose
    public String salesOrderProductsId;
    @SerializedName("Product_opportunities_id")
    @Expose
    public String productOpportunitiesId;
    @SerializedName("product_contract_id")
    @Expose
    public String productContractId;
    @SerializedName("ListPrice")
    @Expose
    public String listPrice;
    @SerializedName("plant_id")
    @Expose
    public String plantId;
    @SerializedName("plant_name")
    @Expose
    public String plantName;
    @SerializedName("Product")
    @Expose
    public String product;
    @SerializedName("Productcode")
    @Expose
    public String productcode;
    @SerializedName("Quantity")
    @Expose
    public String quantity;
    @SerializedName("Subtotal")
    @Expose
    public String subtotal;
    @SerializedName("Discount")
    @Expose
    public String discount;
    public int saleslineItemId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productOpportunitiesId", productOpportunitiesId).append("plantId", plantId).append("plantName", plantName).append("productContractId", productContractId).append("salesOrderProductsId", salesOrderProductsId).append("listPrice", listPrice).append("product", product).append("productcode", productcode).append("quantity", quantity).append("subtotal", subtotal).append("discount", discount).toString();
    }

}
