package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 12/13/2018.
 */

public class SalesOrderDropDownResVo {
    @SerializedName("customers_list")
    @Expose
    public List<CustomersList> customersList = null;
    @SerializedName("plant_list")
    @Expose
    public List<PlantList> plantList = null;
    @SerializedName("freight_list")
    @Expose
    public List<FreightList> freightList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customersList", customersList).append("plantList", plantList).append("freightList", freightList).toString();
    }

}
