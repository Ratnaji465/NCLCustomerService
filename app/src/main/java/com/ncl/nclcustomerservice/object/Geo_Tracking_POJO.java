package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by SupraSoft on 12/20/2018.
 */
@Entity
public class Geo_Tracking_POJO {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("installed_apps")
    @Expose
    public String installedApps;
    @SerializedName("latlon")
    @Expose
    public String latlon;
    @SerializedName("requestname")
    @Expose
    public String requestname;

    public int syncStatus;


    public String checkInLatLong;

    public String checkOutLatLong;

    @SerializedName("tracking_id")
    @Expose
    public String trackingId;
    @SerializedName("visit_type")
    @Expose
    public String visitType;
    @SerializedName("user_id")
    @Expose
    public String userId;
    @SerializedName("check_in_lat_lon")
    @Expose
    public String checkInLatLon;
    @SerializedName("check_out_lat_lon")
    @Expose
    public String checkOutLatLon;
    @SerializedName("route_path_lat_lon")
    @Expose
    public String routePathLatLon;
    @SerializedName("distance")
    @Expose
    public String distance;
    @SerializedName("visit_date")
    @Expose
    public String visitDate;
    @SerializedName("check_in_time")
    @Expose
    public String checkInTime;
    @SerializedName("check_out_time")
    @Expose
    public String checkOutTime;
    @SerializedName("created_datetime")
    @Expose
    public String createdDatetime;
    @SerializedName("updated_datetime")
    @Expose
    public String updatedDatetime;
    @SerializedName("status")
    @Expose
    public String status;
    @SerializedName("installed_app")
    @Expose
    public String installedApp;
    @SerializedName("route_snap")
    @Expose
    public String routeSnap;
    @SerializedName("google_direction")
    @Expose
    public String googleDirection;
    @SerializedName("gps_status")
    @Expose
    public String gpsStatus;
    @SerializedName("pause")
    @Expose
    public String pause;
    @SerializedName("resume")
    @Expose
    public String resume;
    @SerializedName("app_version")
    @Expose
    public String appVersion;
    @SerializedName("polyline")
    @Expose
    public String polyline;
    @SerializedName("route_snap_all")
    @Expose
    public String routeSnapAll;
    @SerializedName("route_snap_failure")
    @Expose
    public String routeSnapFailure;
    @SerializedName("google_direction_all")
    @Expose
    public String googleDirectionAll;
    @SerializedName("google_direction_failure")
    @Expose
    public String googleDirectionFailure;
    @SerializedName("check_in_place")
    @Expose
    public String checkInPlace;
    @SerializedName("check_out_place")
    @Expose
    public String checkOutPlace;
    @SerializedName("check_out_by")
    @Expose
    public String checkOutBy;
    @SerializedName("meter_reading_checkin_image")
    @Expose
    public String meterReadingCheckinImage;
    @SerializedName("meter_reading_checkin_text")
    @Expose
    public String meterReadingCheckinText;
    @SerializedName("meter_reading_checkout_image")
    @Expose
    public String meterReadingCheckoutImage;
    @SerializedName("meter_reading_checkout_text")
    @Expose
    public String meterReadingCheckoutText;
    @SerializedName("vehicle_type")
    @Expose
    public String vehicleType;
    @SerializedName("personal_uses_km")
    @Expose
    public String personalUsesKm;
    @SerializedName("checkin_comment")
    @Expose
    public String checkinComment;



    @Override
    public String toString() {
        return new ToStringBuilder(this).append("trackingId", trackingId).append("visitType", visitType).append("userId", userId).append("checkInLatLon", checkInLatLon).append("checkOutLatLon", checkOutLatLon).append("routePathLatLon", routePathLatLon).append("distance", distance).append("visitDate", visitDate).append("checkInTime", checkInTime).append("checkOutTime", checkOutTime).append("createdDatetime", createdDatetime).append("updatedDatetime", updatedDatetime).append("status", status).append("installedApp", installedApp).append("routeSnap", routeSnap).append("googleDirection", googleDirection).append("gpsStatus", gpsStatus).append("pause", pause).append("resume", resume).append("appVersion", appVersion).append("polyline", polyline).append("routeSnapAll", routeSnapAll).append("routeSnapFailure", routeSnapFailure).append("googleDirectionAll", googleDirectionAll).append("googleDirectionFailure", googleDirectionFailure).append("checkInPlace", checkInPlace).append("checkOutPlace", checkOutPlace).append("checkOutBy", checkOutBy).append("meterReadingCheckinImage", meterReadingCheckinImage).append("meterReadingCheckinText", meterReadingCheckinText).append("meterReadingCheckoutImage", meterReadingCheckoutImage).append("meterReadingCheckoutText", meterReadingCheckoutText).append("vehicleType", vehicleType).append("personalUsesKm", personalUsesKm).append("checkinComment", checkinComment).toString();
    }
}

