package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */

public class PaymentList  implements Serializable
{

    @SerializedName("Payment_term_id")
    @Expose
    public String paymentTermId;
    @SerializedName("Payment_name")
    @Expose
    public String paymentName;
    private final static long serialVersionUID = 7339432684147400764L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("paymentTermId", paymentTermId).append("paymentName", paymentName).toString();
    }

}
