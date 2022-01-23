package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by User on 10/9/2018.
 */

public class ContractDelete {

    @SerializedName("contract_id")
    @Expose
    public int contractId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contractId", contractId).toString();
    }

}
