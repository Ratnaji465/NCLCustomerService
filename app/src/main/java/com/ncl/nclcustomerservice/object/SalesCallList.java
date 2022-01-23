package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import javax.annotation.Nonnull;

/**
 * Created by User on 10/4/2018.
 */

@Entity
public class SalesCallList implements Serializable {
    @PrimaryKey
    @Nonnull
    @SerializedName("sales_call_id")
    @Expose
    public int salesCallId;
    @SerializedName("Subject")
    @Expose
    public String subject;
    @SerializedName("releted_to")
    @Expose
    public String reletedTo;
    @SerializedName("releted_name")
    @Expose
    public String reletedName;
    @SerializedName("related_to_id")
    @Expose
    public String relatedToId;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_name")
    @Expose
    public String contactName;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("Call_Type")
    @Expose
    public String callType;
    @SerializedName("call_report")
    @Expose
    public String call_report;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("Call_Date")
    @Expose
    public String callDate;
    @SerializedName("Call_Time")
    @Expose
    public String callTime;
    @SerializedName("Assigned_To")
    @Expose
    public String assignedTo;
    @SerializedName("Assigned_To_id")
    @Expose
    public String assignedToId;
    @SerializedName("Email")
    @Expose
    public String email;
    @SerializedName("Phone")
    @Expose
    public String phone;
    @SerializedName("Comments")
    @Expose
    public String comments;
    @SerializedName("NextVisitDate")
    @Expose
    public String nextVisitDate;
    @SerializedName("Priority")
    @Expose
    public String priority;
    @SerializedName("MinutesOfMeeting")
    @Expose
    public String minutesOfMeeting;
    @SerializedName("CommentsByManager")
    @Expose
    public String commentsByManager;
    @SerializedName("Owner")
    @Expose
    public String owner;
    @SerializedName("Owner_name")
    @Expose
    public String ownerName;
    @SerializedName("lat_lon_val")
    @Expose
    public String lat_lon_val;
    @SerializedName("tracking_id")
    @Expose
    public String tracking_id;
    @SerializedName("geo_status")
    @Expose
    public String geo_status;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("releted_to_new_contact_customer")
    @Expose
    public String releted_to_new_contact_customer;
    @SerializedName("new_contact_customer_person_name")
    @Expose
    public String new_contact_customer_person_name;
    @SerializedName("new_contact_customer_company_name")
    @Expose
    public String new_contact_customer_company_name;
    @SerializedName("new_contact_customer_other_person_name")
    @Expose
    public String new_contact_customer_other_person_name;
    @SerializedName("sales_calls_temp_id")
    @Expose
    public String sales_calls_temp_id;
    @SerializedName("created_date_time")
    @Expose
    public String created_date_time;
    @SerializedName("sales_call_customer_contact_type")
    @Expose
    public String sales_call_customer_contact_type;
    @SerializedName("check_in_time")
    @Expose
    public String checkin_time;
    @SerializedName("check_out_time")
    @Expose
    public String checkout_time;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallId", salesCallId).append("subject", subject).append("reletedTo", reletedTo).append("reletedName", reletedName).append("relatedToId", relatedToId).append("contactId", contactId).append("contactName", contactName).append("status", status).append("callType", callType).append("description", description).append("callDate", callDate).append("assignedTo", assignedTo).append("email", email).append("phone", phone).append("comments", comments).append("nextVisitDate", nextVisitDate).append("priority", priority).append("minutesOfMeeting", minutesOfMeeting).append("commentsByManager", commentsByManager).append("owner", owner).toString();
    }
}
