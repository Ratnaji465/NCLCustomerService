package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;


public class BillToParty implements Serializable {
    @SerializedName("customer_address_sold_bill_ship_id")
    @Expose
    public String customerAddressSoldBillShipId;
    @SerializedName("bill_title")
    @Expose
    public String billTitle;
    @SerializedName("bill_street")
    @Expose
    public String billStreet;
    @SerializedName("bill_city")
    @Expose
    public String billCity;
    @SerializedName("bill_state")
    @Expose
    public String billState;
    @SerializedName("bill_counter")
    @Expose
    public String billCounter;
    @SerializedName("bill_pin_code")
    @Expose
    public String billPinCode;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerAddressSoldBillShipId",customerAddressSoldBillShipId).append("billTitle", billTitle).append("billStreet", billStreet).append("billCity", billCity).append("billState", billState).append("billCounter", billCounter).append("billPinCode", billPinCode).toString();
    }
}
