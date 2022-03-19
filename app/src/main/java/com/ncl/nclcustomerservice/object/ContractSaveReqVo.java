package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 11/5/2018.
 */

public class ContractSaveReqVo {
    @SerializedName("contract_id")
    @Expose
    public int contractId;
    @SerializedName("Customer")
    @Expose
    public String customer;
    @SerializedName("ActivatedBy")
    @Expose
    public String activatedBy;
    @SerializedName("ActivatedDate")
    @Expose
    public String activatedDate;
    @SerializedName("CompanySignedBy")
    @Expose
    public String companySignedBy;
    @SerializedName("CompanySignedDate")
    @Expose
    public String companySignedDate;
    @SerializedName("ContractStartDate")
    @Expose
    public String contractStartDate;
    @SerializedName("ContractEndDate")
    @Expose
    public String contractEndDate;
    @SerializedName("ContractName")
    @Expose
    public String contractName;
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
    @SerializedName("OwnerExpirationNotice")
    @Expose
    public String ownerExpirationNotice;
    @SerializedName("SpecialTerms")
    @Expose
    public String specialTerms;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("total_amount")
    @Expose
    public String totalAmount;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contractId",contractId).append("customer", customer).append("activatedBy", activatedBy).append("activatedDate", activatedDate).append("companySignedBy", companySignedBy).append("companySignedDate", companySignedDate).append("contractStartDate", contractStartDate).append("contractEndDate", contractEndDate).append("contractName", contractName).append("contractTerm", contractTerm).append("customerSignedBy", customerSignedBy).append("customerSignedDate", customerSignedDate).append("description", description).append("ownerExpirationNotice", ownerExpirationNotice).append("specialTerms", specialTerms).append("status", status).append("totalAmount", totalAmount).toString();
    }
}
