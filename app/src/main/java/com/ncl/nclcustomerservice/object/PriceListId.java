package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 12/3/2018.
 */

public class PriceListId {
    @SerializedName("price_list_id")
    @Expose
    public String priceListId;
    @SerializedName("division_id")
    @Expose
    public String division_id;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("priceListId", priceListId).toString();
    }

}
