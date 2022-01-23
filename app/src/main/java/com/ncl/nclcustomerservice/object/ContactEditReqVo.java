package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/1/2018.
 */

public class ContactEditReqVo implements Serializable
{

    @SerializedName("contact_id")
    @Expose
    public int contactId;
    @SerializedName("Salutation")
    @Expose
    public String salutation;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("LastName")
    @Expose
    public String lastName;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Fax")
    @Expose
    public String fax;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("Company_text")
    @Expose
    public String companyText;
    @SerializedName("Department")
    @Expose
    public String department;
    @SerializedName("Title_Designation")
    @Expose
    public String titleDesignation;
    @SerializedName("OtherPhone")
    @Expose
    public String otherPhone;
    @SerializedName("HomePhone")
    @Expose
    public String homePhone;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Birthdate")
    @Expose
    public String birthdate;
    @SerializedName("LeadSource")
    @Expose
    public String leadSource;
    @SerializedName("ReportsTo")
    @Expose
    public String reportsTo;
    @SerializedName("Category")
    @Expose
    public String category;
    @SerializedName("other_category")
    @Expose
    public String otherCategory;
    @SerializedName("MallingStreet1")
    @Expose
    public String mallingStreet1;
    @SerializedName("Mallingstreet2")
    @Expose
    public String mallingstreet2;
    @SerializedName("MallingStateProvince")
    @Expose
    public String mallingStateProvince;
    @SerializedName("MallingCity")
    @Expose
    public String mallingCity;
    @SerializedName("MallingZipPostal")
    @Expose
    public String mallingZipPostal;
    @SerializedName("MallingCountry")
    @Expose
    public String mallingCountry;
    @SerializedName("OtherStreet1")
    @Expose
    public String otherStreet1;
    @SerializedName("Otherstreet2")
    @Expose
    public String otherstreet2;
    @SerializedName("OtherCountry")
    @Expose
    public String otherCountry;
    @SerializedName("OtherStateProvince")
    @Expose
    public String otherStateProvince;
    @SerializedName("OtherCity")
    @Expose
    public String otherCity;
    @SerializedName("OtherZipPostal")
    @Expose
    public String otherZipPostal;
    @SerializedName("isAccountTagged")
    @Expose
    public String isAccountTagged;
    private final static long serialVersionUID = -3357241199743017660L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).append("salutation", salutation).append("firstName", firstName).append("lastName", lastName).append("email", email).append("fax", fax).append("mobile", mobile).append("phone", phone).append("company", company).append("department", department).append("titleDesignation", titleDesignation).append("otherPhone", otherPhone).append("homePhone", homePhone).append("description", description).append("birthdate", birthdate).append("leadSource", leadSource).append("reportsTo", reportsTo).append("category", category).append("mallingStreet1", mallingStreet1).append("mallingstreet2", mallingstreet2).append("mallingStateProvince", mallingStateProvince).append("mallingCity", mallingCity).append("mallingZipPostal", mallingZipPostal).append("mallingCountry", mallingCountry).append("otherStreet1", otherStreet1).append("otherstreet2", otherstreet2).append("otherCountry", otherCountry).append("otherStateProvince", otherStateProvince).append("otherCity", otherCity).append("otherZipPostal", otherZipPostal).toString();
    }
}
