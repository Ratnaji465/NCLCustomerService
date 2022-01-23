package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import javax.annotation.Nonnull;

/**
 * Created by sowmy on 10/4/2018.
 */
@Entity
public class OpportunitiesList implements Serializable {
    @PrimaryKey
    @Nonnull
    @SerializedName("opportunity_id")
    @Expose
    public int opportunityId;
    @SerializedName("opp_id")
    @Expose
    public String oppId;
    @SerializedName("leads_id")
    @Expose
    public int leadsId;
    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("Company_Text")
    @Expose
    public String companyText;
    @SerializedName("Leadno")
    @Expose
    public String leadno;
    @SerializedName("sampling")
    @Expose
    public String sampling;
    @SerializedName("mockup")
    @Expose
    public String mockup;
    @SerializedName("Rating")
    @Expose
    public String rating;
    @SerializedName("project_name")
    @Expose
    public String projectName;
    @SerializedName("project_type")
    @Expose
    public String projectType;
    @SerializedName("size_class_project")
    @Expose
    public String sizeClassProject;
    @SerializedName("status_project")
    @Expose
    public String statusProject;
    @SerializedName("BillingStreet1")
    @Expose
    public String billingStreet1;
    @SerializedName("BillingStreet2")
    @Expose
    public String billingStreet2;
    @SerializedName("BillingCountry")
    @Expose
    public String billingCountry;
    @SerializedName("BillingState")
    @Expose
    public String billingState;
    @SerializedName("BillingCity")
    @Expose
    public String billingCity;
    @SerializedName("BillingZipPostal")
    @Expose
    public String billingZipPostal;
    @SerializedName("BillingArea")
    @Expose
    public String billingArea;
    @SerializedName("BillingWebsite")
    @Expose
    public String billingWebsite;
    @SerializedName("BillingEmail")
    @Expose
    public String billingEmail;
    @SerializedName("BillingPhone")
    @Expose
    public String billingPhone;
    @SerializedName("BillingPlotno")
    @Expose
    public String billingPlotno;
    @SerializedName("ShippingStreet1")
    @Expose
    public String shippingStreet1;
    @SerializedName("Shippingstreet2")
    @Expose
    public String shippingstreet2;
    @SerializedName("ShippingLandmark")
    @Expose
    public String shippingLandmark;
    @SerializedName("Shippingplotno")
    @Expose
    public String shippingplotno;
    @SerializedName("ShippingCity")
    @Expose
    public String shippingCity;
    @SerializedName("ShippingStateProvince")
    @Expose
    public String shippingStateProvince;
    @SerializedName("ShippingZipPostal")
    @Expose
    public String shippingZipPostal;
    @SerializedName("opportunity_main_contact_id")
    @Expose
    public String opportunityMainContactId;
    @SerializedName("opportunity_main_contact_name")
    @Expose
    public String opportunityMainContactName;
    @SerializedName("opportunity_main_contact_designation")
    @Expose
    public String opportunityMainContactDesignation;
    @SerializedName("opportunity_main_contact_email")
    @Expose
    public String opportunityMainContactEmail;
    @SerializedName("opportunity_main_contact_mobile")
    @Expose
    public String opportunityMainContactMobile;
    @SerializedName("opportunity_main_contact_category")
    @Expose
    public String opportunityMainContactCategory;
    @SerializedName("opportunity_main_contact_phone")
    @Expose
    public String opportunityMainContactPhone;
    @SerializedName("opportunity_main_contact_company")
    @Expose
    public String opportunityMainContactCompany;
    @SerializedName("brands_product")
    @Expose
    @Ignore
    public List<OpportunityBrandsLineItem> brandsProduct = null;
    @SerializedName("associate_contact")
    @Expose
    @Ignore
    public List<AssociateContactLead> associateContact = null;
    @Ignore
    @SerializedName("competition_product")
    @Expose
    public List<OpportunityCompetitionLineItem> competitionProduct = null;
    @Ignore
    @SerializedName("final_product")
    @Expose
    public List<OpportunityProductLineItem> finalProduct = null;
    @Ignore
    @SerializedName("stage")
    @Expose
    public String stage="";
    @SerializedName("created_date_time")
    @Expose
    public String created_date_time;
    @SerializedName("business_status")
    @Expose
    public String business_status;
    @SerializedName("business_status_delayed_value")
    @Expose
    public String business_status_delayed_value;
    @SerializedName("business_status_pending_value")
    @Expose
    public String business_status_pending_value;
    @SerializedName("business_status_lost_value")
    @Expose
    public String business_status_lost_value;
    @SerializedName("business_status_lost_other_value")
    @Expose
    public String business_status_lost_other_value;
    @SerializedName("lead_size_class_of_project")
    @Expose
    public String leadSizeClassOfProject;
    @SerializedName("size_calss_unit")
    @Expose
    public String size_calss_unit;
    @SerializedName("size_calss_unit_no_of_floor_per_block")
    @Expose
    public String size_calss_unit_no_of_floor_per_block;
    @SerializedName("size_calss_unit_no_of_blocks")
    @Expose
    public String size_calss_unit_no_of_blocks;
    @SerializedName("lead_class_of_project")
    @Expose
    public String lead_class_of_project;
    @SerializedName("no_of_flats")
    @Expose
    public String no_of_flats;
    @SerializedName("cubic_meters")
    @Expose
    public String cubic_meters;
    @SerializedName("sft")
    @Expose
    public String sft;
    @SerializedName("requirement_details_collected")
    @Expose
    public String requirement_details_collected;
    @SerializedName("remarks")
    @Expose
    public String remarks;
    @SerializedName("Finalizationdate")
    @Expose
    public String finalizationdate;
    @SerializedName("OwnerName")
    @Expose
    public String owner_name;
}
