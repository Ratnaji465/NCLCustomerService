package com.ncl.nclcustomerservice.object;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * Created by sowmy on 10/5/2018.
 */
@Entity
public class PriceList implements Serializable {
    @PrimaryKey(autoGenerate = true)
    int id;
    @SerializedName("price")
    @Expose
    public String price;
    private final static long serialVersionUID = -3859292396408112346L;

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("price", price).toString();
    }
}
