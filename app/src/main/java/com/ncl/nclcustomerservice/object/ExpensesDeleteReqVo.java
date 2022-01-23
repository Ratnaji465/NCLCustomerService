package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/26/2018.
 */

public class ExpensesDeleteReqVo implements Serializable
{
    @SerializedName("expenses_id")
    @Expose
    public String expensesId;
    private final static long serialVersionUID = 7158036297378659071L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("expensesId", expensesId).toString();
    }

}
