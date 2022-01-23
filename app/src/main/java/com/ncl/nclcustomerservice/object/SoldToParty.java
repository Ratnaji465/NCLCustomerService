package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class SoldToParty implements Serializable {

    @SerializedName("customer_address_sold_bill_ship_id")
    @Expose
    public String customerAddressSoldBillShipId;
    @SerializedName("sold_title")
    @Expose
    public String soldTitle;
    @SerializedName("sold_street")
    @Expose
    public String soldStreet;
    @SerializedName("sold_city")
    @Expose
    public String soldCity;
    @SerializedName("sold_state")
    @Expose
    public String soldState;
    @SerializedName("sold_counter")
    @Expose
    public String soldCounter;
    @SerializedName("sold_pin_code")
    @Expose
    public String soldPinCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerAddressSoldBillShipId",customerAddressSoldBillShipId).append("soldTitle", soldTitle).append("soldStreet", soldStreet).append("soldCity", soldCity).append("soldState", soldState).append("soldCounter", soldCounter).append("soldPinCode", soldPinCode).toString();
    }
}
