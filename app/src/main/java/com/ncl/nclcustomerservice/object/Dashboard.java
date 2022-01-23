package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 2/12/2019.
 */

public class Dashboard implements Serializable
{

    @SerializedName("sales_call_list")
    @Expose
    public DashboardSalesCall salesCallList;
    @SerializedName("sales_funnel")
    @Expose
    public DashboardSalesFunnel salesFunnel;
    private final static long serialVersionUID = -6318920459111918443L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallList", salesCallList).append("salesFunnel", salesFunnel).toString();
    }
}
