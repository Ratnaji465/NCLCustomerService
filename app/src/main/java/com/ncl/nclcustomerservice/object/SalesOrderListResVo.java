package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/2/2018.
 */

public class SalesOrderListResVo implements Serializable
{

    @SerializedName("sales_order_list")
    @Expose
    public List<SalesOrderList> salesOrderList = null;
    private final static long serialVersionUID = -710225401682814837L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesOrderList", salesOrderList).toString();
    }
}
