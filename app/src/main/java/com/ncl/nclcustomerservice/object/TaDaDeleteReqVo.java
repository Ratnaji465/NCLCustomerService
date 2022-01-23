package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/25/2018.
 */

public class TaDaDeleteReqVo implements Serializable
{
    @SerializedName("ta_da_id")
    @Expose
    public int taDaId;
    private final static long serialVersionUID = -8223279010081350370L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taDaId", taDaId).toString();
    }
}
