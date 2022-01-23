package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 10/4/2018.
 */

public class SalesCallInsert implements Serializable {

    @SerializedName("sales_call_id")
    @Expose
    public int salesCallId;
    @SerializedName("Subject")
    @Expose
    public String subject;
    @SerializedName("releted_to")
    @Expose
    public String reletedTo;
    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("Status")
    @Expose
    public String status;
    @SerializedName("contacts_id")
    @Expose
    public String contact_id;
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
    @SerializedName("Company")
    @Expose
    public String company;
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
    @SerializedName("sales_call_customer_contact_type")
    @Expose
    public String sales_call_customer_contact_type;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("salesCallId", salesCallId).append("subject", subject).append("reletedTo", reletedTo).append("id", id).append("status", status).append("callType", callType).append("description", description).append("callDate", callDate).append("assignedTo", assignedTo).append("email", email).append("phone", phone).append("comments", comments).append("nextVisitDate", nextVisitDate).append("priority", priority).append("minutesOfMeeting", minutesOfMeeting).append("commentsByManager", commentsByManager).append("contact_id", contact_id).toString();
    }

    public static class CallReport implements Serializable {
        @SerializedName("report")
        @Expose
        public String report;

        public CallReport(String report) {
            this.report = report;
        }
    }
}
