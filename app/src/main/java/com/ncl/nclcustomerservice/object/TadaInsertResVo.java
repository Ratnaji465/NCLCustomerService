package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/25/2019.
 */

public class TadaInsertResVo implements Serializable
{

    @SerializedName("ta_da_id")
    @Expose
    public Integer taDaId;
    private final static long serialVersionUID = 8832254857268246517L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("taDaId", taDaId).toString();
    }
}
