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
    private var clientProjectList: MutableList<ClientProject> = mutableListOf()
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
            tvRelatedTo.text = Common.setSppanableText("* Related To")
            tvCallType.text = Common.setSppanableText("* Call Type")
            tvCallDate.text = Common.setSppanableText("* Call Date")
            tvCallTime.text = Common.setSppanableText("* Call Time")
            tvOANo.text = Common.setSppanableText("* OA No")
            for (i in 0..customerProjectList.size - 1) {
                clientProjectList.addAll(customerProjectList.get(i).clientProjects)
            }
            Log.d("clientProjectList", clientProjectList.size.toString())
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
            etRelatedTo.setText(reportObj.relatedTo.orEmpty())
            etCallType.setText(reportObj.callType.orEmpty())
            etCallDate.setText(reportObj.callDate.orEmpty())
            etCallTime.setText(reportObj.callTime.orEmpty())
            etOANo.setText(reportObj.oaNumbers.orEmpty())
            if (reportObj.relatedTo.equals("Contractor")) {
                llSelectRelatedTo.visibility = View.VISIBLE
                tvClientProject.text = Common.setSppanableText("* Contractor")
                tvProjectHeadName.text = Common.setSppanableText("* Mobile No")
                etClientProject.setText(reportObj.contractorName)
                etProjectHeadName.setText(reportObj.contractorMobileNo)
                loadContractorDetails()
            } else if (reportObj.relatedTo.equals("Client Project")) {
                llSelectRelatedTo.visibility = View.VISIBLE
                tvClientProject.text = Common.setSppanableText("* Client Project")
                tvProjectHeadName.text = Common.setSppanableText("* Project Head Name")
                etClientProject.setText(reportObj.projectName)
                etProjectHeadName.setText(reportObj.projectHeadName)
                loadClientProjectDetails()
            }
            reportObj.descriptionOfWorks?.forEach {
                addDescriptionItem(it)
            }
        }
    }

    private fun setClickListeners() {
        binding.apply {
            etRelatedTo.setOnClickListener {
                var list = listOf<String>("Contractor", "Client Project")
                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = list,
                    mapper = { it },
                    selectedPosition = null,
                    isSingleSelection = true
                ) {
                    etRelatedTo.setText(list[it.first()])
                    if (list[it.first()].equals("Contractor")) {
                        llSelectRelatedTo.visibility = View.VISIBLE
                        tvClientProject.text = Common.setSppanableText("* Contractor")
                        tvProjectHeadName.text = Common.setSppanableText("* Mobile No")
                        loadContractorDetails()
                    } else if (list[it.first()].equals("Client Project")) {
                        llSelectRelatedTo.visibility = View.VISIBLE
                        tvClientProject.text = Common.setSppanableText("* Client Project")
                        tvProjectHeadName.text = Common.setSppanableText("* Project Head Name")
                        loadClientProjectDetails()
                    }
                }.show()
            }
            etCallType.setOnClickListener {
                var list = listOf<String>("Visit", "Call")
                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = list,
                    mapper = { it },
                    selectedPosition = null,
                    isSingleSelection = true
                ) {
                    etCallType.setText(list[it.first()])
                }.show()
            }
            etOANo.setOnClickListener {
                MultiSelectionDialog(
                    context = this@CreateDailyReportsActivity,
                    list = clientProjectList,
                    mapper = { it.oaNumber },
                    selectedPosition = null,
                    isSingleSelection = false
                ) {

                    selectedSubseries.add(clientProjectList[it[0]].csCustomerprojectClientProjectDetailsId)
                    etOANo.setText(
                        it.toMutableSet().map { clientProjectList[it].oaNumber }
                            .joinToString { it }
                    )
                }.show()
            }
            etCallDate.setOnClickListener {
                val mcurrentDate = Calendar.getInstance()
                val mYear = mcurrentDate[Calendar.YEAR]
                val mMonth = mcurrentDate[Calendar.MONTH]
                val mDay = mcurrentDate[Calendar.DAY_OF_MONTH]
                val mDatePicker = DatePickerDialog(
                    this@CreateDailyReportsActivity,
                    { datepicker, selectedyear, selectedmonth, selectedday ->
                        // TODO Auto-generated method stub
                        val sel_month = selectedmonth + 1
                        var sday = selectedday.toString()
                        var smonth: String? = null
                        smonth = if (sel_month < 10) "0$sel_month" else sel_month.toString()
                        sday = if (selectedday < 10) "0$selectedday" else selectedday.toString()
                        etCallDate.setText(Common.getDatenewFormat("$selectedyear-$smonth-$sday")[0])
                        etCallDate.setTag("$selectedyear-$smonth-$sday")
                    },
                    mYear,
                    mMonth,
                    mDay
                )
                mDatePicker.show()
            }
            etCallTime.setOnClickListener {
                getTime(etCallTime)
            }
