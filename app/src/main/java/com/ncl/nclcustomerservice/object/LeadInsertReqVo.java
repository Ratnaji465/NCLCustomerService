package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ncl.nclcustomerservice.typeconverter.LeadActionTOTC;
import com.ncl.nclcustomerservice.typeconverter.LeadAssociatedTC;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/1/2018.
 */
@Entity
public class LeadInsertReqVo implements Serializable {

    @PrimaryKey
    @NonNull
    @SerializedName("leads_id")
    @Expose
    public int leadsId;
    @SerializedName("lead_number")
    @Expose
    public String leadNumber;

    @SerializedName("Company")
    @Expose
    public String company;
    @SerializedName("Company_Text")
    @Expose
    public String companyText;
    @SerializedName("LeadSource")
    @Expose
    public String leadSource;
    @SerializedName("LeadOwner")
    @Expose
    public String LeadOwner;
    @SerializedName("created_date_time")
    @Expose
    public String date;
    @SerializedName("is_lead_converted")
    @Expose
    public String is_lead_converted="0";
    @SerializedName("lead_street1")
    @Expose
    public String leadStreet1;
    @SerializedName("lead_street2")
    @Expose
    public String leadStreet2;
    @SerializedName("lead_plotno")
    @Expose
    public String leadPlotno;
    @SerializedName("lead_area")
    @Expose
    public String leadArea;
    @SerializedName("lead_state")
    @Expose
    public String leadState;
    @SerializedName("lead_City")
    @Expose
    public String leadCity;
    @SerializedName("lead_pin_zip_code")
    @Expose
    public String leadPinZipCode;
    @SerializedName("lead_country")
    @Expose
    public String leadCountry;
    @SerializedName("lead_website")
    @Expose
    public String leadWebsite;
    @SerializedName("lead_email")
    @Expose
    public String leadEmail;
    @SerializedName("lead_phone")
    @Expose
    public String leadPhone;
    @SerializedName("lead_status")
    @Expose
    public String leadStatus;
    @SerializedName("lead_project_name")
    @Expose
    public String leadProjectName;
    @SerializedName("lead_project_type")
    @Expose
    public String leadProjectType;
    @SerializedName("lead_size_class_of_project")
    @Expose
    public String leadSizeClassOfProject;
    @SerializedName("size_calss_unit")
    @Expose
    public String size_calss_unit;
   @SerializedName("size_calss_unit_no_of_floor_per_block")
    @Expose
    public String size_calss_unit_no_of_floor_per_block;
   @SerializedName("no_of_flats")
    @Expose
    public String no_of_flats;
  @SerializedName("cubic_meters")
    @Expose
    public String cubic_meters;
  @SerializedName("sft")
    @Expose
    public String sft;
   @SerializedName("size_calss_unit_no_of_blocks")
    @Expose
    public String size_calss_unit_no_of_blocks;
   @SerializedName("lead_class_of_project")
    @Expose
    public String lead_class_of_project;
    @SerializedName("lead_project_status")
    @Expose
    public String leadProjectStatus;
    @SerializedName("lead_project_street1")
    @Expose
    public String leadProjectStreet1;
    @SerializedName("lead_project_street2")
    @Expose
    public String leadProjectStreet2;
    @SerializedName("lead_project_plot_no")
    @Expose
    public String leadProjectPlotNo;
    @SerializedName("lead_project_land_mark")
    @Expose
    public String leadProjectLandMark;
    @SerializedName("lead_project_city")
    @Expose
    public String leadProjectCity;
    @SerializedName("lead_project_state")
    @Expose
    public String leadProjectState;
    @SerializedName("lead_project_pin_zip_code")
    @Expose
    public String leadProjectPinZipCode;
    @SerializedName("lead_main_contact_designation")
    @Expose
    public String leadMainContactDesignation;
    @SerializedName("lead_main_contact_id")
    @Expose
    public String leadMainContactId;
    @SerializedName("lead_main_contact_name")
    @Expose
    public String leadMainContactName;
    @SerializedName("lead_main_contact_email")
    @Expose
    public String leadMainContactEmail;
    @SerializedName("lead_main_contact_mobile")
    @Expose
    public String leadMainContactMobile;
    @SerializedName("lead_main_contact_category")
    @Expose
    public String leadMainContactCategory;
    @SerializedName("lead_main_contact_phone")
    @Expose
    public String leadMainContactPhone;
    @SerializedName("lead_main_contact_company")
    @Expose
    public String leadMainContactCompany;
    @SerializedName("associate_contact")
    @Expose
    @TypeConverters(LeadAssociatedTC.class)
    public List<AssociateContactLead> associateContact;

    @SerializedName("action_work_done")
    @Expose
    @TypeConverters(LeadActionTOTC.class)
    public List<ActionWorkDone> actionWorkDone;

}
