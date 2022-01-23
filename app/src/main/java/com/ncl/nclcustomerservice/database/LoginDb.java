package com.ncl.nclcustomerservice.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by User on 8/23/2018.
 */
@Entity
public class LoginDb implements Serializable {

    @ColumnInfo(name = "password")
    @SerializedName("password")
    @Expose
    public String password;
    @PrimaryKey
    @ColumnInfo(name = "user_id")
    @SerializedName("user_id")
    @Expose
    @NonNull
    public int userid;
    @ColumnInfo(name = "user_name")
    @SerializedName("user_name")
    @Expose
    public String username;
    @ColumnInfo(name = "email_id")
    @SerializedName("email_id")
    @Expose
    public String emailid;
    @ColumnInfo(name = "mobile_number")
    @SerializedName("mobile_number")
    @Expose
    public String mobilenumber;
    @ColumnInfo(name = "user_type")
    @SerializedName("user_type")
    @Expose
    public String usertype;
    @ColumnInfo(name = "profile_image")
    @SerializedName("profile_img")
    @Expose
    public String profileimg;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("password", password).append("userId", userid).append("userName", username).append("emailId", emailid).append("mobileNumber", mobilenumber).append("userType", usertype).append("profileImg", profileimg).toString();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailid() {
        return emailid;
    }

    public void setEmailid(String emailid) {
        this.emailid = emailid;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getUsertype() {
        return usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    public String getProfileimg() {
        return profileimg;
    }

    public void setProfileimg(String profileimg) {
        this.profileimg = profileimg;
    }


}
