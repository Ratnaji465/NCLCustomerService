package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class CustomerIdName implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    public String customer_id;
    @SerializedName("CustomerName")
    @Expose
    public String CustomerName;

}
