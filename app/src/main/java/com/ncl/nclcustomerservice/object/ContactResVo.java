package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/1/2018.
 */

public class ContactResVo implements Serializable
{

    @SerializedName("contact_list")
    @Expose
    public List<ContactList> contactList = null;
    private final static long serialVersionUID = -7146322085826028609L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactList", contactList).toString();
    }

}
