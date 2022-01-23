package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 10/1/2018.
 */

public class CustomerEdit implements Serializable {

    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("CustomerSAPCode")
    @Expose
    public String customerSAPCode;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Website")
    @Expose
    public String website;
    @SerializedName("AccountSource")
    @Expose
    public String accountSource;
    @SerializedName("AnnualRevenue")
    @Expose
    public String annualRevenue;
    @SerializedName("GSTINNumber")
    @Expose
    public String gSTINNumber;
    @SerializedName("Employees")
    @Expose
    public String employees;
    @SerializedName("Fax")
    @Expose
    public String fax;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("Type")
    @Expose
    public String type;
    @SerializedName("PaymentTerms")
    @Expose
    public String paymentTerms;
    @SerializedName("IncoTerms")
    @Expose
    public String incoTerms;
    @SerializedName("Ownership")
    @Expose
    public String ownership;
    @SerializedName("ParentAccount")
    @Expose
    public String parentAccount;
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
    @SerializedName("Billing")
    @Expose
    public String billing;
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
    @SerializedName("Shipping")
    @Expose
    public String shipping;
    @SerializedName("ShippingZipPostal")
    @Expose
    public String shippingZipPostal;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).append("customerSAPCode", customerSAPCode).append("description", description).append("phone", phone).append("website", website).append("accountSource", accountSource).append("annualRevenue", annualRevenue).append("gSTINNumber", gSTINNumber).append("employees", employees).append("fax", fax).append("industry", industry).append("type", type).append("paymentTerms", paymentTerms).append("incoTerms", incoTerms).append("ownership", ownership).append("parentAccount", parentAccount).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billing", billing).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shipping", shipping).append("shippingZipPostal", shippingZipPostal).toString();
    }
}
