package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/17/2018.
 */

public class DeleteSalesOrderItemReqVo {
    @SerializedName("sales_order_id")
    @Expose
    public int salesOrderId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesOrderId", salesOrderId).toString();
    }
}
