package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/22/2018.
 */

public class QuotationPdfReqVo implements Serializable
{
    @SerializedName("Quotation_id")
    @Expose
    public int quotationId;
    @SerializedName("type")
    @Expose
    public String type;
    private final static long serialVersionUID = 2409401120037867453L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationId", quotationId).append("type", type).toString();
    }
}
