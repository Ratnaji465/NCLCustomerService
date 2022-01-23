package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 12/13/2018.
 */

public class CustomersList {
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("customer_name")
    @Expose
    public String customerName;
    @SerializedName("customer_SAP_code")
    @Expose
    public String customerSAPCode;
    @SerializedName("contract_list")
    @Expose
    public List<ContractItem> contractList = null;
    @SerializedName("contact_list")
    @Expose
    public List<ContactItem> contactList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).append("contractList", contractList).append("contactList", contactList).toString();
    }
}
