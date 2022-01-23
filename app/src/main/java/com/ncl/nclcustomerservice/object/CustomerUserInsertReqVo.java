package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/20/2018.
 */

public class CustomerUserInsertReqVo implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("user_list")
    @Expose
    public List<UsersList> userList = null;
    private final static long serialVersionUID = -8509563860524047849L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("userList", userList).toString();
    }
}
