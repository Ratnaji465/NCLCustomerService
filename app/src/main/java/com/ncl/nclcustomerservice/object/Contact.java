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
@Entity(foreignKeys = @ForeignKey(entity = Customer.class, parentColumns = "id", childColumns = "mobileSerivceDetailsId", onDelete = ForeignKey.CASCADE))
public class Contact implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("contact_id")
    @Expose
    public String contactId;
    @SerializedName("contact_name")
    @Expose
    public String contactName;
    @SerializedName("mobileSerivceDetailsId")
    @Expose
    public int mobileSerivceDetailsId;
    private final static long serialVersionUID = 8088439035303583578L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("contactId", contactId).append("contactName", contactName).toString();
    }
}
