package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/1/2018.
 */
@Entity
public class Lead implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("leads_id")
    @Expose
    public int leadsId;
    @SerializedName("FirstName")
    @Expose
    public String firstName;
    @SerializedName("LastName")
    @Expose
    public String lastName;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("Associate_contact_id")
    @Expose
    public String associateContactId;
    @SerializedName("Address")
    @Expose
    public String address;
    @SerializedName("AnnualRevenue")
    @Expose
    public String annualRevenue;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("DoNotCall")
    @Expose
    public String doNotCall;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Fax")
    @Expose
    public String fax;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("LeadOwner")
    @Expose
    public String leadOwner;
    @SerializedName("LeadSource")
    @Expose
    public String leadSource;
    @SerializedName("LeadStatus")
    @Expose
    public String leadStatus;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
    @SerializedName("No_Employees")
    @Expose
    public String noEmployees;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Rating")
    @Expose
    public String rating;
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Website")
    @Expose
    public String website;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("project_name")
    @Expose
    public String projectName;
    @SerializedName("project_type")
    @Expose
    public String projectType;
    @SerializedName("size_class_project")
    @Expose
    public String sizeClassProject;
    @SerializedName("status_project")
    @Expose
    public String statusProject;
    @SerializedName("size_calss_unit")
    @Expose
    public String sizeCalssUnit;
    @SerializedName("BillingStreet1")
    @Expose
    public String billingStreet1;
    @SerializedName("Billingstreet2")
    @Expose
    public String billingstreet2;
    @SerializedName("BillingCountry")
    @Expose
    public String billingCountry;
    @SerializedName("StateProvince")
    @Expose
    public String stateProvince;
    @SerializedName("BillingCity")
    @Expose
    public String billingCity;
    @SerializedName("BillingZipPostal")
    @Expose
    public String billingZipPostal;
    @SerializedName("ShippingStreet1")
    @Expose
    public String shippingStreet1;
    @SerializedName("Shippingstreet2")
    @Expose
    public String shippingstreet2;
    @SerializedName("ShippingCountry")
    @Expose
    public String shippingCountry;
    @SerializedName("ShippingStateProvince")
    @Expose
    public String shippingStateProvince;
    @SerializedName("ShippingCity")
    @Expose
    public String shippingCity;
    @SerializedName("ShippingZipPostal")
    @Expose
    public String shippingZipPostal;
    @SerializedName("Category")
    @Expose
    public String category;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadsId", leadsId).append("firstName", firstName).append("lastName", lastName).append("company", company).append("associateContactId", associateContactId).append("address", address).append("annualRevenue", annualRevenue).append("description", description).append("doNotCall", doNotCall).append("email", email).append("fax", fax).append("industry", industry).append("leadOwner", leadOwner).append("leadSource", leadSource).append("leadStatus", leadStatus).append("mobile", mobile).append("noEmployees", noEmployees).append("phone", phone).append("rating", rating).append("title", title).append("website", website).append("status", status).append("projectName", projectName).append("projectType", projectType).append("sizeClassProject", sizeClassProject).append("statusProject", statusProject).append("sizeCalssUnit", sizeCalssUnit).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shippingZipPostal", shippingZipPostal).toString();
    }
}
