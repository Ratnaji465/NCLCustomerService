package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/13/2018.
 */

public class QuotationResVo implements Serializable
{

    @SerializedName("Customer")
    @Expose
    public String customer;
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
    @SerializedName("total_amount")
    @Expose
    public Object totalAmount;
    @SerializedName("contact_list")
    @Expose
    public List<ContactList> contactList = null;
    private final static long serialVersionUID = 8770599388250471821L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customer", customer).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shippingZipPostal", shippingZipPostal).append("totalAmount", totalAmount).append("contactList", contactList).toString();
    }

}
