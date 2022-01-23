package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 12/13/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = ContractList.class, parentColumns = "contractId", childColumns = "lineItemId", onDelete = ForeignKey.CASCADE))
public class ContractLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("product_contract_id")
    @Expose
    public String productContractId;
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

    public int lineItemId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("productContractId", productContractId).append("product", product).append("listPrice", listPrice).append("quantity", quantity).append("discount", discount).append("subtotal", subtotal).toString();
    }
}
