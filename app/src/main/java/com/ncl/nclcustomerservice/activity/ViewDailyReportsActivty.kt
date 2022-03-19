package com.ncl.nclcustomerservice.activity

import CheckInCheckOutVO
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.ApiRequestController
import com.ncl.nclcustomerservice.`object`.ApiResponseController
import com.ncl.nclcustomerservice.`object`.DailyReportsAddVO
import com.ncl.nclcustomerservice.`object`.DailyReportsResObjVO
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.databinding.ActivityViewDailyreportsBinding
import com.ncl.nclcustomerservice.databinding.ItemDescriptionWorkBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ViewDailyReportsActivty : AppCompatActivity(), RetrofitResponseListener {
private lateinit var binding:ActivityViewDailyreportsBinding
private lateinit var dailyReportsList:DailyReportsAddVO
    private var nextTag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_dailyreports)
        binding.toolbar.titleText.setText("View Daily Report")
        binding.toolbar.backButton.setOnClickListener {
            finish()
        }
        getArguments<Args>()?.dailyReportsAddVO?.let {
            dailyReportsList = it
        }
        binding.apply {
            tvRelatedTo.text=dailyReportsList.relatedTo
            tvContractor.text=dailyReportsList.contractorName
            tvMobileNo.text=dailyReportsList.contractorMobileNo
            tvCalltype.text=dailyReportsList.callType
            tvOANo.text=dailyReportsList.oaNumbers
            if(dailyReportsList.checkInTime==null){
                tvCheckIn.text="--"
            }else{
                tvCheckIn.text=""+dailyReportsList.checkInTime
            }
            if(dailyReportsList.checkoutTime==null){
                tvCheckOut.text="--"
            }else{
                tvCheckOut.text=""+dailyReportsList.checkoutTime
            }
            if(dailyReportsList.callStatus==0){
                btnCheckIn.visibility=View.VISIBLE
                btnCheckOut.visibility=View.GONE
            }else if(dailyReportsList.callStatus==1){
                btnCheckIn.visibility=View.GONE
                btnCheckOut.visibility=View.VISIBLE
            }else if(dailyReportsList.callStatus==2){
                btnCheckIn.visibility=View.GONE
                btnCheckOut.visibility=View.GONE
            }
            if(dailyReportsList.descriptionOfWorks!=null && dailyReportsList.descriptionOfWorks!!.size>0){
                for(i in 0..dailyReportsList.descriptionOfWorks!!.size-1){
                    addDesWork(dailyReportsList.descriptionOfWorks!!.get(i))
                }
            }
            btnEdit.setOnClickListener {
                CreateDailyReportsActivity.open(
                        this@ViewDailyReportsActivty,
                        CreateDailyReportsActivity.Args(dailyReportsList,true)
                )
            }
            btnCheckIn.setOnClickListener {
                val currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
                Log.d("localTime", parseDateToddMMyyyy(currentDateTimeString).toString())
                var checkInCheckOutVO=CheckInCheckOutVO().apply {
                    csDailyreportId=dailyReportsList.csDailyreportId
                    type="checkin"
                    datetime=parseDateToddMMyyyy(currentDateTimeString)
                }
                RetrofitRequestController(this@ViewDailyReportsActivty).sendRequest(
                        Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT,
                        checkInCheckOutVO,
                        true
                )
            }
            btnCheckOut.setOnClickListener {
                val currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
                Log.d("localTime", parseDateToddMMyyyy(currentDateTimeString).toString())
                var checkInCheckOutVO=CheckInCheckOutVO().apply {
                    csDailyreportId=dailyReportsList.csDailyreportId
                    type="checkout"
                    datetime=parseDateToddMMyyyy(currentDateTimeString)
                }
                RetrofitRequestController(this@ViewDailyReportsActivty).sendRequest(
                        Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT,
                        checkInCheckOutVO,
                        true
                )
            }
        }
    }
    fun parseDateToddMMyyyy(time: String?): String? {
//        "6 Feb 2022 15:06:10"
//        2022-01-17 20:10:10
        val inputPattern = "dd MMM yyyy HH:mm:ss"
        val outputPattern = "yyyy-MM-dd HH:mm:ss"
        val inputFormat = SimpleDateFormat(inputPattern)
        val outputFormat = SimpleDateFormat(outputPattern)
        var date: Date? = null
        var str: String? = null
        try {
            date = inputFormat.parse(time)
            str = outputFormat.format(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return str
    }
    private fun addDesWork(obj: DailyReportsAddVO.DescriptionOfWork) {
        binding.apply {
            var view: ItemDescriptionWorkBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(this@ViewDailyReportsActivty),
                    R.layout.item_description_work,
                    null,
                    false
            )
            view.etText.setText(obj.descriptionOfWorks ?: "")
            view.etText.isEnabled=false
            view.root.setTag(nextTag)
            nextTag++
            view.btnRemove.setOnClickListener {
                var requiredTag = view.root.getTag() as Int
                for (i in 0..llDescriptionWork.childCount) {
                    var attachedTag = llDescriptionWork.getChildAt(i).getTag() as Int
                    if (requiredTag == attachedTag) {
                        llDescriptionWork.removeViewAt(i)
                        break
                    }
                }
            }
            llDescriptionWork.addView(view.root)
            view.root.layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    data class Args(var dailyReportsAddVO: DailyReportsAddVO) : Serializable
    companion object{
        @JvmStatic
        fun open(context: Context, dailyReportsAddVO: DailyReportsAddVO) {
            context.startActivity(Intent(context, ViewDailyReportsActivty::class.java).apply {
                putExtra("args", Args(dailyReportsAddVO))
            })
        }
    }

    override fun onResponseSuccess(objectResponse: ApiResponseController?, objectRequest: ApiRequestController?, progressDialog: ProgressDialog?) {
        try {
            when (objectResponse?.requestname) {
                Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT-> {

                    val dailyReportsAddVO: DailyReportsResObjVO =
                            Common.getSpecificDataObject(
                                    objectResponse.result,
                                    DailyReportsResObjVO::class.java
                            )
                    val callStatus=dailyReportsAddVO.dailyReportsResObjVO?.callStatus
                    binding.apply {
                        if(callStatus==0){
                            btnCheckIn.visibility=View.VISIBLE
                            btnCheckOut.visibility=View.GONE
                        }else if(callStatus==1){
                            btnCheckIn.visibility=View.GONE
                            btnCheckOut.visibility=View.VISIBLE
                        }else if(callStatus==2){
                            btnCheckIn.visibility=View.GONE
                            btnCheckOut.visibility=View.GONE
                        }

                        if(dailyReportsAddVO.dailyReportsResObjVO?.checkInTime==null){
                            tvCheckIn.text="--"
                        }else{
                            tvCheckIn.text=dailyReportsAddVO.dailyReportsResObjVO?.checkInTime
                        }
                        if(dailyReportsAddVO.dailyReportsResObjVO?.checkoutTime==null){
                            tvCheckOut.text="--"
                        }else{
                            tvCheckOut.text=dailyReportsAddVO.dailyReportsResObjVO?.checkoutTime
                        }
                    }

                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }
}