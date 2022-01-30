package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 1/9/2019.
 */

public class DivisionList implements Serializable
{

    @SerializedName("division_master_id")
    @Expose
    public String divisionMasterId;
    @SerializedName("division_name")
    @Expose
    public String divisionName;
    @SerializedName("division_sap_code")
    @Expose
    public String divisionSapCode;
    @SerializedName("products_list")
    @Expose
    public List<ProductList> productList;
    private final static long serialVersionUID = 7356533960173061017L;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("divisionMasterId", divisionMasterId)
                .append("divisionName", divisionName)
                .append("divisionSapCode",divisionSapCode)
                .append("productList",productList)
                .toString();
    }

}
