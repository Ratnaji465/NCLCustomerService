package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 8/28/2018.
 */

public class CheckInCheckOutReqVo implements Serializable
{

    @SerializedName("requesterid")
    @Expose
    public String requesterid;
    @SerializedName("requestname")
    @Expose
    public String requestname;
    @SerializedName("visit_date")
    @Expose
    public String visitDate;
    @SerializedName("check_in_time")
    @Expose
    public String checkInTime;
    @SerializedName("check_out_time")
    @Expose
    public String checkOutTime;
    @SerializedName("check_in_lat_lon")
    @Expose
    public String checkInLatLon;
    @SerializedName("check_out_lat_lon")
    @Expose
    public String checkOutLatLon;
    @SerializedName("user_description")
    @Expose
    public String userDescription;
    @SerializedName("purpose_id")
    @Expose
    public String purposeId;
    @SerializedName("dealer")
    @Expose
    public String dealer;
    @SerializedName("person")
    @Expose
    public String person;
    @SerializedName("contact_number")
    @Expose
    public String contactNumber;
    private final static long serialVersionUID = -8483833105517857160L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requesterid", requesterid).append("requestname", requestname).append("visitDate", visitDate).append("checkInTime", checkInTime).append("checkOutTime", checkOutTime).append("checkInLatLon", checkInLatLon).append("checkOutLatLon", checkOutLatLon).append("userDescription", userDescription).append("purposeId", purposeId).append("dealer", dealer).append("person", person).append("contactNumber", contactNumber).toString();
    }
}
