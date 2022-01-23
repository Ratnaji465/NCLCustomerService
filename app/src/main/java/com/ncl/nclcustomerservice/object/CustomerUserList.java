package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by SupraSoft on 11/19/2018.
 */
@Entity(foreignKeys = @ForeignKey(entity = CustomerList.class, parentColumns = "customerId", childColumns = "lineitemid", onDelete = ForeignKey.CASCADE))
public class CustomerUserList implements Serializable {


    @PrimaryKey(autoGenerate = true)
    @NonNull
    public int lineitemPrimarykey;

    @SerializedName("customer_user_id")
    @Expose
    public String customerUserId;
    @SerializedName("user_name")
    @Expose
    public String userName;
    public int lineitemid;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("customerUserId", customerUserId).append("userName", userName).toString();
    }
}
