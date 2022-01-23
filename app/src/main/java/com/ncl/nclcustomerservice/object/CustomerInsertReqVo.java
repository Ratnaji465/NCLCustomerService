package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 12/12/2018.
 */
@Entity
public class CustomerInsertReqVo implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("primaryKey")
    @Expose
    public int primaryKey;

    @SerializedName("AccountSource")
    @Expose
    public String accountSource;
    @SerializedName("AnnualRevenue")
    @Expose
    public String annualRevenue;
    @SerializedName("BillingCity")
    @Expose
    public String billingCity;
    @SerializedName("BillingCountry")
    @Expose
    public String billingCountry;
    @SerializedName("BillingStreet1")
    @Expose
    public String billingStreet1;
    @SerializedName("BillingZipPostal")
    @Expose
    public String billingZipPostal;
    @SerializedName("Billingstreet2")
    @Expose
    public String billingstreet2;
    @SerializedName("customer_id")
    @Expose
    public Integer customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("DistributionChannel")
    @Expose
    public String distributionChannel;
    @SerializedName("Division")
    @Expose
    public String division;
    @SerializedName("Employees")
    @Expose
    public String employees;
    @SerializedName("Fax")
    @Expose
    public String fax;
    @SerializedName("GSTINNumber")
    @Expose
    public String gSTINNumber;
    @SerializedName("IncoTerms1")
    @Expose
    public String incoTerms1;
    @SerializedName("IncoTerms2")
    @Expose
    public String incoTerms2;
    @SerializedName("Industry")
    @Expose
    public String industry;
    @SerializedName("pancard")
    @Expose
    public String pancard;
    @SerializedName("ParentAccount")
    @Expose
    public String parentAccount;
    @SerializedName("PaymentTerms")
    @Expose
    public String paymentTerms;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("SalesOrganisation")
    @Expose
    public String salesOrganisation;
    @SerializedName("ShippingCity")
    @Expose
    public String shippingCity;
    @SerializedName("ShippingCountry")
    @Expose
    public String shippingCountry;
    @SerializedName("ShippingStateProvince")
    @Expose
    public String shippingStateProvince;
    @SerializedName("ShippingStreet1")
    @Expose
    public String shippingStreet1;
    @SerializedName("ShippingZipPostal")
    @Expose
    public String shippingZipPostal;
    @SerializedName("Shippingstreet2")
    @Expose
    public String shippingstreet2;
    @SerializedName("StateProvince")
    @Expose
    public String stateProvince;
    @SerializedName("Type")
    @Expose
    public String type;
    @SerializedName("Website")
    @Expose
    public String website;
    @SerializedName("ship_to_party")
    @Expose
    public List<ShipToParty> shipToParty = null;
    @SerializedName("sold_to_party")
    @Expose
    public List<SoldToParty> soldToParty = null;
    @SerializedName("bill_to_party")
    @Expose
    public List<BillToParty> billToParty = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("accountSource", accountSource).append("annualRevenue", annualRevenue).append("billingCity", billingCity).append("billingCountry", billingCountry).append("billingStreet1", billingStreet1).append("billingZipPostal", billingZipPostal).append("billingstreet2", billingstreet2).append("customerId", customerId).append("customerName", customerName).append("description", description).append("distributionChannel", distributionChannel).append("division", division).append("employees", employees).append("fax", fax).append("gSTINNumber", gSTINNumber).append("incoTerms1", incoTerms1).append("incoTerms2", incoTerms2).append("industry", industry).append("pancard", pancard).append("parentAccount", parentAccount).append("paymentTerms", paymentTerms).append("phone", phone).append("salesOrganisation", salesOrganisation).append("shippingCity", shippingCity).append("shippingCountry", shippingCountry).append("shippingStateProvince", shippingStateProvince).append("shippingStreet1", shippingStreet1).append("shippingZipPostal", shippingZipPostal).append("shippingstreet2", shippingstreet2).append("stateProvince", stateProvince).append("type", type).append("website", website).append("shipToParty", shipToParty).append("soldToParty", soldToParty).append("billToParty", billToParty).toString();
    }
}
