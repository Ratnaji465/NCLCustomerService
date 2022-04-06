package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class RemarksListVO implements Serializable {
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("remark_date")
    @Expose
    public String remarkDate;
    @SerializedName("remark")
    @Expose
    public String remark;

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("remakDate",remarkDate)
                .append("remark", remark).toString();
    }
}
