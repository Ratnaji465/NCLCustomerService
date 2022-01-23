package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 10/4/2018.
 */

public class UsersList implements Serializable {
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("user_name")
    @Expose
    public String userName;
    public boolean aBoolean;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("userName", userName).toString();
    }
}
