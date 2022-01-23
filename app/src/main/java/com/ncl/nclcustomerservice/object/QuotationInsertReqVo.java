package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/13/2018.
 */

public class QuotationInsertReqVo implements Serializable
{
    @SerializedName("Opportunity")
    @Expose
    public int opportunity;
    @SerializedName("Quotation_id")
    @Expose
    public int quotationId;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("Contact")
    @Expose
    public String contact;
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
    @SerializedName("TotalPrice")
    @Expose
    public String totalPrice;
    @SerializedName("Remarks")
    @Expose
    public String remarks;
    @SerializedName("QuotationDate")
    @Expose
    public String quotationDate;
    @SerializedName("ExpiryDate")
    @Expose
    public String expiryDate;
    @SerializedName("products_price")
    @Expose
    public List<QuotationProductList> productsPrice = null;
    private final static long serialVersionUID = -7919722713033278555L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationId", quotationId).append("opportunity", opportunity).append("customer", customer).append("contact", contact).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shippingZipPostal", shippingZipPostal).append("totalPrice", totalPrice).append("remarks", remarks).append("quotationDate", quotationDate).append("expiryDate", expiryDate).append("productsPrice", productsPrice).toString();
    }

}
