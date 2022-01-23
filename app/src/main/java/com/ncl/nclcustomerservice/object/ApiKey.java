package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suprasoft on 10/1/2018.
 */

public class ApiKey {
    @SerializedName("api_key")
    @Expose
    public List<ApiKeyObject> list=new ArrayList();
}
