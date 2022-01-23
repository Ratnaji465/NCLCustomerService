package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class MapCustomerReq implements Serializable {
    @SerializedName("user_id")
    @Expose
    public String user_id;
    @SerializedName("customer_id")
    @Expose
    public String customer_id;


}
