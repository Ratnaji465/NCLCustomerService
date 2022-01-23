package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/4/2018.
 */

public class LeadConvertCheckResVo implements Serializable
{

    @SerializedName("checking_customer")
    @Expose
    public List<CheckingCustomer> checkingCustomer = null;
    @SerializedName("contact_name")
    @Expose
    public String contactName;
    private final static long serialVersionUID = 601850049467023265L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("checkingCustomer", checkingCustomer).append("contactName", contactName).toString();
    }



}
