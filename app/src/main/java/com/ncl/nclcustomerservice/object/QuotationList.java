package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/16/2018.
 */
@Entity
public class QuotationList implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("Quotation_id")
    @Expose
    public int quotationId;
    @SerializedName("QuotationversionID")
    @Expose
    public String quotationversionID;
    @SerializedName("Opportunity")
    @Expose
    public int opportunity;
    @SerializedName("QuotationDate")
    @Expose
    public String quotationDate;
    @SerializedName("ExpiryDate")
    @Expose
    public String expiryDate;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("Customer_id")
    @Expose
    public String customerId;
    @SerializedName("Contact_id")
    @Expose
    public String contactId;
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
    @Ignore
    @SerializedName("qutation_product_list")
    @Expose
    public List<QuotationProductList> qutationProductList = null;
    @Ignore
    @SerializedName("approval_process")
    @Expose
    public List<ApprovalProcess> approvalProcess = null;
    private final static long serialVersionUID = -8411284504134987406L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("quotationId", quotationId).append("quotationversionID", quotationversionID).append("opportunity", opportunity).append("quotationDate", quotationDate).append("expiryDate", expiryDate).append("customer", customer).append("customerId", customerId).append("contactId", contactId).append("customerId", customerId).append("contact", contact).append("billingStreet1", billingStreet1).append("billingstreet2", billingstreet2).append("billingCountry", billingCountry).append("stateProvince", stateProvince).append("billingCity", billingCity).append("billingZipPostal", billingZipPostal).append("shippingStreet1", shippingStreet1).append("shippingstreet2", shippingstreet2).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingCity", shippingCity).append("shippingZipPostal", shippingZipPostal).append("totalPrice", totalPrice).append("remarks", remarks).append("qutationProductList", qutationProductList).append("approvalProcess", approvalProcess).toString();
    }

}
