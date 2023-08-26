package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Ignore;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/9/2018.
 */

public class ComplaintsInsertReqVo implements Serializable {
    @SerializedName("requestname")
    @Expose
    public String requestName;
    @SerializedName("requesterid")
    @Expose
    public String requesterId;
    @SerializedName("profile_id")
    @Expose
    public String profileId;
    @SerializedName("role_id")
    @Expose
    public String roleId;
    @SerializedName("complaint_date")
    @Expose
    public String complaintDate;
    @SerializedName("client_code")
    @Expose
    public String clientCode;
    @SerializedName("oa_number")
    @Expose
    public String oaNumber;
    @SerializedName("area_office")
    @Expose
    public String areaOffice;
    @SerializedName("client_name")
    @Expose
    public String clientName;
    @SerializedName("cs_project_type_id")
    @Expose
    public String csProjectTypeId;
    @SerializedName("other_Project_type")
    @Expose
    public String otherProjectType;
    @SerializedName("division_master_id")
    @Expose
    public String divisionMasterId;
    @SerializedName("marketing_officer_name")
    @Expose
    public String marketingOfficerName;
    @SerializedName("fab_unit_id")
    @Expose
    public String fabUnitId;
    @SerializedName("nature_of_complaint_id")
    @Expose
    public String natureOfComplaintId;
    @SerializedName("other_nature_of_complaint")
    @Expose
    public String otherNatureOfComplaint;
    @SerializedName("closing_date")
    @Expose
    public String closingDate;
    @SerializedName("no_days_for_resolve")
    @Expose
    public String noDaysForResolve;
    @SerializedName("complaint_status")
    @Expose
    public String complaintStatus;
    @SerializedName("remarks")
    @Expose
    public List<RemarksList> remarks;

    @SerializedName("supervisior_remarks")
    @Expose
    public List<RemarksList> supervisiorRemarks;
    @SerializedName("complaint_receiver_remarks")
    @Expose
    public List<RemarksList> complaintReceiverRemarks;
    @SerializedName("commercial_department_remarks")
    @Expose
    public List<RemarksList> commercialDepartmentRemarks;
    @SerializedName("final_remarks")
    @Expose
    public List<RemarksList> finalRemarks;

    @SerializedName("images")
    @Expose
    public List<ImagesList> imagesLists;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestName", requestName)
                .append("requesterId",requesterId).append("profileId",profileId)
                .append("roleId",roleId).append("complaintDate",complaintDate)
                .append("clientCode",clientCode).append("oaNumber",oaNumber)
                .append("areaOffice",areaOffice).append("clientName",clientName)
                .append("csProjectTypeId",csProjectTypeId).append("otherProjectType",otherProjectType)
                .append("divisionMasterId",divisionMasterId).append("marketingOfficerName",marketingOfficerName)
                .append("fabUnitId",fabUnitId).append("natureOfComplaintId",natureOfComplaintId)
                .append("otherNatureOfComplaint",otherNatureOfComplaint).append("closingDate",closingDate)
                .append("noDaysForResolve",noDaysForResolve).append("complaintStatus",complaintStatus)
                .append("remarks",remarks).toString();
    }

    public class RemarksList implements Serializable{
        @SerializedName("date")
        @Expose
        public String date;
        @SerializedName("remarks_val")
        @Expose
        public String remarksVal;
        @Override
        public String toString() {
            return new ToStringBuilder(this).append("date", date)
                    .append("remarksVal",remarksVal).toString();}
    }

    public class ImagesList implements Serializable{
        @SerializedName("image_path")
        @Expose
        public String imagePath;
        @Override
        public String toString() {
            return new ToStringBuilder(this).append("imagePath", imagePath).toString();}
    }
}
