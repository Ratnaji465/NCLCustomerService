package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 2/6/2019.
 */

public class UpdateTableResVo implements Serializable
{

    @SerializedName("lead_list")
    @Expose
    public List<LeadInsertReqVo> leadList = null;
    @SerializedName("contact_list")
    @Expose
    public List<ContactList> contactList = null;
    @SerializedName("customer_list")
    @Expose
    public List<CustomerList> customerList = null;
    @SerializedName("opportunities_list")
    @Expose
    public List<OpportunitiesList> opportunitiesList = null;
    @SerializedName("contract_list")
    @Expose
    public List<ContractList> contractList = null;
    @SerializedName("sales_order_list")
    @Expose
    public List<SalesOrderList> salesOrderList = null;
    @SerializedName("sales_call_list")
    @Expose
    public List<SalesCallList> salesCallList = null;
    @SerializedName("qutation_list")
    @Expose
    public List<QuotationList> qutationList = null;
    private final static long serialVersionUID = -5366042532008218053L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("leadList", leadList).append("contactList", contactList).append("customerList", customerList).append("opportunitiesList", opportunitiesList).append("contractList", contractList).append("salesOrderList", salesOrderList).append("salesCallList", salesCallList).append("qutationList", qutationList).toString();
    }
}
