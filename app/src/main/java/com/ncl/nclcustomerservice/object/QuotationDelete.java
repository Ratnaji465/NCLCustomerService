package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/8/2019.
 */

public class QuotationDelete implements Serializable
{

    @SerializedName("Quotation_id")
    @Expose
    public int quotationId;
    private final static long serialVersionUID = 836274063847067499L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationId", quotationId).toString();
    }
}
