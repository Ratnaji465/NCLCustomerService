package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/25/2018.
 */

public class ExpensesList implements Serializable
{

    @SerializedName("expenses_id")
    @Expose
    public String expensesId;
    @SerializedName("expenses_number")
    @Expose
    public String expensesNumber;
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
    @SerializedName("expenses_owner")
    @Expose
    public String expensesOwner;
    @SerializedName("files")
    @Expose
    public List<String> files = null;
    private final static long serialVersionUID = 7929883659172172190L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("expensesId", expensesId).append("expensesNumber", expensesNumber).append("expensesType", expensesType).append("expensesName", expensesName).append("taDaId", taDaId).append("price", price).append("expensesdate", expensesdate).append("expensesOwner", expensesOwner).append("files", files).toString();
    }
}
