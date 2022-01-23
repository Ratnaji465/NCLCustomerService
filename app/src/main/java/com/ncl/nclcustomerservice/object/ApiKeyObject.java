package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Created by suprasoft on 10/1/2018.
 */

public class ApiKeyObject {

    @SerializedName("api_key_id")
    @Expose
    public String apiKeyId;
    @SerializedName("api_name")
    @Expose
    public String apiName;
    @SerializedName("modified_by")
    @Expose
    public String modifiedBy;
    @SerializedName("modified_date_time")
    @Expose
    public String modifiedDateTime;
    @SerializedName("accuracy")
    @Expose
    public String accuracy;
    @SerializedName("direction_distance_setting")
    @Expose
    public String directionDistanceSetting;
    private final static long serialVersionUID = 2816969954115088343L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("apiKeyId", apiKeyId).append("apiName", apiName).append("modifiedBy", modifiedBy).append("modifiedDateTime", modifiedDateTime).toString();
    }


}
