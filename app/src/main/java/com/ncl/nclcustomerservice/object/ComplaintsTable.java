package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/9/2018.
 */
@Entity
public class ComplaintsTable implements Serializable {

    @SerializedName("complaint_id")
    @Expose
    @PrimaryKey
    @NonNull
    public String complaintId;
    @SerializedName("customer_id")
    @Expose
    public int customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @SerializedName("salesorderdate")
    @Expose
    public String salesorderdate;
    @SerializedName("salesordernumber")
    @Expose
    public String salesordernumber;
    @SerializedName("feedback")
    @Expose
    public String feedback;
    @SerializedName("applicationdate")
    @Expose
    public String applicationdate;
    @SerializedName("feedbackother")
    @Expose
    public String feedbackother;
    @SerializedName("invoicedate")
    @Expose
    public String invoicedate;
    @SerializedName("invoicenumber")
    @Expose
    public String invoicenumber;
    @SerializedName("batchnumber")
    @Expose
    public String batchnumber;
    @SerializedName("defectivesample")
    @Expose
    public String defectivesample;
    @SerializedName("sampleplantlab")
    @Expose
    public String sampleplantlab;
    @SerializedName("sales_sitevisit")
    @Expose
    public String salesSitevisit;

    @SerializedName("sales_assessment")
    @Expose
    public String salesAssessment;

    @SerializedName("sales_recommendedsolution")
    @Expose
    public String salesRecommendedsolution;
    @SerializedName("area_sitevisit")
    @Expose
    public String areaSitevisit;
    @SerializedName("area_assessment")
    @Expose

    public String areaAssessment;
    @SerializedName("area_recommendedsolution")
    @Expose

    public String areaRecommendedsolution;
    @SerializedName("regional_sitevisit")
    @Expose
    public String regionalSitevisit;
    @SerializedName("regional_assessment")
    @Expose

    public String regionalAssessment;
    @SerializedName("regional_recommendedsolution")
    @Expose

    public String regionalRecommendedsolution;
    @SerializedName("national_sitevisit")
    @Expose
    public String nationalSitevisit;
    @SerializedName("national_assessment")
    @Expose

    public String nationalAssessment;
    @SerializedName("national_recommendedsolution")
    @Expose

    public String nationalRecommendedsolution;
    @SerializedName("qualitytestsdone")
    @Expose
    public String qualitytestsdone;
    @SerializedName("qualityassessment")
    @Expose

    public String qualityassessment;
    @SerializedName("qualityrecommendation")
    @Expose

    public String qualityrecommendation;
    @SerializedName("manufacturingassessment")
    @Expose

    public String manufacturingassessment;
    @SerializedName("managementassessment")
    @Expose

    public String managementassessment;
    @SerializedName("managementRecommendation")
    @Expose

    public String managementRecommendation;
    @SerializedName("credit_note_given")
    @Expose
    public String creditNoteGiven;
    @SerializedName("material_replaced")
    @Expose
    public String materialReplaced;
    @SerializedName("comercial_remarks")
    @Expose
    public String comercialRemarks;

    @SerializedName("sales_status")
    @Expose
    public String salesStatus;
    @SerializedName("area_status")
    @Expose
    public String areaStatus;
    @SerializedName("regional_status")
    @Expose
    public String regionalStatus;
    @SerializedName("national_status")
    @Expose
    public String nationalStatus;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("complaintId", complaintId).append("customerName", customerName).append("salesorderdate", salesorderdate).append("salesordernumber", salesordernumber).append("feedback", feedback).append("applicationdate", applicationdate).append("feedbackother", feedbackother).append("invoicedate", invoicedate).append("invoicenumber", invoicenumber).append("batchnumber", batchnumber).append("defectivesample", defectivesample).append("sampleplantlab", sampleplantlab).append("salesSitevisit", salesSitevisit).append("salesAssessment", salesAssessment).append("salesRecommendedsolution", salesRecommendedsolution).append("areaSitevisit", areaSitevisit).append("areaAssessment", areaAssessment).append("areaRecommendedsolution", areaRecommendedsolution).append("regionalSitevisit", regionalSitevisit).append("regionalAssessment", regionalAssessment).append("regionalRecommendedsolution", regionalRecommendedsolution).append("nationalSitevisit", nationalSitevisit).append("nationalAssessment", nationalAssessment).append("nationalRecommendedsolution", nationalRecommendedsolution).append("qualitytestsdone", qualitytestsdone).append("qualityassessment", qualityassessment).append("qualityrecommendation", qualityrecommendation).append("manufacturingassessment", manufacturingassessment).append("managementassessment", managementassessment).append("managementRecommendation", managementRecommendation).append("creditNoteGiven", creditNoteGiven).append("materialReplaced", materialReplaced).append("comercialRemarks", comercialRemarks).toString();
    }

}
