package com.ncl.nclcustomerservice.object;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

public class ComplaintRegisterMasterVo  implements Serializable {
    @SerializedName("cs_project_types")
    @Expose
    public List<ProjectTypeList> projectTypeLists = null;
    @SerializedName("division_master")
    @Expose
    public List<DivisionMasterList> divisionMasterLists=null;
    @SerializedName("cs_fab_unit")
    @Expose
    public List<FabUnitList> fabUnitLists=null;
    @SerializedName("cs_nature_of_complaint")
    @Expose
    public List<NatureOfComplaintList> natureOfComplaintLists=null;
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("projectTypeLists",projectTypeLists)
                .append("divisionMasterLists", divisionMasterLists)
                .append("fabUnitLists", fabUnitLists)
                .append("natureOfComplaintLists", natureOfComplaintLists).toString();
    }





}
