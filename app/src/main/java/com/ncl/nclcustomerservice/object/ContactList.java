package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Entity
public class ContactList implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("contact_id")
    @Expose
    public int contactId;
    @SerializedName("ReportsTo_name")
    @Expose
    public String reportsToName;
    @SerializedName("ReportsTo")
    @Expose
    public String reportsTo;
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
    @SerializedName("customer_id")
    @Expose
    public String customerId;
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
    @SerializedName("Birthdate")
    @Expose
    public String birthdate;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("LeadSource")
    @Expose
    public String leadSource;
    @SerializedName("ContactOwner")
    @Expose
    public String contactOwner;
    @SerializedName("ContactOwner_name")
    @Expose
    public String contactOwnerName;
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
    @SerializedName("MallingCountry")
    @Expose
    public String mallingCountry;
    @SerializedName("MallingStateProvince")
    @Expose
    public String mallingStateProvince;
    @SerializedName("MallingCity")
    @Expose
    public String mallingCity;
    @SerializedName("MallingZipPostal")
    @Expose
    public String mallingZipPostal;
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
   @SerializedName("created_date_time")
    @Expose
    public String created_date_time;
    private final static long serialVersionUID = -977744107413154322L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("reportsToName", reportsToName).append("reportsTo", reportsTo).append("contactId", contactId).append("salutation", salutation).append("firstName", firstName).append("lastName", lastName).append("email", email).append("fax", fax).append("mobile", mobile).append("phone", phone).append("company", company).append("customerId", customerId).append("department", department).append("titleDesignation", titleDesignation).append("otherPhone", otherPhone).append("homePhone", homePhone).append("birthdate", birthdate).append("description", description).append("leadSource", leadSource).append("contactOwner", contactOwner).append("contactOwnerName",contactOwnerName).append("category", category).append("mallingStreet1", mallingStreet1).append("mallingstreet2", mallingstreet2).append("mallingCountry", mallingCountry).append("mallingStateProvince", mallingStateProvince).append("mallingCity", mallingCity).append("mallingZipPostal", mallingZipPostal).append("otherStreet1", otherStreet1).append("otherstreet2", otherstreet2).append("otherCountry", otherCountry).append("otherStateProvince", otherStateProvince).append("otherCity", otherCity).append("otherZipPostal", otherZipPostal).toString();
    }


}