//            {"requesterid":529,"requestname":"daily_report_checkin_checkout","requestparameters":
//            {"cs_dailyreport_id":"12","type":"checkin","datetime":"2022-01-17 20:10:10"}}

            btnCheckIn.setOnClickListener {
                val currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
                Log.d("localTime", parseDateToddMMyyyy(currentDateTimeString).toString())
                var checkInCheckOutVO=CheckInCheckOutVO().apply {
                    csDailyreportId="12"
                    type="checkin"
                    datetime=parseDateToddMMyyyy(currentDateTimeString)
                }
                RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                        Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT,
                        checkInCheckOutVO,
                        true
                )
            }
            btnCheckOut.setOnClickListener {
                val currentDateTimeString: String = DateFormat.getDateTimeInstance().format(Date())
                Log.d("localTime", parseDateToddMMyyyy(currentDateTimeString).toString())
                var checkInCheckOutVO=CheckInCheckOutVO().apply {
                    csDailyreportId="12"
                    type="checkout"
                    datetime=parseDateToddMMyyyy(currentDateTimeString)
                }
                RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                        Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT,
                        checkInCheckOutVO,
                        true
                )
            }
            btnAdd.setOnClickListener {
                addDescriptionItem(null)
            }
            save.setOnClickListener {
                if (isValidate()) {
                    if (etRelatedTo.text?.toString().equals("Select")) {
                        Toast.makeText(
                            this@CreateDailyReportsActivity,
                            "Please Select Related to",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (etCallType.text?.toString().equals("Select")) {
                        Toast.makeText(
                            this@CreateDailyReportsActivity,
                            "Please Select Call type",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    } else if (etOANo.text?.toString().equals("Select")) {
                        Toast.makeText(
                            this@CreateDailyReportsActivity,
                            "Please Select OA No",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    callAddReportsApi(getWorksData())
                }
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
                    csCustomerprojectClientprojectDetails = selectedSubseries.map {
                        CustomerprojectClientprojectDetails(it)
                    }
                    if (csCustomerprojectClientprojectDetails!!.size==0){
                        toast("Please select OA No")
                        return
                    }
                    descriptionOfWorks = worksData.map {
                        DailyReportsAddVO.DescriptionOfWork().apply {
                            descriptionOfWorks = it
                        }
                    }
                    relatedTo = etRelatedTo.text.toString()
                    callType = etCallType.text.toString()
                    callDate = etCallDate.text.toString()
                    callTime = etCallDate.text.toString() + " " + etCallTime.text.toString()

                }
                if(isEdit){
                    if (etRelatedTo.text.toString().equals("Contractor")) {
                        dailyReportsAddVO.contactContractorId = dailyReport.contactContractorId
                        dailyReportsAddVO.customerProjectId = "0"
                    } else if (etRelatedTo.text.toString() == "Client Project") {
                        dailyReportsAddVO.contactContractorId = "0"
                        dailyReportsAddVO.customerProjectId = dailyReport.customerProjectId
                    }
                    dailyReportsAddVO.csDailyreportId= dailyReport.csDailyreportId
                    println(Gson().toJson(dailyReportsAddVO))
                    RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                            Constants.RequestNames.EDIT_DAILY_REPORTS,
                            dailyReportsAddVO,
                            true
                    )
                }else{
                    if (etRelatedTo.text.toString().equals("Contractor")) {
                        dailyReportsAddVO.contactContractorId = selectedCCId
                        dailyReportsAddVO.customerProjectId = "0"
                    } else if (etRelatedTo.text.toString() == "Client Project") {
                        dailyReportsAddVO.contactContractorId = "0"
                        dailyReportsAddVO.customerProjectId = selectedCPId
                    }
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
                Constants.RequestNames.EDIT_DAILY_REPORTS,
                Constants.RequestNames.DAILY_REPORTS_CHECKIN_CHECKOUT-> {

                    val dailyReportsAddVO: DailyReportsResObjVO =
                            Common.getSpecificDataObject(
                                    objectResponse.result,
                                    DailyReportsResObjVO::class.java
                            )
                    if (dailyReportsAddVO != null) {
//                        db.commonDao().insertCustomerProject(dailyReportsAddVO)
                        ViewDailyReportsActivty.open(
                                this@CreateDailyReportsActivity,
                                dailyReportsAddVO.dailyReportsResObjVO!!
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
    private fun isValidate(): Boolean {
        var isFilled = false
        binding.apply {
            if (etCallDate.text?.length == 0) {
                etCallDate.requestFocus()
                etCallDate.setError("Please add Call Date")
            } else if (etCallTime.text?.length == 0) {
                etCallTime.requestFocus()
                etCallTime.setError("Please add Call time")
            } else if (getWorksData().size == 0) {
                toast("Description of works are missed")
            } else
                isFilled = true
        }
        return isFilled
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

    fun getTime(textView: CustomEditText) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textView.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        textView.setOnClickListener {
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }
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