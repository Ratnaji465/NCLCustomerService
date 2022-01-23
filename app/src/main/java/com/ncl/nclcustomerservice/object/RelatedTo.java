package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 10/4/2018.
 */

public class RelatedTo implements Serializable{
    @SerializedName("related_name")
    @Expose
    public String relatedName;
    @SerializedName("related_list")
    @Expose
    public List<RelatedList> relatedList = null;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("relatedName", relatedName).append("relatedList", relatedList).toString();
    }

}
