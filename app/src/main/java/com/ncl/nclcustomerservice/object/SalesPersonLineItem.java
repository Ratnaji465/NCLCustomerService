package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by sowmy on 10/4/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = SalesOrderList.class, parentColumns = "salesOrderId", childColumns = "saleslineItemId", onDelete = ForeignKey.CASCADE))
public class SalesPersonLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("product_id")
    @Expose
    public String salesOrderProductsId;
    @SerializedName("Product_opportunities_id")
    @Expose
    public String productOpportunitiesId;
    @SerializedName("plan_quantity")
    @Expose
    public String plan_quantity;
    @SerializedName("ordered_quantity")
    @Expose
    public String ordered_quantity;
    @SerializedName("supplied_quantity")
    @Expose
    public String supplied_quantity;
    @SerializedName("supplied_date")
    @Expose
    public String supplied_date;
    @SerializedName("product")
    @Expose
    public String productName;

    public int saleslineItemId;


}
