package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

@Entity
public class CustomerList implements Serializable {


   /* @PrimaryKey(autoGenerate = true)
    @NonNull
    @SerializedName("primaryKey")
    @Expose
    public int primaryKey;*/
    @PrimaryKey
    @NonNull
    @SerializedName("customer_id")
    @Expose
    public int customerId;
 @SerializedName("CustomerName")
 @Expose
 public String customerName;
 @SerializedName("CustomerContactName")
 @Expose
 public String contactName;
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
 @SerializedName("PaymentTerms")
 @Expose
 public String paymentTerms;
 @SerializedName("pancard")
 @Expose
 public String pancard;
 @SerializedName("BillingStreet1")
 @Expose
 public String billingStreet1;
 @SerializedName("BillingStreet2")
 @Expose
 public String billingStreet2;
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
 @SerializedName("ShippingCity")
 @Expose
 public String shippingCity;
 @SerializedName("ShippingStateProvince")
 @Expose
 public String shippingStateProvince;
 @SerializedName("ShippingZipPostal")
 @Expose
 public String shippingZipPostal;
 @SerializedName("SalesOrganisation")
 @Expose
 public String salesOrganisation;
 @SerializedName("DistributionChannel")
 @Expose
 public String distributionChannel;
 @SerializedName("Division")
 @Expose
 public String division;
 @SerializedName("CustomerType")
 @Expose
 public String customerType;
 @SerializedName("Email")
 @Expose
 public String email;
 @SerializedName("CustomerCategory")
 @Expose
 public String customerCategory;
 @SerializedName("CreditLimit")
 @Expose
 public String creditLimit;
 @SerializedName("SecurityInstruments")
 @Expose
 public String securityInstruments;
 @SerializedName("Pdc_Check_number")
 @Expose
 public String pdcCheckNumber;
 @SerializedName("Bank")
 @Expose
 public String bank;
 @SerializedName("Bank_guarntee_amount_Rs")
 @Expose
 public String bankGuarnteeAmountRs;
 @SerializedName("LC_amount_Rs")
 @Expose
 public String lCAmountRs;
 @SerializedName("IncoTerms1")
 @Expose
 public String incoTerms1;
 @SerializedName("IncoTerms2")
 @Expose
 public String incoTerms2;
 @SerializedName("Fax")
 @Expose
 public String fax;
 @SerializedName("Industry")
 @Expose
 public String industry;
 @SerializedName("contact_id")
 @Expose
 public String contactId;
 @SerializedName("opportunity_id")
 @Expose
 public String opportunityId;
 @SerializedName("price_list")
 @Expose
 public String priceList;
 @SerializedName("ship_to_party")
 @Expose
 @Ignore
 public List<ShipToParty> shipToParty = null;
 @SerializedName("sold_to_party")
 @Expose
 @Ignore
 public List<SoldToParty> soldToParty = null;
 @SerializedName("bill_to_party")
 @Expose
 @Ignore
 public List<BillToParty> billToParty = null;

 @SerializedName("price_list_id")
 @Expose
 public String priceListId;
 @Ignore
 @SerializedName("user_details")
 @Expose
 public List<CustomerUserList> customerUserList = null;
 @SerializedName("CustomerOwner")
 @Expose
 public String customerOwner;
 @SerializedName("ParentAccount")
 @Expose
 public String parentAccount;
@SerializedName("created_date_time")
 @Expose
 public String created_date_time;
@SerializedName("remarks")
 @Expose
 public String remarks;
@SerializedName("customer_number")
 @Expose
 public String customer_number;
@SerializedName("approve_status")
 @Expose
 public String approve_status;
@SerializedName("manager_user_id")
 @Expose
 public String manager_user_id;
@SerializedName("approval_comments")
 @Expose
 public String approval_comments;
@SerializedName("manager_name")
 @Expose
 public String manager_name;

@SerializedName("Customer_location")
 @Expose
 public String Customer_location;


}