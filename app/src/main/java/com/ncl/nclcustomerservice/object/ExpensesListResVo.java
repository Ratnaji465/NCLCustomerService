package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/25/2018.
 */

public class ExpensesListResVo implements Serializable
{

    @SerializedName("expenses_list")
    @Expose
    public List<ExpensesList> expensesList = null;
    private final static long serialVersionUID = 7071063715514165250L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("expensesList", expensesList).toString();
    }

}
