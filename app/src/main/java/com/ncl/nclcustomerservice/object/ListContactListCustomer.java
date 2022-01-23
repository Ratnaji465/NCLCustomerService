package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/6/2018.
 */

public class ListContactListCustomer implements Serializable
{

    @SerializedName("contact_id")
    @Expose
    public int contactId;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("ContactOwner")
    @Expose
    public String contactOwner;
    private final static long serialVersionUID = 4413772488853181161L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).append("email", email).append("name", name).append("mobile", mobile).append("title", title).append("contactOwner", contactOwner).toString();
    }

}
