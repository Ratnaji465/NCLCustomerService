package com.ncl.nclcustomerservice.checkinout;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;


/**
 * Created by suprasoft on 9/10/2018.
 */
@Entity(indices = {@Index(value = {"employeeActivityId"},
        unique = true)})
public class EmpActivityPojo implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @SerializedName("sqlPK")
    @Expose
    @NonNull
    public int sqlPk;

    @SerializedName("employee_activity_id")
    @Expose
    public int employeeActivityId;
    @SerializedName("employee_id")
    @Expose
    public int employeeId;
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
    @SerializedName("route_path")
    @Expose
    public String routePath;
    @SerializedName("distance")
    @Expose
    public String distance;
    @SerializedName("installed_app")
    @Expose
    public String installedApp;
    @SerializedName("app_version")
    @Expose
    public String appVersion;
    @SerializedName("polyline")
    @Expose
    public String polyline;
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
    @SerializedName("type")
    @Expose
    @Ignore
    public String type;
    @SerializedName("gps_status")
    @Expose
    public String gpsStatus;
    @SerializedName("route_snap")
    @Expose
    @Ignore
    public String routeSnap;
    @SerializedName("google_direction")
    @Expose
    @Ignore
    public String googleDirection;

    @SerializedName("customer_activity")
    @Expose
    @Ignore
    public List<EmpActivityLogsPojo> empActivityLogsPojo;

    private final static long serialVersionUID = 6883631065992796224L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("employeeActivityId", employeeActivityId).append("employeeId", employeeId).append("date", date).append("checkInTime", checkInTime).append("checkInLatLong", checkInLatLong).append("checkOutTime", checkOutTime).append("checkOutLatLong", checkOutLatLong).append("routePath", routePath).append("distance", distance).append("installedApp", installedApp).append("appVersion", appVersion).append("polyline", polyline).append("status", status).append("createdBy", createdBy).append("modifiedBy", modifiedBy).append("createdDateTime", createdDateTime).append("modifiedDateTime", modifiedDateTime).toString();

    }


}
