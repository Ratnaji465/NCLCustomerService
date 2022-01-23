package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class CustomerUserResVo implements Serializable
{
    @SerializedName("customer_user_list")
    @Expose
    public List<CustomerUserList> customerUserList = null;
    private final static long serialVersionUID = 452785065717440634L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerUserList", customerUserList).toString();
    }
}
