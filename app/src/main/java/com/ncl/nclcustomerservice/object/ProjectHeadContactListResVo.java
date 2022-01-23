package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProjectHeadContactListResVo implements Serializable {
//   This is for ProjectHead List
    @SerializedName("contact_list")
    @Expose
    public ProjectHeadListResVo projectHeadListResVo=null;


    public static class ProjectHeadListResVo implements Serializable{
        @SerializedName("ProjectHeads")
        @Expose
        public List<ProjectHeadReqVo> projectHeadReqVoList=null;
    }
}
