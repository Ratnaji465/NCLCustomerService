package com.ncl.nclcustomerservice.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.DailyReportsAddVO
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.databinding.ActivityViewDailyreportsBinding
import com.ncl.nclcustomerservice.databinding.ItemDescriptionWorkBinding
import java.io.Serializable

class ViewDailyReportsActivty : AppCompatActivity() {
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
        if(dailyReportsList!=null){
            binding.apply {
                tvRelatedTo.text=dailyReportsList.relatedTo
                tvContractor.text=dailyReportsList.contractorName
                tvMobileNo.text=dailyReportsList.contractorMobileNo
                tvCalltype.text=dailyReportsList.callType
                tvCallDate.text=dailyReportsList.callDate
                tvCalltime.text=dailyReportsList.callTime
                tvOANo.text=dailyReportsList.oaNumbers
                tvCheckIn.text=""+dailyReportsList.checkInTime
                tvCheckOut.text=""+dailyReportsList.checkoutTime
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
            }

        }
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
}