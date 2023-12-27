package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncl.nclcustomerservice.typeconverter.ImagesListTC;
import com.ncl.nclcustomerservice.typeconverter.RemarksListTC;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by sowmy on 10/9/2018.
 */
@Entity
public class ComplaintsInsertReqVo implements Serializable {
    @SerializedName("requestname")
    @Expose
    public String requestName;
    @SerializedName("requesterid")
    @Expose
    public String requesterId;
    @SerializedName("cs_complaint_register_id")
    @Expose
    @PrimaryKey
    @NonNull
    public String csComplaintRegisterId;
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
    @SerializedName("project_type_name")
    @Expose
    public String projectTypeName;
    @SerializedName("complaint_registration_number")
    @Expose
    public String complaintRegistrationNumber;
    @SerializedName("other_Project_type")
    @Expose
    public String otherProjectType;
    @SerializedName("division_master_id")
    @Expose
    public String divisionMasterId;
    @SerializedName("division_master_name")
    @Expose
    public String divisionMastername;
    @SerializedName("marketing_officer_name")
    @Expose
    public String marketingOfficerName;
    @SerializedName("fab_unit_id")
    @Expose
    public String fabUnitId;
    @SerializedName("fab_unit_name")
    @Expose
    public String fabUnitName;
    @SerializedName("nature_of_complaint_id")
    @Expose
    public String natureOfComplaintId;
    @SerializedName("nature_of_complaint_name")
    public String natureOfComplaintName;
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
    @TypeConverters(RemarksListTC.class)
    public List<RemarksList> remarks;

    @SerializedName("supervisior_remarks")
    @Expose
    @TypeConverters(RemarksListTC.class)
    public List<RemarksList> supervisiorRemarks;
    @SerializedName("complaint_receiver_remarks")
    @Expose
    @TypeConverters(RemarksListTC.class)
    public List<RemarksList> complaintReceiverRemarks;
    @SerializedName("commercial_department_remarks")
    @Expose
    @TypeConverters(RemarksListTC.class)
    public List<RemarksList> commercialDepartmentRemarks;
    @SerializedName("final_remarks")
    @Expose
    @TypeConverters(RemarksListTC.class)
    public List<RemarksList> finalRemarks;

    @SerializedName("images")
    @Expose
    @TypeConverters(ImagesListTC.class)
    public List<ImagesList> imagesLists;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("requestName", requestName)
                .append("requesterId",requesterId).append("csComplaintRegisterId",csComplaintRegisterId)
                .append("profileId",profileId)
                .append("roleId",roleId).append("complaintDate",complaintDate)
                .append("clientCode",clientCode).append("oaNumber",oaNumber)
                .append("areaOffice",areaOffice).append("clientName",clientName)
                .append("csProjectTypeId",csProjectTypeId).append("projectTypeName",projectTypeName)
                .append("otherProjectType",otherProjectType)
                .append("complaintRegistrationNumber",complaintRegistrationNumber)
                .append("divisionMasterId",divisionMasterId).append("marketingOfficerName",marketingOfficerName)
                .append("fabUnitId",fabUnitId).append("fabUnitName",fabUnitName)
                .append("natureOfComplaintId",natureOfComplaintId)
                .append("otherNatureOfComplaint",otherNatureOfComplaint).append("closingDate",closingDate)
                .append("noDaysForResolve",noDaysForResolve).append("complaintStatus",complaintStatus)
                .append("remarks",remarks).append("natureOfComplaintName",natureOfComplaintName).toString();
    }

    public static class RemarksList implements Serializable{
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
