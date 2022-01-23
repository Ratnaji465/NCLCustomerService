package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/25/2018.
 */

public class TaDaDropDownResVo implements Serializable
{

    @SerializedName("users_list")
    @Expose
    public List<TaDaUsersList> usersList = null;
    @SerializedName("verfied_list")
    @Expose
    public List<TaDaVerfiedList> verfiedList = null;
    private final static long serialVersionUID = -7632018661720983159L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("usersList", usersList).append("verfiedList", verfiedList).toString();
    }
}
