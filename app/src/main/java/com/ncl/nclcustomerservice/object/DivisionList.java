package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/9/2019.
 */

public class DivisionList implements Serializable
{

    @SerializedName("division_master_id")
    @Expose
    public String divisionMasterId;
    @SerializedName("division_name")
    @Expose
    public String divisionName;
    private final static long serialVersionUID = 7356533960173061017L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("divisionMasterId", divisionMasterId).append("divisionName", divisionName).toString();
    }

}
