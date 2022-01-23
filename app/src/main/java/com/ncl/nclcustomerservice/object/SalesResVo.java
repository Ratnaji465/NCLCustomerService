package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 10/4/2018.
 */

public class SalesResVo implements Serializable {

    @SerializedName("sales_call_list")
    @Expose
    public List<SalesCallList> salesCallList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallList", salesCallList).toString();
    }

}
