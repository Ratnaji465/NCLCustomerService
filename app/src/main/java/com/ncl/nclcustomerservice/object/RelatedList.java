package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 10/4/2018.
 */

public class RelatedList implements Serializable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("Company_Text")
    @Expose
    public String Company_Text;
    @SerializedName("Company")
    @Expose
    public String Company;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("price_list_id")
    @Expose
    public String price_list;
    @SerializedName("price_list_area")
    @Expose
    public String price_list_area;
    @SerializedName("approve_status")
    @Expose
    public String approved;
    @SerializedName("SAP_code")
    @Expose
    public String sap;
 @SerializedName("customer_type")
    @Expose
    public String customer_type ;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("id", id).append("name", name).toString();
    }

}
