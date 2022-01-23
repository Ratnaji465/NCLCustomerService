package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/8/2018.
 */

public class TaDaItem implements Serializable
{
    @SerializedName("ta_da_id")
    @Expose
    public String taDaId;
    @SerializedName("Fromdate")
    @Expose
    public String fromdate;
    @SerializedName("Todate")
    @Expose
    public String todate;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Designation")
    @Expose
    public String designation;
    @SerializedName("TowardsTA")
    @Expose
    public String towardsTA;
    @SerializedName("Amountclaimed")
    @Expose
    public String amountclaimed;
    @SerializedName("Hotel_accommodation")
    @Expose
    public String hotelAccommodation;
    @SerializedName("TowardsDA")
    @Expose
    public String towardsDA;
    @SerializedName("Towards_conveyance")
    @Expose
    public String towardsConveyance;
    @SerializedName("Others")
    @Expose
    public String others;
    @SerializedName("Total")
    @Expose
    public String total;
    @SerializedName("AdvanceTakenon")
    @Expose
    public String advanceTakenon;
    @SerializedName("TABillPassedfor")
    @Expose
    public String tABillPassedfor;
    @SerializedName("BalanceDue")
    @Expose
    public String balanceDue;
    @SerializedName("Verified")
    @Expose
    public String verified;
    @SerializedName("VerifiedBY")
    @Expose
    public String verifiedBY;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("modified_by")
    @Expose
    public String modifiedBy;
    @SerializedName("created_date_time")
    @Expose
    public String createdDateTime;
    @SerializedName("modified_date_time")
    @Expose
    public String modifiedDateTime;
    private final static long serialVersionUID = -5724067575890850751L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taDaId", taDaId).append("fromdate", fromdate).append("todate", todate).append("name", name).append("designation", designation).append("towardsTA", towardsTA).append("amountclaimed", amountclaimed).append("hotelAccommodation", hotelAccommodation).append("towardsDA", towardsDA).append("towardsConveyance", towardsConveyance).append("others", others).append("total", total).append("advanceTakenon", advanceTakenon).append("tABillPassedfor", tABillPassedfor).append("balanceDue", balanceDue).append("verified", verified).append("verifiedBY", verifiedBY).append("createdBy", createdBy).append("modifiedBy", modifiedBy).append("createdDateTime", createdDateTime).append("modifiedDateTime", modifiedDateTime).toString();
    }

}
