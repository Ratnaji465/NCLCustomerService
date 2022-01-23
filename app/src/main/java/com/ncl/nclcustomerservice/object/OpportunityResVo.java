package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/4/2018.
 */

public class OpportunityResVo implements Serializable
{

    @SerializedName("opportunities_list")
    @Expose
    public List<OpportunitiesList> opportunitiesList = null;
    private final static long serialVersionUID = 6341411905582380434L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("opportunitiesList", opportunitiesList).toString();
    }

}
