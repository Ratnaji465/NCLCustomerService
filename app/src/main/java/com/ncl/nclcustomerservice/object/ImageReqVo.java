package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/23/2018.
 */

public class ImageReqVo implements Serializable {

    @SerializedName("requesterid")
    @Expose
    public String requesterid;
    @SerializedName("requestname")
    @Expose
    public String requestname;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requesterid", requesterid).append("requestname", requestname).toString();
    }

}
