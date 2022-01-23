package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by suprasoft on 10/1/2018.
 */

public class AssigmentRecommendObj {
    @SerializedName("assigment_recommend_tbl_id")
    @Expose
    public String assigment_recommend_tbl_id;
    @SerializedName("complaints_name")
    @Expose
    public String complaints_name;
    @SerializedName("s")
    @Expose
    public String s;


}
