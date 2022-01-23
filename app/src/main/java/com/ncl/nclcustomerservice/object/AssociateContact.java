package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 10/1/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = OpportunitiesList.class, parentColumns = "opportunityId", childColumns = "contacts", onDelete = ForeignKey.CASCADE))
public class AssociateContact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int primaryKey;
    @SerializedName("Contact_id")
    @Expose
    public String contactId;
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("designation")
    @Expose
    public String designation;
    @SerializedName("Mobile")
    @Expose
    public String Mobile;
    @SerializedName("Phone")
    @Expose
    public String Phone;
    @SerializedName("Email")
    @Expose
    public String Email;
    @SerializedName("Category")
    @Expose
    public String Category;
    @SerializedName("isAccountTagged")
    @Expose
    public String isAccountTagged;
    @SerializedName("ContactOwner")
    @Expose
    public String ContactOwner;
    @SerializedName("Customer_id")
    @Expose
    public String Customer_id;
    @SerializedName("Customer")
    @Expose
    public String Customer;

    public boolean aBoolean;

    public int contacts;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("contactId", contactId).append("name", name).toString();
    }

}
