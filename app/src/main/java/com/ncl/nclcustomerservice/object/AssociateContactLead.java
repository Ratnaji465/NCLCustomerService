package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;


public class AssociateContactLead implements Serializable {
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("associate_contact_designation")
    @Expose
    public String associateContactDesignation;
    @SerializedName("associate_contact_mobile")
    @Expose
    public String associateContactMobile;
    @SerializedName("associate_contact_category")
    @Expose
    public String associateContactCategory;
    @SerializedName("assoc_contact_company")
    @Expose
    public String associateContactCompany;
    @SerializedName("FirstName")
    @Expose
    public String firstName=null;
    @SerializedName("LastName")
    @Expose
    public String lastName=null;
    @SerializedName("lead_associate_contact_id")
    @Expose
    public String leadAssociateContactId;
}
