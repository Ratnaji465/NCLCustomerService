package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 2/12/2019.
 */

public class DashboardSalesFunnel implements Serializable
{

    @SerializedName("Prospecting_val")
    @Expose
    public double prospectingVal;
    @SerializedName("makers")
    @Expose
    public double makers;
    @SerializedName("fitment")
    @Expose
    public double fitment;
    @SerializedName("sample")
    @Expose
    public double sample;
    @SerializedName("Quote")
    @Expose
    public double quote;
    @SerializedName("Review")
    @Expose
    public double review;
    @SerializedName("price")
    @Expose
    public double price;
    @SerializedName("Payment")
    @Expose
    public double payment;
    @SerializedName("Won")
    @Expose
    public double won;
    @SerializedName("Lost")
    @Expose
    public double lost;
    private final static long serialVersionUID = -1372611943515804220L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prospectingVal", prospectingVal).append("makers", makers).append("fitment", fitment).append("sample", sample).append("quote", quote).append("review", review).append("price", price).append("payment", payment).append("won", won).append("lost", lost).toString();
    }
}
