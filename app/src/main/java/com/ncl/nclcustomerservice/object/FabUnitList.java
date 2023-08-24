package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Entity
public class FabUnitList  implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("fab_unit_id")
    @Expose
    public String fab_unit_id;
    @SerializedName("fab_unit_name")
    @Expose
    public String fab_unit_name;
    @SerializedName("email")
    @Expose
    public String email;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("fab_unit_id",fab_unit_id)
                .append("fab_unit_name", fab_unit_name)
                .append("email", email).toString();
    }
}
