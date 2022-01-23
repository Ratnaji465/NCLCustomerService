package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by suprasoft on 10/1/2018.
 */

public class CompaintNameKey implements Serializable {
    @SerializedName("complaints_name")
    @Expose
    public String complaints_name;
@SerializedName("com_ass_rec_id")
    @Expose
    public String com_ass_rec_id;
@SerializedName("complement_ass_name")
    @Expose
    public String complement_ass_name;
}
