package com.ncl.nclcustomerservice.activity

import android.app.DatePickerDialog
import android.app.ProgressDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.ncl.nclcustomerservice.R
import com.ncl.nclcustomerservice.`object`.*
import com.ncl.nclcustomerservice.abstractclasses.NetworkChangeListenerActivity
import com.ncl.nclcustomerservice.commonutils.Common
import com.ncl.nclcustomerservice.commonutils.Constants
import com.ncl.nclcustomerservice.customviews.CustomEditText
import com.ncl.nclcustomerservice.database.DatabaseHandler
import com.ncl.nclcustomerservice.databinding.ActivityCreateClientprojectBinding
import com.ncl.nclcustomerservice.databinding.ActivityCreateDailyreportsBinding
import com.ncl.nclcustomerservice.network.RetrofitRequestController
import com.ncl.nclcustomerservice.network.RetrofitResponseListener
import java.text.SimpleDateFormat
import java.util.*

class CreateDailyReportsActivity : NetworkChangeListenerActivity(), RetrofitResponseListener {
    override fun onInternetConnected() {
    }

    override fun onInternetDisconnected() {
    }

    private lateinit var binding: ActivityCreateDailyreportsBinding
    private lateinit var db: DatabaseHandler
    private lateinit var customerProjectList: List<CustomerProjectResVO>
    private lateinit var contractorContactList: List<CustomerContactResponseVo.ContactContractorList>
    lateinit var selectedCCId: String
    lateinit var selectedCPId: String
    private var clientProjectList:MutableList<ClientProject> = mutableListOf()
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
            for (i in 0..customerProjectList.size-1) {
                clientProjectList.addAll(customerProjectList.get(i).clientProjects)
            }
            Log.d("clientProjectList", clientProjectList.size.toString())
            setClickListeners()
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

                    selectedSubseries.add(clientProjectList.get(it.get(0)).csCustomerprojectClientProjectDetailsId)
                    etOANo.setText(
                            it.toMutableSet().map { clientProjectList.get(it).oaNumber }
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
            btnCheckIn.setOnClickListener {

            }
            btnCheckOut.setOnClickListener {

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
                    callAddReportsApi()
                }
            }

        }
    }

    //    {"requesterid":529,"requestname":"daily_report_add","requestparameters":
//    {"call_type":"Visit","related_to":"Client Project",
//    "contact_contractor_id":"0","customer_project_id":"4","call_date":"2022-01-17",
//    "call_time":"2022/01/17 17:00","cs_customerproject_clientproject_details":
//    [{"cs_customerproject_clientproject_detailsid":"3"},{"cs_customerproject_clientproject_detailsid":"15"}],
//    "description_of_works":[{"description_of_work":"test"},{"description_of_work":"test2"}]}}
    private fun callAddReportsApi() {
        try {
//            var list: MutableList<Pair<String, String>> = mutableListOf()
//            selectedSubseries.forEach { hm ->
//                var productCode = hm.key
//                var ind = hm.value
//                var subseries =
//                        ind.map {
//                            Pair(
//                                    hm.key,
//                                    hmProducts[productCode]?.get(it)?.productId ?: ""
//                            )
//                        }
//                                .toMutableList()
//                list.addAll(subseries.toMutableList())
//            }
//            var arrProduct = selectedSubseries.map {
//                DailyReportsAddVO.CustomerprojectClientprojectDetails(
//                        csCustomerprojectClientprojectDetailsid = it
//                )
//            }.toList()

            binding.apply {
                val dailyReportsAddVO = DailyReportsAddVO().apply {
                    relatedTo = etRelatedTo.text.toString()
                    callType = etCallType.text.toString()
                    callDate = etCallDate.text.toString()
                    callTime = etCallDate.text.toString() + " " + etCallTime.text.toString()
                    if (etRelatedTo.text.toString().equals("Contractor")) {
                        contactContractorId = selectedCCId
                        customerProjectId = "0"
                    } else if (etRelatedTo.text.toString().equals("Client Project")) {
                        contactContractorId = "0"
                        customerProjectId = selectedCPId
                    }
//                contractors = alContractors
//                projectHeads = alProjectHead
                }
                RetrofitRequestController(this@CreateDailyReportsActivity).sendRequest(
                        Constants.RequestNames.ADD_DAILY_REPORTS,
                        dailyReportsAddVO,
                        true
                )
            }
        }catch (e:Exception){
            e.printStackTrace()
        }



    }

    private fun isValidate(): Boolean {
        var isFilled = true
        binding.apply {
            if (etCallDate.text?.length == 0) {
                etCallDate.requestFocus()
                etCallDate.setError("Please add Call Date")
                isFilled = false
            } else if (etCallTime.text?.length == 0) {
                etCallTime.requestFocus()
                etCallTime.setError("Please add Call time")
                isFilled = false
            } else if (etDecWork.text?.length == 0) {
                etDecWork.requestFocus()
                etDecWork.setError("Please add Description of Works")
                isFilled = false
            }
        }
        return isFilled
    }

    fun getTime(textView: CustomEditText) {
        val cal = Calendar.getInstance()
        val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
            cal.set(Calendar.HOUR_OF_DAY, hour)
            cal.set(Calendar.MINUTE, minute)
            textView.setText(SimpleDateFormat("HH:mm").format(cal.time))
        }
        textView.setOnClickListener {
            TimePickerDialog(this, timeSetListener, cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE), true).show()
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

    override fun onResponseSuccess(objectResponse: ApiResponseController?, objectRequest: ApiRequestController?, progressDialog: ProgressDialog?) {
    }
}