package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class SallesCallInsertResVo implements Serializable
{

    @SerializedName("sales_call_id")
    @Expose
    public Integer salesCallId;
    private final static long serialVersionUID = 9206361608674595651L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallId", salesCallId).toString();
    }
}
