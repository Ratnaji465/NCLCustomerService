package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/28/2019.
 */

public class QuotationInsertResVo implements Serializable
{

    @SerializedName("Quotation_id")
    @Expose
    public Integer quotationId;
    private final static long serialVersionUID = -5431529622085176094L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationId", quotationId).toString();
    }
}
