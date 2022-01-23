package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/13/2018.
 */

public class ContactItem {
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_name")
    @Expose
    public String contactName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).append("contactName", contactName).toString();
    }
}
