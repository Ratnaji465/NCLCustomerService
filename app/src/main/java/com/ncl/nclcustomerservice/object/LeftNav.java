package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 9/27/2018.
 */

public class LeftNav implements Serializable
{
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("method_name")
    @Expose
    public String methodName;
    @SerializedName("left_icon")
    @Expose
    public String leftIcon;
    @SerializedName("read")
    @Expose
    public String read;
    @SerializedName("create")
    @Expose
    public String create;
    @SerializedName("update")
    @Expose
    public String update;
    @SerializedName("delete")
    @Expose
    public String delete;
    public int drawable;

    private final static long serialVersionUID = -3659042757757372778L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).append("methodName",methodName).append("leftIcon", leftIcon).append("read", read).append("create", create).append("update", update).append("delete", delete).toString();
    }

}
