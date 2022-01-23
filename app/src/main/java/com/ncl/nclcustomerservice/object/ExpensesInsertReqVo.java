package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/26/2018.
 */

public class ExpensesInsertReqVo implements Serializable
{
    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("expenses_id")
    @Expose
    public String expensesId;
    @SerializedName("expenses_type")
    @Expose
    public String expensesType;
    @SerializedName("expenses_name")
    @Expose
    public String expensesName;
    @SerializedName("ta_da_id")
    @Expose
    public int taDaId;
    @SerializedName("price")
    @Expose
    public String price;
    @SerializedName("expensesdate")
    @Expose
    public String expensesdate;
    private final static long serialVersionUID = 7235610155253100214L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("requestname", requestname).append("expensesId", expensesId).append("expensesType", expensesType).append("expensesName", expensesName).append("taDaId", taDaId).append("price", price).append("expensesdate", expensesdate).toString();
    }

}
