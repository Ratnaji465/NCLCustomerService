package com.ncl.nclcustomerservice.object;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by User on 8/17/2018.
 */

public class LoginResVo implements Serializable
{

    @SerializedName("user_id")
    @Expose
    public int userId;
    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("phone")
    @Expose
    public String phone;
    @SerializedName("email")
    @Expose
    public String email;
    @SerializedName("profile_img")
    @Expose
    public String profileImg;
    @SerializedName("profile_id")
    @Expose
    public String profileId;
    @SerializedName("profile_name")
    @Expose
    public String profileName;
    @SerializedName("role_id")
    @Expose
    public int roleId;
    @SerializedName("role_name")
    @Expose
    public String roleName;
    @SerializedName("department_id")
    @Expose
    public String departmentId;
    @SerializedName("department_name")
    @Expose
    public String departmentName;
    @SerializedName("price_list_id")
    @Expose
    public String price_list_id;
    @SerializedName("price_list_area")
    @Expose
    public String price_list_area;
    @SerializedName("left_nav")
    @Expose
    public List<LeftNav> leftNav = null;
    @SerializedName("users_team")
    @Expose
    public List<UsersTeam> usersTeam = null;
    @SerializedName("divisions")
    @Expose
    public List<DivisionList> divisions = null;
    private final static long serialVersionUID = 6567779910123687661L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("userId", userId).append("name", name).append("phone", phone).append("email", email).append("profileImg", profileImg).append("profileId", profileId).append("profileName", profileName).append("roleId", roleId).append("roleName", roleName).append("departmentId", departmentId).append("departmentName", departmentName).append("leftNav", leftNav).toString();
    }

}
