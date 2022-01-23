package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/9/2019.
 */

public class SalesOrganisationList implements Serializable
{

    @SerializedName("sales_organisation_id")
    @Expose
    public String salesOrganisationId;
    @SerializedName("organistation_name")
    @Expose
    public String organistationName;
    private final static long serialVersionUID = 9002704426207825756L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesOrganisationId", salesOrganisationId).append("organistationName", organistationName).toString();
    }
}
