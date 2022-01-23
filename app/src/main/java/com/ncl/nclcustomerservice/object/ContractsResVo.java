package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 10/8/2018.
 */

public class ContractsResVo implements Serializable
{

    @SerializedName("contract_list")
    @Expose
    public List<ContractList> contractList = null;
    private final static long serialVersionUID = -8843140685428412089L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contractList", contractList).toString();
    }
}
