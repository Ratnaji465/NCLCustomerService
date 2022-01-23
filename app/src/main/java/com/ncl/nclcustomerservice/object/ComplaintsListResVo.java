package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/8/2018.
 */

public class ComplaintsListResVo implements Serializable
{

    @SerializedName("complaint_list")
    @Expose
    public List<ComplaintsInsertReqVo> complaintList = null;
    private final static long serialVersionUID = 3494675703725477787L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("complaintList", complaintList).toString();
    }

}