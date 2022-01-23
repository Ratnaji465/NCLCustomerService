package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/23/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = OpportunitiesList.class, parentColumns = "opportunityId", childColumns = "oppProduct", onDelete = ForeignKey.CASCADE))
public class OpportunityProductLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("product_id")
    @Expose
    public String productId;
    @SerializedName("product_name")
    @Expose
    public String productName;
    @SerializedName("quantity")
    @Expose
    public String quantity;
    @SerializedName("probability")
    @Expose
    public String probability;
    @SerializedName("schedule_date_from")
    @Expose
    public String scheduleDateFrom;
    @SerializedName("schedule_date_upto")
    @Expose
    public String scheduleDateUpto;
    @SerializedName("Product_opportunities_id")
    @Expose
    public int oppProduct;
    @SerializedName("rate_per_sft")
    @Expose
    public String rate_per_sft;
    @SerializedName("value")
    @Expose
    public String value;
    private final static long serialVersionUID = 1788333467619556834L;


}
