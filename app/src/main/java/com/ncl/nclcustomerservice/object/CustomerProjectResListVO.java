package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class CustomerProjectResListVO implements Serializable {
    @SerializedName("Customer_Projects")
    @Expose
    public List<CustomerProjectResVO> customerProjectResVOList;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("customerProjectResVOList", customerProjectResVOList).toString();
    }
}
