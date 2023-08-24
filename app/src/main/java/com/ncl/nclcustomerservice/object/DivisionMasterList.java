package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Entity
public class DivisionMasterList  implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("division_master_id")
    @Expose
    public String division_master_id;
    @SerializedName("division_name")
    @Expose
    public String division_name;
    @SerializedName("division_sap_code")
    @Expose
    public String division_sap_code;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("division_master_id",division_master_id)
                .append("division_name", division_name)
                .append("division_sap_code", division_sap_code).toString();
    }
}
