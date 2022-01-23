package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProjectHeadVO implements Serializable {
    //    This is for add and edit projectHead
    @SerializedName("contact_list")
    @Expose
    public List<ProjectHeadReqVo> list=null;
}
