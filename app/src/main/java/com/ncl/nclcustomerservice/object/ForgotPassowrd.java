package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/20/2018.
 */

public class ForgotPassowrd implements Serializable
{

    @SerializedName("email_id")
    @Expose
    public String emailId;
    private final static long serialVersionUID = -767062447859930203L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("emailId", emailId).toString();
    }

}
