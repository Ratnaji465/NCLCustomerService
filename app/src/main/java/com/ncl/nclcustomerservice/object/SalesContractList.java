package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 12/3/2018.
 */

public class SalesContractList implements Serializable {
    @SerializedName("contract_id")
    @Expose
    public String contractId;
    @SerializedName("contract_Name")
    @Expose
    public String contractName;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contractId", contractId).append("contractName", contractName).toString();
    }

}
