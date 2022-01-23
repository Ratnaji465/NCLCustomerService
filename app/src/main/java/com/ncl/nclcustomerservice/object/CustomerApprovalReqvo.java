package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by User on 12/4/2018.
 */

public class CustomerApprovalReqvo implements Serializable {
    @SerializedName("customer_id")
    @Expose
    public int customer_id;
    @SerializedName("approve_status")
    @Expose
    public String approve_status;
    @SerializedName("approval_comments")
    @Expose
    public String approval_comments;

    @Override
    public String toString() {
        return "CustomerApprovalReqvo{" +
                "customer_id=" + customer_id +
                ", approve_status=" + approve_status +
                ", approval_comments='" + approval_comments + '\'' +
                '}';
    }
}
