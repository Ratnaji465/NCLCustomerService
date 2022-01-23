package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/16/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = QuotationList.class, parentColumns = "quotationId" , childColumns = "quotationLineId", onDelete = ForeignKey.CASCADE))
public class QuotationProductList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;

    @SerializedName("Quotation_Product_id")
    @Expose
    public String quotationProductId;
    @SerializedName("ListPrice")
    @Expose
    public String listPrice;
    @SerializedName("Product")
    @Expose
    public String product;
    @SerializedName("Quantity")
    @Expose
    public String quantity;
    @SerializedName("Subtotal")
    @Expose
    public String subtotal;
    @SerializedName("Discount")
    @Expose
    public String discount;

    public int quotationLineId;

    private final static long serialVersionUID = 1679615511209713409L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationProductId", quotationProductId).append("listPrice", listPrice).append("product", product).append("quantity", quantity).append("subtotal", subtotal).append("discount", discount).toString();
    }
}
