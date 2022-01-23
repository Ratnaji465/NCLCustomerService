package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class IncotermList implements Serializable
{

    @SerializedName("Incoterm_id")
    @Expose
    public String incotermId;
    @SerializedName("Incoterm_name")
    @Expose
    public String incotermName;
    private final static long serialVersionUID = -1006886232688027179L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("incotermId", incotermId).append("incotermName", incotermName).toString();
    }
}
