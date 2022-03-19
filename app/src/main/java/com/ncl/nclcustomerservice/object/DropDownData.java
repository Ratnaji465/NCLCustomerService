package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by SupraSoft on 10/1/2018.
 */

public class DropDownData {
    @SerializedName("divisions_list")
    @Expose
    public List<DivisionList> divisionList = null;
    @SerializedName("states_list")
    @Expose
    public List<StatesList> statesList = null;
    @SerializedName("department_list")
    @Expose
    public List<DepartmentList> departmentLists = null;
    @SerializedName("company_list")
    @Expose
    public List<CompanyList> companyLists=null;


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("divisionList", divisionList)
                .append("statesList", statesList)
                .append("departmentlist",departmentLists).toString();
    }
}
