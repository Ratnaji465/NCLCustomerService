package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 2/12/2019.
 */

public class DashboardResVo implements Serializable
{

    @SerializedName("dashboard")
    @Expose
    public Dashboard dashboard;
    private final static long serialVersionUID = -1046010153020288339L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("dashboard", dashboard).toString();
    }

}
