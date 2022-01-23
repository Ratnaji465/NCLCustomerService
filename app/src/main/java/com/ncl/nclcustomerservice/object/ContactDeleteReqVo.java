package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/4/2018.
 */

public class ContactDeleteReqVo implements Serializable
{

    @SerializedName("contact_id")
    @Expose
    public int contactId;
    private final static long serialVersionUID = -9172250723643665817L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).toString();
    }
}
