package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class UserList implements Serializable
{

    @SerializedName("user_id")
    @Expose
    public String userId;
    private final static long serialVersionUID = 4084542696842130083L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).toString();
    }

}
