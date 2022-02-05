package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class DailyReportsResListVO implements Serializable {
    @SerializedName("daily_report_info")
    @Expose
    public List<DailyReportsResVO> dailyReportsResVOList;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("dailyReportsResVOList", dailyReportsResVOList).toString();
    }

    public class DailyReportsResVO implements Serializable{
        @SerializedName("cs_dailyreport_id")
        @Expose
        public String csDailyreportId;
        @SerializedName("related_to")
        @Expose
        public String relatedTo;
        @SerializedName("contact_contractor_id")
        @Expose
        public String contactContractorId;
        @SerializedName("contractor_name")
        @Expose
        public String contractorName;
        @SerializedName("contractor_mobile_no")
        @Expose
        public String contractorMobileNo;
        @SerializedName("customer_project_id")
        @Expose
        public String customerProjectId;
        @SerializedName("project_name")
        @Expose
        public Object projectName;
        @SerializedName("project_head_name")
        @Expose
        public Object projectHeadName;
        @SerializedName("call_type")
        @Expose
        public String callType;
        @SerializedName("call_date")
        @Expose
        public String callDate;
        @SerializedName("call_time")
        @Expose
        public String callTime;
        @SerializedName("check_in_time")
        @Expose
        public Object checkInTime;
        @SerializedName("checkout_time")
        @Expose
        public Object checkoutTime;
        @SerializedName("cs_customerproject_clientproject_detailsid")
        @Expose
        public String csCustomerprojectClientprojectDetailsid;
        @SerializedName("oa_numbers")
        @Expose
        public String oaNumbers;
        @SerializedName("user_id")
        @Expose
        public String userId;
        @SerializedName("userName")
        @Expose
        public String userName;
        @SerializedName("description_of_works")
        @Expose
        public List<DescriptionOfWork> descriptionOfWorks = null;

        public class DescriptionOfWork implements Serializable{
            @SerializedName("cs_dailyreport_description_of_works_id")
            @Expose
            public String csDailyreportDescriptionOfWorksId;
            @SerializedName("cs_dailyreport_id")
            @Expose
            public String csDailyreportId;
            @SerializedName("description_of_works")
            @Expose
            public String descriptionOfWorks;
        }
    }
}
