package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/5/2018.
 */

public class ListReqVo implements Serializable
{

    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("value")
    @Expose
    public String value;
    @SerializedName("id")
    @Expose
    public int id;
    private final static long serialVersionUID = 8714204706724312801L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("type", type).append("value", value).append("id", id).toString();
    }
}
