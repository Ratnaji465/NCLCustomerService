package com.ncl.nclcustomerservice.object;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ApiResponseController
{

    @SerializedName("result")
    @Expose
    public Object result;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("code")
    @Expose
    public int code;

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}



