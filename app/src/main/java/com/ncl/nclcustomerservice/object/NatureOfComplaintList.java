package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Entity
public class NatureOfComplaintList  implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("nature_of_complaint_id")
    @Expose
    public String nature_of_complaint_id;
    @SerializedName("nature_of_complaint_name")
    @Expose
    public String nature_of_complaint_name;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("nature_of_complaint_id",nature_of_complaint_id)
                .append("nature_of_complaint_name", nature_of_complaint_name).toString();
    }
}
