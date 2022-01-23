package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class ContactInsertResVo implements Serializable
{

    @SerializedName("contact_id")
    @Expose
    public Integer contactId;
    private final static long serialVersionUID = 3123581381337964526L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).toString();
    }
}
