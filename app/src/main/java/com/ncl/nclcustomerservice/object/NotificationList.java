package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 1/11/2019.
 */

public class NotificationList implements Serializable
{

    @SerializedName("notiffication_id")
    @Expose
    public String notifficationId;
    @SerializedName("notiffication_type")
    @Expose
    public String notifficationType;
    @SerializedName("notiffication_type_id")
    @Expose
    public String notifficationTypeId;
    @SerializedName("opportunity_id")
    @Expose
    public int opportunityId;
    @SerializedName("created_by")
    @Expose
    public String createdBy;
    @SerializedName("subject")
    @Expose
    public String subject;
    private final static long serialVersionUID = -6401020539574754295L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("notifficationId", notifficationId).append("notifficationType", notifficationType).append("notifficationTypeId", notifficationTypeId).append("opportunityId", opportunityId).append("createdBy", createdBy).append("subject", subject).toString();
    }
}
