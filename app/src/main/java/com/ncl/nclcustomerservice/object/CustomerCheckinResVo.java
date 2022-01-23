package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 9/10/2018.
 */

public class CustomerCheckinResVo implements Serializable
{

    @SerializedName("employee_activity_log_id")
    @Expose
    public int employeeActivityLogId;
    private final static long serialVersionUID = 8688219604261577717L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("employeeActivityLogId", employeeActivityLogId).toString();
    }

}
