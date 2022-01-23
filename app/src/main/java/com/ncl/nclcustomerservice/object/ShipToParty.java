package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class ShipToParty implements Serializable {

    @SerializedName("customer_address_sold_bill_ship_id")
    @Expose
    public String customerAddressSoldBillShipId;
    @SerializedName("ship_title")
    @Expose
    public String shipTitle;
    @SerializedName("ship_street")
    @Expose
    public String shipStreet;
    @SerializedName("ship_city")
    @Expose
    public String shipCity;
    @SerializedName("ship_state")
    @Expose
    public String shipState;
    @SerializedName("ship_counter")
    @Expose
    public String shipCounter;
    @SerializedName("ship_pin_code")
    @Expose
    public String shipPinCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerAddressSoldBillShipId",customerAddressSoldBillShipId).append("shipTitle", shipTitle).append("shipStreet", shipStreet).append("shipCity", shipCity).append("shipState", shipState).append("shipCounter", shipCounter).append("shipPinCode", shipPinCode).toString();
    }
}
