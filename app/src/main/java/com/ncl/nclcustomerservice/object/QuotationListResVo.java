package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/16/2018.
 */

public class QuotationListResVo implements Serializable
{

    @SerializedName("qutation_list")
    @Expose
    public List<QuotationList> qutationList = null;
    private final static long serialVersionUID = -5406487743391346011L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("qutationList", qutationList).toString();
    }

}
