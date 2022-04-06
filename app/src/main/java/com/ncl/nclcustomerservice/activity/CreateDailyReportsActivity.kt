package com.ncl.nclcustomerservice.activity

import CheckInCheckOutVO
import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.gson.Gson
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.`object`.DailyReportsAddVO.CustomerprojectClientprojectDetails
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.commonutils.getArguments
import com.ncl.nclcustomerservice.commonutils.toast
import com.ncl.nclcustomerservice.customviews.CustomEditText
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.ActivityCreateDailyreportsBinding
import com.ncl.nclcustomerservice.databinding.ItemDescriptionWorkBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.io.Serializable
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class CreateDailyReportsActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    override fun onInternetConnected() {
    }

    override fun onInternetDisconnected() {
    }
    private lateinit var dailyReport:DailyReportsAddVO
    var isEdit = false
    private var nextTag = 0
    private lateinit var binding: ActivityCreateDailyreportsBinding
    private lateinit var db: DatabaseHandler
    private lateinit var customerProjectList: List<CustomerProjectResVO>
    private lateinit var contractorContactList: List<CustomerContactResponseVo.ContactContractorList>
    lateinit var selectedCCId: String
    lateinit var selectedCPId: String
    private var selectedSubseries: MutableList<String> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_create_dailyreports)
        db = DatabaseHandler.getDatabase(this)
        customerProjectList = db.commonDao().getAllCustomerProjectList()
        contractorContactList = db.commonDao().getAllCustomerContactList()
        binding.apply {
            toolbar.titleText.setText("Daily Report")
            toolbar.backButton.setOnClickListener {
                finish()
            }
            tvProjectName.text=Common.setSppanableText("* Project Name")
            tvContactName.text=Common.setSppanableText("* Contact Name")
            tvMobileNo.text=Common.setSppanableText("* Mobile No")
            tvCallType.text = Common.setSppanableText("* Call Type")
            tvClientProject.text = Common.setSppanableText("* Client Project")
            tvProjectHeadName.text = Common.setSppanableText("* Project Head Name")

            getArguments<Args>()?.let {
                isEdit = true
                 dailyReport = it.dailyReport!!
                it.dailyReport?.let { it1 -> setUI(it1) }
            }
            setClickListeners()
        }
    }


    private fun setUI(reportObj: DailyReportsAddVO) {
        binding.apply {
            if (reportObj.relatedTo.equals("New Project")) {
                rbNewProject.isChecked=true
                llNewProject.visibility=View.VISIBLE
                llDescriptionWork.visibility=View.VISIBLE
                etProjectName.setText(reportObj.newprojectName)
                etContactName.setText(reportObj.newprojectContactName)
                etMobileNo.setText(reportObj.mobileNo)
                etCallType.setText(reportObj.callType.orEmpty())

            } else if (reportObj.relatedTo.equals("Existing Project")) {
                rbExistingProject.isChecked=true
                llExistingProject.visibility=View.VISIBLE
                llDescriptionWork.visibility=View.VISIBLE
                selectedCPId=""+reportObj.csCustomerprojectClientprojectDetailsid
                etClientProject.setText(reportObj.projectName)
                etProjectHeadName.setText(reportObj.projectHeadName)
            }
            reportObj.descriptionOfWorks?.forEach {
                addDescriptionItem(it)
            }

        }
    }

    private fun setClickListeners() {
        binding.apply {
            loadClientProjectDetails()
            rbNewProject.setOnClickListener {
                llNewProject.visibility=View.VISIBLE
                llDescriptionWork.visibility=View.VISIBLE
                llExistingProject.visibility=View.GONE
            }
            rbExistingProject.setOnClickListener {
                llNewProject.visibility=View.GONE
                llDescriptionWork.visibility=View.VISIBLE
                llExistingProject.visibility=View.VISIBLE
            }
            var view: ItemDescriptionWorkBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(this@CreateDailyReportsActivity),
                    R.layout.item_description_work,
                    null,
                    false
            )
            view.root.setTag(nextTag)
            llDesWork.addView(view.root)
            etCallType.setOnClickListener {
                var list = listOf<String>("Visit", "Call")
                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = list,
                    mapper = { it },
                    selectedPosition = null,
                    isSingleSelection = true,
                        isSearchable=false
                ) {
                    etCallType.setText(list[it.first()])
                }.show()
            }
