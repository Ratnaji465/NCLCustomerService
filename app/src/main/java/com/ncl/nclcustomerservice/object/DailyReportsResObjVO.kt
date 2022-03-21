package com.ncl.nclcustomerservice.`object`

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import org.apache.commons.lang3.builder.ToStringBuilder
import java.io.Serializable

class DailyReportsResObjVO : Serializable {
    @JvmField
    @SerializedName("daily_report_info")
    @Expose
    var dailyReportsResObjVO: List<DailyReportsAddVO> ? = null
    override fun toString(): String {
        return ToStringBuilder(this)
                .append("dailyReportsResVOList", dailyReportsResObjVO).toString()
    }
}