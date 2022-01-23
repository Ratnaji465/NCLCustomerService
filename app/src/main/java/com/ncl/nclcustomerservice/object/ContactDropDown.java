package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/2/2018.
 */

public class ContactDropDown implements Serializable {

    @SerializedName("Contact_id")
    @Expose
    public String contactId;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("designation")
    @Expose
    public String designation;
    @SerializedName("Company_id")
    @Expose
    public String Company_id;
    @SerializedName("Company")
    @Expose
    public String Company;
    @SerializedName("Mobile")
    @Expose
    public String Mobile;
    @SerializedName("Phone")
    @Expose
    public String Phone;
    @SerializedName("Email")
    @Expose
    public String Email;
    @SerializedName("Category")
    @Expose
    public String Category;
    @SerializedName("isAccountTagged")
    @Expose
    public String isAccountTagged;
    @SerializedName("ContactOwner")
    @Expose
    public String ContactOwner;
    @SerializedName("Customer_id")
    @Expose
    public String Customer_id;
    @SerializedName("Customer")
    @Expose
    public String Customer;
    private final static long serialVersionUID = -5362964955638418988L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).append("firstName", firstName).toString();
    }
}
