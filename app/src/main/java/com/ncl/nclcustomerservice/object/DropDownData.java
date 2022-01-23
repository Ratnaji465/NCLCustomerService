package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 10/1/2018.
 */

public class DropDownData {
    @SerializedName("Customer_list")
    @Expose
    public List<CustomerDropDown> customerList = null;
    @SerializedName("Contact_list")
    @Expose
    public List<ContactDropDown> contactList = null;
    @SerializedName("states_list")
    @Expose
    public List<StatesList> statesList = null;
    @SerializedName("associate_contacts")
    @Expose
    public List<AssociateContact> associateContacts = null;
    @SerializedName("users_list")
    @Expose
    public List<UsersList> usersList = null;
    @SerializedName("project_type")
    @Expose
    public List<ProjectType> projectType = null;
    @SerializedName("project_class_size")
    @Expose
    public List<ProjectClassSize> projectClassSize = null;
    @SerializedName("project_status")
    @Expose
    public List<ProjectStatus> projectStatus = null;
    @SerializedName("Sub_Dealer")
    @Expose
    public List<CustomerIdName> Sub_Dealer = null;
 @SerializedName("Distributor")
    @Expose
    public List<CustomerIdName> Distributor = null;
@SerializedName("Dealer")
    @Expose
    public List<CustomerIdName> Dealer = null;

    @SerializedName("incoterm_list")
    @Expose
    public List<IncotermList> incotermList = null;
    @SerializedName("payment_list")
    @Expose
    public List<PaymentList> paymentList = null;
    @SerializedName("price_list")
    @Expose
    public List<PriceUserList> priceList = null;
    @SerializedName("product_list")
    @Expose
    public List<ProductList> productList = null;
    @SerializedName("division_list")
    @Expose
    public List<DivisionList> divisionList = null;
    @SerializedName("DistributionChannel_list")
    @Expose
    public List<DistributionChannelList> distributionChannelList = null;
    @SerializedName("sales_organisation_list")
    @Expose
    public List<SalesOrganisationList> salesOrganisationList = null;
 @SerializedName("assigment")
    @Expose
    public List<AssigmentRecommendObj> assigmentList = null;
 @SerializedName("recommend")
    @Expose
    public List<AssigmentRecommendObj> recommendList = null;


    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerList", customerList).append("contactList", contactList).append("statesList", statesList).append("associateContacts", associateContacts).append("usersList", usersList).append("projectType", projectType).append("projectClassSize", projectClassSize).append("projectStatus", projectStatus).append("incotermList", incotermList).append("paymentList", paymentList).append("priceList", priceList).append("productList", productList).append("divisionList", divisionList).append("distributionChannelList", distributionChannelList).append("salesOrganisationList", salesOrganisationList).toString();
    }
}
