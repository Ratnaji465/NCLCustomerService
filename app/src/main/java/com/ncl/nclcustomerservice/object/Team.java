package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 12/6/2018.
 */

public class Team implements Serializable
{
    @SerializedName("team_id")
    @Expose
    public String teamId;
    @SerializedName("role_id")
    @Expose
    public String roleId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("table_name")
    @Expose
    public String tableName;
    @SerializedName("customerType")
    @Expose
    public String customerType=null;
    private final static long serialVersionUID = -1984306194268709873L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("teamId", teamId).append("roleId", roleId).append("type", type).append("tableName", tableName).toString();
    }

}
