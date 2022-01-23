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
@Entity(foreignKeys = @ForeignKey(entity = OpportunitiesList.class,parentColumns = "opportunityId",childColumns = "opportunityCompetion",onDelete = ForeignKey.CASCADE))
public class OpportunityCompetitionLineItem implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("product")
    @Expose
    public String product;
    @SerializedName("units")
    @Expose
    public String units;
    @SerializedName("price")
    @Expose
    public String price;
    public int opportunityCompetion;

    private final static long serialVersionUID = 4067882244999349931L;


}
