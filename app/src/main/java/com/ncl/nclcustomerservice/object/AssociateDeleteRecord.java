package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class AssociateDeleteRecord implements Serializable {

    @SerializedName("tbl")
    @Expose
    public String tbl;
    @SerializedName("field")
    @Expose
    public String field;
    @SerializedName("val")
    @Expose
    public String val;
    @Override
    public String toString() {
        return new ToStringBuilder(this).append("tbl", tbl).append("field",field).append("val",val).toString();
    }

}
