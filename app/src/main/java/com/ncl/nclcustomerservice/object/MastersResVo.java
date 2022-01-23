package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 11/21/2018.
 */

public class MastersResVo implements Serializable{
    @SerializedName("lead_list")
    @Expose
    public List<LeadInsertReqVo> leadList = null;
    @SerializedName("customer_list")
    @Expose
    public List<CustomerList> customerList = null;
    @SerializedName("contact_list")
    @Expose
    public List<ContactList> contactList = null;
    @SerializedName("sales_call_list")
    @Expose
    public List<SalesCallList> salesCallList = null;
    @SerializedName("tada_list")
    @Expose
    public List<TadaList> tadaList = null;
    @SerializedName("complaint_list")
    @Expose
    public List<ComplaintsInsertReqVo> complaintList = null;
    @SerializedName("opportunities_list")
    @Expose
    public List<OpportunitiesList> opportunitiesList = null;
    @SerializedName("contract_list")
    @Expose
    public List<ContractList> contractList = null;
    @SerializedName("sales_order_list")
    @Expose
    public List<SalesOrderList> salesOrderList = null;
    @SerializedName("qutation_list")
    @Expose
    public List<QuotationList> qutationLists = null;
    @SerializedName("payment_collection_list")
    @Expose
    public List<PaymentCollectionList> paymentCollectionList = null;

    private final static long serialVersionUID = 191100281228983571L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadList", leadList).append("customerList", customerList).append("contactList", contactList).append("salesCallList", salesCallList).append("tadaList", tadaList).append("complaintList", complaintList).append("opportunitiesList", opportunitiesList).append("contractList", contractList).append("salesOrderList", salesOrderList).append("qutation_list", qutationLists).append("paymentCollectionList",paymentCollectionList).toString();
    }
}
