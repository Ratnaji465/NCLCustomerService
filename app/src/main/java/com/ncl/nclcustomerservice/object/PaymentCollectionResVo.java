package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 3/6/2019.
 */

public class PaymentCollectionResVo implements Serializable
{

    @SerializedName("payment_collection_list")
    @Expose
    public List<PaymentCollectionList> paymentCollectionList = null;
    private final static long serialVersionUID = -8463527841214229656L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("paymentCollectionList", paymentCollectionList).toString();
    }
}
