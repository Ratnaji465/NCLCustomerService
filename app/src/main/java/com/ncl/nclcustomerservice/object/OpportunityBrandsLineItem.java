package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/20/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = OpportunitiesList.class, parentColumns = "opportunityId",childColumns = "oppoBrand",onDelete = ForeignKey.CASCADE))
public class OpportunityBrandsLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;

    @SerializedName("product")
    @Expose
    public String product;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("units")
    @Expose
    public String units;
    @SerializedName("quantity")
    @Expose
    public String quantity;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("brands_opp_id")
    @Expose
    public int oppoBrand;

    private final static long serialVersionUID = 1488830656785933032L;


}
