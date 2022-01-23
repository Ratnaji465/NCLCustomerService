package com.ncl.nclcustomerservice.checkinout;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by suprasoft on 9/10/2018.
 */

@Entity(foreignKeys = @ForeignKey(entity = EmpActivityPojo.class,
        parentColumns = "employeeActivityId",
        childColumns = "employeeActivityId",
        onDelete = ForeignKey.CASCADE),
        indices = {@Index(value = {"employeeActivityId"},
                unique = false)})
public class EmpActivityLogsPojo implements Serializable {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @SerializedName("sqlPkLog")
    @Expose
    public int sqlPkLog;

    @SerializedName("reference_key")
    @Expose
    public int reference_key;

    @SerializedName("employee_activity_log_id")
    @Expose
    public int employeeActivityLogId;
    @SerializedName("employee_activity_id")
    @Expose
    public int employeeActivityId;
    @SerializedName("visit_type")
    @Expose
    public int visitType;
    @SerializedName("customer_id")
    @Expose
    public int customerId;
    @SerializedName("date")
    @Expose
    public String date;
    @SerializedName("check_in_time")
    @Expose
    public String checkInTime;
    @SerializedName("check_in_lat_long")
    @Expose
    public String checkInLatLong;
    @SerializedName("check_out_time")
    @Expose
    public String checkOutTime;
    @SerializedName("check_out_lat_long")
    @Expose
    public String checkOutLatLong;
    @SerializedName("remark")
    @Expose
    public String remark;
    @SerializedName("contact_person")
    @Expose
    public String contactPerson;
    @SerializedName("signature")
    @Expose
    public String signature;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("created_by")
    @Expose
    public int createdBy;
    @SerializedName("modified_by")
    @Expose
    public int modifiedBy;
    @SerializedName("created_date_time")
    @Expose
    public String createdDateTime;
    @SerializedName("modified_date_time")
    @Expose
    public String modifiedDateTime;
    @Ignore
    @SerializedName("requesterid")
    @Expose
    public int requesterid;
    @Ignore
    @SerializedName("requestname")
    @Expose
    public String requestname;

    private final static long serialVersionUID = -5187936255060697544L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("employeeActivityLogId", employeeActivityLogId).append("employeeActivityId", employeeActivityId).append("customerId", customerId).append("date", date).append("checkInTime", checkInTime).append("checkInLatLong", checkInLatLong).append("checkOutTime", checkOutTime).append("checkOutLatLong", checkOutLatLong).append("remark", remark).append("contactPerson", contactPerson).append("signature", signature).append("status", status).append("createdBy", createdBy).append("modifiedBy", modifiedBy).append("createdDateTime", createdDateTime).append("modifiedDateTime", modifiedDateTime).toString();
    }
}
