package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class CheckCustomerReq implements Serializable {
    @SerializedName("pancard")
    @Expose
    public String pancard;
    @SerializedName("GSTINNumber")
    @Expose
    public String GSTINNumber;


}
