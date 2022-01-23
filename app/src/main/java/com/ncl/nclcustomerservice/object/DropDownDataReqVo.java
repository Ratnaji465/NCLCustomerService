package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class DropDownDataReqVo implements Serializable {

    @SerializedName("customer_list")
    @Expose
    public String customerList;
    @SerializedName("contacts_list")
    @Expose
    public String contactsList;
    @SerializedName("associative_contacts")
    @Expose
    public String associativeContacts;
    @SerializedName("users_list")
    @Expose
    public String usersList;
    @SerializedName("IncoTerms")
    @Expose
    public String incoTerms;
    @SerializedName("Payment_terms")
    @Expose
    public String paymentTerms;
    @SerializedName("price_list")
    @Expose
    public String priceList;
    @SerializedName("all_product_list")
    @Expose
    public String allProductList;
    @SerializedName("assigment_recommend")
    @Expose
    public String assigmentRecommend;
    @SerializedName("team_id")
    @Expose
    public String teamId;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("sales_organisation")
    @Expose
    public String sales_organisation;
 @SerializedName("divisions")
    @Expose
    public String divisions;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerList", customerList).append("contactsList", contactsList).append("associativeContacts", associativeContacts).append("usersList", usersList).append("incoTerms", incoTerms).append("paymentTerms", paymentTerms).append("priceList", priceList).append("allProductList", allProductList).toString();
    }

}
