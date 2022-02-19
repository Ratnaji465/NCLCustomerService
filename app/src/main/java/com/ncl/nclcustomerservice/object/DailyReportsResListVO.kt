package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import org.apache.commons.lang3.builder.ToStringBuilder
import java.io.Serializable

class DailyReportsResListVO : Serializable {
    @JvmField
    @SerializedName("daily_report_info")
    @Expose
    var dailyReportsResVOList: List<DailyReportsAddVO>? = null
    override fun toString(): String {
        return ToStringBuilder(this)
                .append("dailyReportsResVOList", dailyReportsResVOList).toString()
    }
}