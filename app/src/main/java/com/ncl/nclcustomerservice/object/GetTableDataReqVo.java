package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 9/26/2018.
 */

public class GetTableDataReqVo {
    @SerializedName("table_name")
    @Expose
    public String tableName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tableName", tableName).toString();
    }
}
