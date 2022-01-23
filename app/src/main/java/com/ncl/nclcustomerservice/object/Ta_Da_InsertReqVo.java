package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/22/2018.
 */

public class Ta_Da_InsertReqVo  implements Serializable
{

    @SerializedName("ta_da_id")
    @Expose
    public int taDaId;
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
    @SerializedName("Amountclaimed")
    @Expose
    public String amountclaimed;
    @SerializedName("TowardsTA")
    @Expose
    public String towardsTA;
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
    private final static long serialVersionUID = -130978580414134349L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taDaId", taDaId).append("fromdate", fromdate).append("todate", todate).append("name", name).append("designation", designation).append("amountclaimed", amountclaimed).append("towardsTA", towardsTA).append("hotelAccommodation", hotelAccommodation).append("towardsDA", towardsDA).append("towardsConveyance", towardsConveyance).append("others", others).append("total", total).append("advanceTakenon", advanceTakenon).append("tABillPassedfor", tABillPassedfor).append("balanceDue", balanceDue).append("verified", verified).append("verifiedBY", verifiedBY).toString();
    }

}