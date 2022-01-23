package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 1/11/2019.
 */

public class NotificationResVo implements Serializable
{

    @SerializedName("notification_list")
    @Expose
    public List<NotificationList> notificationList = null;
    private final static long serialVersionUID = -3880538771826870571L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("notificationList", notificationList).toString();
    }
}
