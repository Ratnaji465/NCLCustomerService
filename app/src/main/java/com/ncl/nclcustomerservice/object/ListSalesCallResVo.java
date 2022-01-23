package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/5/2018.
 */

public class ListSalesCallResVo implements Serializable
{
    @SerializedName("sales_call")
    @Expose
    public List<ListSalesCallContact> salesCall = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCall", salesCall).toString();
    }
}
