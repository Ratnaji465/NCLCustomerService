package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/8/2018.
 */

public class TaDaListResVo implements Serializable
{

    @SerializedName("tada_list")
    @Expose
    public List<TadaList> tadaList = null;
    private final static long serialVersionUID = 7544054533209567644L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tadaList", tadaList).toString();
    }

}