//            {"requesterid":529,"requestname":"daily_report_checkin_checkout","requestparameters":
//            {"cs_dailyreport_id":"12","type":"checkin","datetime":"2022-01-17 20:10:10"}}
            btnAdd.setOnClickListener {
                addDescriptionItem(null)
            }
            save.setOnClickListener {
//                if (isValidate()) {

                if(rbNewProject.isChecked){
                    if(etProjectName.text?.length==0){
                        etProjectName.requestFocus()
                        etProjectName.setError("Please add Project Name")
                        return@setOnClickListener
                    }else if(etContactName.text?.length==0){
                        etContactName.requestFocus()
                        etContactName.setError("Please add Contact Name")
                        return@setOnClickListener
                    }else if(etMobileNo.text?.length==0){
                        etMobileNo.requestFocus()
                        etMobileNo.setError("Please add Mobile No")
                        return@setOnClickListener
                    }else if (etCallType.text?.toString().equals("Select")) {
                        Toast.makeText(
                                this@CreateDailyReportsActivity,
                                "Please Select Call type",
                                Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }else if (getWorksData().size == 0) {
                        toast("Please add Description of works")
                        return@setOnClickListener
                    }
                }else if(rbExistingProject.isChecked) {
                    if (etClientProject.text.toString().equals("Select")) {
                        Toast.makeText(
                                this@CreateDailyReportsActivity,
                                "Please Select Client project",
                                Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (getWorksData().size == 0) {
                        toast("Please add Description of works")
                        return@setOnClickListener
                    }
                }
                    callAddReportsApi(getWorksData())
            }

        }
    }
    private fun addDescriptionItem(obj: DailyReportsAddVO.DescriptionOfWork?) {
        binding.apply {
            var view: ItemDescriptionWorkBinding = DataBindingUtil.inflate(
                LayoutInflater.from(this@CreateDailyReportsActivity),
                R.layout.item_description_work,
                null,
                false
            )
            view.etText.setText(obj?.descriptionOfWorks ?: "")
            view.root.setTag(nextTag)
            nextTag++
            view.btnRemove.setOnClickListener {
                var requiredTag = view.root.getTag() as Int
                for (i in 0..llDesWork.childCount) {
                    var attachedTag = llDesWork.getChildAt(i).getTag() as Int
                    if (requiredTag == attachedTag) {
                        llDesWork.removeViewAt(i)
                        break
                    }
                }
            }
            llDesWork.addView(view.root)
            view.root.layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        }
    }

    private fun callAddReportsApi(worksData: MutableList<String>) {
        try {


            binding.apply {
                val dailyReportsAddVO = DailyReportsAddVO().apply {
                    if(rbNewProject.isChecked){
                        callType = etCallType.text.toString()
                        newprojectName=etProjectName.text.toString()
                        newprojectContactName=etContactName.text.toString()
                        mobileNo=etMobileNo.text.toString()
                        relatedTo="New Project"
                    }else if(rbExistingProject.isChecked){
                        customerProjectId =selectedCPId
                        projectHeadName=etProjectHeadName.text.toString()
                        relatedTo="Existing Project"
                    }
                    descriptionOfWorks = worksData.map {
                        DailyReportsAddVO.DescriptionOfWork().apply {
                            descriptionOfWorks = it
                        }
                    }
                    callDate = ""
                    callTime = ""
                    callStatus = 0

                }
                if(isEdit){
//                    if (etRelatedTo.text.toString().equals("Contractor")) {
//                        dailyReportsAddVO.contactContractorId = dailyReport.contactContractorId
//                        dailyReportsAddVO.customerProjectId = "0"
//                    } else if (etRelatedTo.text.toString() == "Client Project") {
//                        dailyReportsAddVO.contactContractorId = "0"
//                        dailyReportsAddVO.customerProjectId = dailyReport.customerProjectId
//                    }
                    dailyReportsAddVO.csDailyreportId= dailyReport.csDailyreportId
                    println(Gson().toJson(dailyReportsAddVO))
                    RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                            Constants.RequestNames.EDIT_DAILY_REPORTS,
                            dailyReportsAddVO,
                            true
                    )
                }else{
//                    if (etRelatedTo.text.toString().equals("Contractor")) {
//                        dailyReportsAddVO.contactContractorId = selectedCCId
//                        dailyReportsAddVO.customerProjectId = "0"
//                    } else if (etRelatedTo.text.toString() == "Client Project") {
//                        dailyReportsAddVO.contactContractorId = "0"
//                        dailyReportsAddVO.customerProjectId = selectedCPId
//                    }
                    println(Gson().toJson(dailyReportsAddVO))
                    RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                            Constants.RequestNames.ADD_DAILY_REPORTS,
                            dailyReportsAddVO,
                            true
                    )
                }


            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


    }
    override fun onResponseSuccess(
            objectResponse: ApiResponseController,
            objectRequest: ApiRequestController,
            progressDialog: ProgressDialog
    ) {
        try {
            when (objectResponse.requestname) {
                Constants.RequestNames.ADD_DAILY_REPORTS,
                Constants.RequestNames.EDIT_DAILY_REPORTS-> {

                    val dailyReportsAddVO: DailyReportsResObjVO =
                            Common.getSpecificDataObject(
                                    objectResponse.result,
                                    DailyReportsResObjVO::class.java
                            )
                    if (dailyReportsAddVO != null && dailyReportsAddVO.dailyReportsResObjVO!=null) {
                        db.commonDao().insertDailyReports(dailyReportsAddVO.dailyReportsResObjVO?.get(0))
                        ViewDailyReportsActivty.open(
                                this@CreateDailyReportsActivity,
                                dailyReportsAddVO.dailyReportsResObjVO?.get(0)!!
                        )
                        finish()
                    }
                }
            }
            Common.dismissProgressDialog(progressDialog)
        } catch (e: Exception) {
            Common.disPlayExpection(e, progressDialog)
        }
    }

    fun getWorksData(): MutableList<String> {
        var list = mutableListOf<String>()
        for (i in 0 until binding.llDesWork.childCount) {
            var text = binding.llDesWork.getChildAt(i)
                .findViewById<CustomEditText>(R.id.etText).text.toString().trim()
            if (text.isNotEmpty()) {
                list.add(text)
            }
        }
        return list
    }


    private fun loadClientProjectDetails() {
        binding.apply {
            etClientProject.setOnClickListener {
                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = customerProjectList,
                    mapper = { it.projectName },
                    selectedPosition = null,
                    isSingleSelection = true
                ) {
                    selectedCPId = customerProjectList.get(it.get(0)).customerProjectId
                    etClientProject.setText(customerProjectList.get(it.get(0)).projectName)
                    etProjectHeadName.setText(customerProjectList.get(it.get(0)).projectHead.get(0).projectHeadName)
                }.show()
            }
        }
    }

    private fun loadContractorDetails() {
        binding.apply {
            etClientProject.setOnClickListener {

                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = contractorContactList,
                    mapper = { it.contractorName },
                    selectedPosition = null,
                    isSingleSelection = true
                ) {
                    selectedCCId = contractorContactList.get(it.get(0)).contactContractorId
                    etClientProject.setText(contractorContactList.get(it.get(0)).contractorName)
                    etProjectHeadName.setText(contractorContactList.get(it.get(0)).contractorMobileNo)
                }.show()
            }
        }


    }

    data class Args(var dailyReport: DailyReportsAddVO? = null, var isEdit: Boolean = false) :
        Serializable

    companion object {
        fun open(context: Context, args: Args) {
            context.startActivity(
                    Intent(context, CreateDailyReportsActivity::class.java).apply {
                        putExtra("args", args)
                    }
            )
        }
    }
}