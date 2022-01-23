package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 12/4/2018.
 */

public class ApprovalReqvo implements Serializable {
    @SerializedName("logged_user_role_id")
    @Expose
    public int loggedUserRoleId;
    @SerializedName("salesorder_id")
    @Expose
    public int salesorderId;
    @SerializedName("contract_id")
    @Expose
    public int contractId;
    @SerializedName("quatation_id")
    @Expose
    public int quatationId;
    @SerializedName("comm")
    @Expose
    public String comm;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("team_id")
    @Expose
    public String teamId;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("loggedUserRoleId", loggedUserRoleId).append("salesorderId", salesorderId).append("contractId", contractId).append("quatationId", quatationId).append("comm", comm).append("type", type).append("teamId", teamId).toString();
    }
}
