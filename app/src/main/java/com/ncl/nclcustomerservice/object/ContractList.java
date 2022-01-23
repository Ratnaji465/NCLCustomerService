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
 * Created by User on 10/8/2018.
 */
@Entity
public class ContractList implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("contract_id")
    @Expose
    public int contractId;

    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("ActivatedBy")
    @Expose
    public String activatedBy;
    @SerializedName("ActivatedDate")
    @Expose
    public String activatedDate;
    @SerializedName("BillingAddress")
    @Expose
    public String billingAddress;
    @SerializedName("ShippingAddress")
    @Expose
    public String shippingAddress;
    @SerializedName("CompanySignedBy")
    @Expose
    public String companySignedBy;
    @SerializedName("CompanySignedDate")
    @Expose
    public String companySignedDate;
    @SerializedName("ContractName")
    @Expose
    public String contractName;
    @SerializedName("ContractNumber")
    @Expose
    public String contractNumber;
    @SerializedName("ContractStartDate")
    @Expose
    public String contractStartDate;
    @SerializedName("ContractEndDate")
    @Expose
    public String contractEndDate;
    @SerializedName("ContractOwner")
    @Expose
    public String contractOwner;
    @SerializedName("ContractTerm")
    @Expose
    public String contractTerm;
    @SerializedName("CustomerSignedBy")
    @Expose
    public String customerSignedBy;
    @SerializedName("CustomerSignedDate")
    @Expose
    public String customerSignedDate;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;
    @SerializedName("OwnerExpirationNotice")
    @Expose
    public String ownerExpirationNotice;
    @SerializedName("SpecialTerms")
    @Expose
    public String specialTerms;
    @SerializedName("Status")
    @Expose
    public String status;
    @Ignore
    @SerializedName("contract_product")
    @Expose
    public List<ContractLineItem> contractProduct = null;
    @Ignore
    @SerializedName("approval_process")
    @Expose
    public List<ApprovalProcess> approvalProcess = null;
    private final static long serialVersionUID = -1342641343206428082L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contractId", contractId).append("customer", customer).append("customerId", customerId).append("activatedBy", activatedBy).append("billingAddress", billingAddress).append("shippingAddress", shippingAddress).append("companySignedBy", companySignedBy).append("companySignedDate", companySignedDate).append("contractName", contractName).append("contractNumber", contractNumber).append("contractStartDate", contractStartDate).append("contractEndDate", contractEndDate).append("contractOwner", contractOwner).append("customerSignedBy", customerSignedBy).append("customerSignedDate", customerSignedDate).append("description", description).append("totalAmount", totalAmount).append("ownerExpirationNotice", ownerExpirationNotice).append("specialTerms", specialTerms).append("status", status).append("contractProduct", contractProduct).append("approvalProcess", approvalProcess).toString();
    }

}
