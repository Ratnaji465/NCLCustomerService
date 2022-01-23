package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/4/2018.
 */

public class LeadConvertResVo implements Serializable
{

    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Fax")
    @Expose
    public String fax;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("Rating")
    @Expose
    public String rating;
    @SerializedName("Mobile")
    @Expose
    public String mobile;
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
    private final static long serialVersionUID = 6897638295572754224L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("description", description).append("email", email).append("fax", fax).append("industry", industry).append("rating", rating).append("mobile", mobile).append("projectName", projectName).append("projectType", projectType).append("sizeClassProject", sizeClassProject).append("statusProject", statusProject).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shippingZipPostal", shippingZipPostal).toString();
    }

}
