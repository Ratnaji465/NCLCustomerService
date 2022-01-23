package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by SupraSoft on 10/1/2018.
 */
@Entity
public class Customer implements Serializable {
    @PrimaryKey(autoGenerate = true)
    public int id;
    @SerializedName("customer_id")
    @Expose
    public String customerId;
    @SerializedName("CustomerName")
    @Expose
    public String customerName;
    @Ignore
    @SerializedName("contact_list")
    @Expose
    public List<Contact> contactList = null;
    private final static long serialVersionUID = 4199633836429109028L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerId", customerId).append("customerName", customerName).append("contactList", contactList).toString();
    }

}
