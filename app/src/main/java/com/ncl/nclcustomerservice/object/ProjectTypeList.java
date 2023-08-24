package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

@Entity
public class ProjectTypeList  implements Serializable {
    @PrimaryKey
    @NonNull
    @SerializedName("cs_project_type_id")
    @Expose
    public String cs_project_type_id;
    @SerializedName("project_type_name")
    @Expose
    public String project_type_name;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("cs_project_type_id",cs_project_type_id)
                .append("project_type_name", project_type_name).toString();
    }
}
