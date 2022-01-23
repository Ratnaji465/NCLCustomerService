package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 12/6/2018.
 */

public class UsersTeam implements Serializable
{

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("name")
    @Expose
    public String name;
    private final static long serialVersionUID = -2730438746955424178L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("name", name).toString();
    }
}
